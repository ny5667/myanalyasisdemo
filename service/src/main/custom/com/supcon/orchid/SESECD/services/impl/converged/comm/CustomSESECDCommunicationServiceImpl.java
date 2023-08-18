package com.supcon.orchid.SESECD.services.impl.converged.comm;

import com.alibaba.fastjson.JSONObject;
import com.supcon.orchid.SESEAB.DTO.MemberDTO;
import com.supcon.orchid.SESECD.model.vo.ConversationVO;
import com.supcon.orchid.SESECD.model.vo.MemberVO;
import com.supcon.orchid.SESECD.daos.SESECDSignalConfigDao;
import com.supcon.orchid.SESECD.model.dto.fuse.CallResponseDTO;
import com.supcon.orchid.SESECD.model.dto.fuse.TerminalDTO;
import com.supcon.orchid.SESECD.model.dto.fuse.TerminalListDTO;
import com.supcon.orchid.SESECD.entities.SESECDSignalConfig;
import com.supcon.orchid.SESECD.services.converged.comm.CustomSESECDCommunicationService;
import com.supcon.orchid.SESECD.utils.HttpRequestUtil;
import com.supcon.orchid.SESECD.utils.SqlUtils;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.support.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: xulong2
 * @create: 2021-03-18 15:20
 * @description 广西华谊融合通讯定制
 **/
@Service("CustomSESECDCommunicationService")
@Transactional
public class CustomSESECDCommunicationServiceImpl extends BaseServiceImpl implements CustomSESECDCommunicationService {

    @Autowired
    private SESECDSignalConfigDao signalConfigDao;
    @Autowired
    private SqlUtils sqlUtils;

    @Override
    public Result<String> conversation(ConversationVO vo) {
        SESECDSignalConfig signalConfig = getSignalConfig("SESECD_signalType/001",getCurrentCompanyId());
        if(Objects.isNull(signalConfig)){
            return Result.data(301,"无通信配置，请配置后重新呼叫!","");
        }
        vo.setSourcaller(signalConfig.getSourceTerminal().toString());
        String url = "http://" + signalConfig.getIp() + ":" + signalConfig.getPort() + "/TerminalOperator";
        String json = "";
        try {
            json = HttpRequestUtil.sendHttpPost(url, JSONObject.toJSONString(vo), HttpRequestUtil.CONTENT_TYPE_JSON_URL);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        CallResponseDTO dto = JSONObject.parseObject(json, CallResponseDTO.class);
        if(Objects.isNull(dto)){
            return Result.data(301,"接口异常，请检查融合通信网络是否正常!","");
        }
        return matchingResult(dto.getError());
    }

    public SESECDSignalConfig getSignalConfig(String type,long cid){
        String sql = "from " + SESECDSignalConfig.JPA_NAME + " WHERE VALID = 1 AND ENABLE = 1 AND signalType = '" + type + "' " + sqlUtils.getSqlPartByCID();
        List<SESECDSignalConfig> list = signalConfigDao.findByHql(sql);
        if(Objects.isNull(list) || list.size() == 0){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<MemberVO> getPersonOnlineStatus(List<MemberDTO> staffIdsByPlanIds) {
        List<String> phones = staffIdsByPlanIds.stream().filter(e -> Objects.nonNull(e.getTelephone())).map(MemberDTO::getTelephone).collect(Collectors.toList());
        Map<String,Object> param = new HashMap<>();
        param.put("action",1);
        param.put("terminals",phones);
        SESECDSignalConfig signalConfig = getSignalConfig("SESECD_signalType/001",getCurrentCompanyId());
        Map<String,String> statusMap = new HashMap<>();
        if(Objects.nonNull(signalConfig)){
            String url = "http://" + signalConfig.getIp() + ":" + signalConfig.getPort() + "/GetTerminals";
            String json = "";
            try {
                json = HttpRequestUtil.sendHttpPost(url, JSONObject.toJSONString(param), HttpRequestUtil.CONTENT_TYPE_JSON_URL);
            }catch (Exception e){
                log.error(e.getMessage());
            }
            TerminalListDTO dto = JSONObject.parseObject(json, TerminalListDTO.class);
            if(Objects.nonNull(dto) && Objects.nonNull(dto.getError()) && dto.getError() == 0 && Objects.nonNull(dto.getTerminalist())){
                for(TerminalDTO terminalDTO : dto.getTerminalist()){
                    String status = "";
                    if(Objects.nonNull(terminalDTO.getKeyindex()) && terminalDTO.getKeyindex() == 0){
                        status = "终端不存在";
                    }
                    if(Objects.nonNull(terminalDTO.getIscaller()) && terminalDTO.getIscaller() == 0){
                       status = Objects.nonNull(terminalDTO.getState()) && terminalDTO.getState() == 1 ? "在线" : "脱机";
                    }else if(Objects.nonNull(terminalDTO.getIscaller()) && terminalDTO.getIscaller() != 0){
                        status = "通话中";
                    }else{
                        status = Objects.nonNull(terminalDTO.getState()) && terminalDTO.getState() == 1 ? "在线" : "脱机";
                    }

                    statusMap.put(terminalDTO.getTerminalid(),status);
                }
            }
        }
        List<MemberVO> list = new ArrayList<>();
        for(MemberDTO dto : staffIdsByPlanIds){
            MemberVO vo = new MemberVO();
            BeanUtils.copyProperties(dto,vo);
            if(Objects.nonNull(dto.getTelephone())){
                vo.setOnlineStatus(statusMap.get(dto.getTelephone()));
            }
            list.add(vo);
        }
        return list;
    }

    private Result<String> matchingResult(Integer error){
        if(Objects.isNull(error)){
            return Result.data(301,"接口异常，请检查融合通信网络是否正常!","");
        }
        switch (error.toString()){
            case "0" :
                return Result.data(200,"呼叫成功","");
            case "1" :
                return Result.data(301,"呼叫失败","");
            case "2" :
                return Result.data(301,"参数有误","");
            case "3" :
                return Result.data(301,"源终端号码不是调度总机号码","");
            case "4" :
                return Result.data(301,"被呼叫终端号码不存在","");
            case "5" :
                return Result.data(301,"无法获取键权，可能源终端话机未摘机","");
            case "6" :
                return Result.data(301,"被呼叫终端正忙","");
            case "7" :
                return Result.data(301,"当前终端状态不允许执行当前操作","");
            default:
                return Result.data(301,"未知返回码","");
        }
    }

}
