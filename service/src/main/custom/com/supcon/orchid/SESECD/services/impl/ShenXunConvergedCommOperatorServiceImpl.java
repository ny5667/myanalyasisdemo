package com.supcon.orchid.SESECD.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.supcon.orchid.SESECD.model.dto.common.*;
import com.supcon.orchid.SESECD.model.dto.common.shenxun.CallPhoneReqDTO;
import com.supcon.orchid.SESECD.model.dto.common.shenxun.SMSReqDTO;
import com.supcon.orchid.SESECD.model.dto.common.shenxun.SendBroadcastDTO;
import com.supcon.orchid.SESECD.services.converged.comm.ConvergedCommOperatorService;
import com.supcon.orchid.SESECD.utils.HttpRequestUtil;
import com.supcon.supfusion.framework.cloud.common.exception.Biz500Exception;
import com.supcon.supfusion.framework.cloud.common.exception.ErrorDefinition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 上海申讯
 *
 * @Create by lwten on 2022/11/9 下午7:59
 */
@Service("Custom_ShenXunConvergedCommOperatorServiceImpl")
@Slf4j
public class ShenXunConvergedCommOperatorServiceImpl implements ConvergedCommOperatorService {


    private final String urlFmt = "http://%s:%d%s";

    private final String callPhoneUrl = "/terminalOperator";

    private final String broadcastGroup = "/syncBroadcastGroup";

    private final String sendBroadcast="/sms";



    @Override
    public String getProvider() {
        return "SESECD_signalProvider/001";
    }

    @Override
    public void callPhone(CommCallPhoneDTO commCallPhone) {
        if (StringUtils.isEmpty(commCallPhone.getSourceCaller())) {
            return;
        }
        if (CollectionUtils.isEmpty(commCallPhone.getDestCaller())) {
            return;
        }
        CallPhoneReqDTO req = new CallPhoneReqDTO();
        req.setSourCaller(commCallPhone.getSourceCaller());
        req.setDestCalled(commCallPhone.getDestCaller());
        String url = String.format(urlFmt, commCallPhone.getIp(), commCallPhone.getPort(), callPhoneUrl);
        try{
            HttpRequestUtil.sendHttpPost(url, JSON.toJSONString(req), HttpRequestUtil.CONTENT_TYPE_JSON_URL);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new Biz500Exception(new ErrorDefinition() {
                @Override
                public Integer getCode() {
                    return 500000;
                }

                @Override
                public String getMessage() {
                    return "融合通信语言呼叫失败！";
                }

                @Override
                public String getInfo() {
                    return null;
                }
            });
        }

    }

    @Override
    public List<BroadcastGroupDTO> listBroadcastGroup(CommReqDTO commReq) {
        String url = String.format(urlFmt, commReq.getIp(), commReq.getPort(), broadcastGroup);
        String responseContent = HttpRequestUtil.sendHttpPost(url, StringUtils.EMPTY, HttpRequestUtil.CONTENT_TYPE_JSON_URL);
        JSONObject json = JSON.parseObject(responseContent);
        if (json.getInteger("error") != 0) {
            return Collections.emptyList();
        }
        JSONArray groups = json.getJSONArray("data");
        return JSON.parseArray(groups.toJSONString(),BroadcastGroupDTO.class);
    }

    @Override
    public void operatorBroadcast(BroadcastDTO broadcast) {
        SendBroadcastDTO req = new SendBroadcastDTO();
        if (StringUtils.isEmpty(broadcast.getContent())){
            req.setSystemVoice(0);
        }else{
            req.setVcontent(broadcast.getContent());
        }
        if (broadcast.getAction()==0){
            req.setAction("stop");
        }else{
            req.setAction("send");
        }
        if (CollectionUtils.isEmpty(broadcast.getGroupIds())){
            List<BroadcastGroupDTO> groups = listBroadcastGroup(broadcast);
           String mobile = groups.stream().map(BroadcastGroupDTO::getGroupId).collect(Collectors.joining(","));
           req.setMobile(mobile);
        }else{
            String mobile = String.join(",", broadcast.getGroupIds());
            req.setMobile(mobile);
        }
        String url = String.format(urlFmt, broadcast.getIp(), broadcast.getPort(), req);
        HttpRequestUtil.sendHttpPost(url, JSON.toJSONString(req), HttpRequestUtil.CONTENT_TYPE_JSON_URL);
    }

    @Override
    public void notifyMsg(MsgNotifyDTO msgNotify) {
        SMSReqDTO req = new SMSReqDTO();
        req.setMobile(StringUtils.join(msgNotify.getDestCaller(),","));
        req.setSystemVoice(msgNotify.getSystemVoiceIndex());
        if (msgNotify.getSystemVoiceIndex()==-1){
            req.setVcontent(msgNotify.getContent());
        }
        req.setPlayCnt(msgNotify.getPlaybackTime());
        String url = String.format(urlFmt, msgNotify.getIp(), msgNotify.getPort(), sendBroadcast);
        HttpRequestUtil.sendHttpPost(url, JSON.toJSONString(req), HttpRequestUtil.CONTENT_TYPE_JSON_URL);
    }
}
