package com.supcon.orchid.SESECD.strategy.ConcreteStrategy;

import com.alibaba.fastjson.JSON;

import com.supcon.orchid.SESECD.model.vo.broadcast.*;
import com.supcon.orchid.SESECD.model.vo.broadcast.request.HikvisionBroadcastPageVoRequest;
import com.supcon.orchid.SESECD.model.vo.broadcast.request.HikvisionSetBroadcastVolumeRequest;
import com.supcon.orchid.SESECD.model.vo.broadcast.request.HikvisionStartBroadcastH5URLResponse;
import com.supcon.orchid.SESECD.model.vo.broadcast.response.HikvisionBroadcastH5URLResponse;
import com.supcon.orchid.SESECD.model.vo.broadcast.response.HikvisionBroadcastPageVoResponse;
import com.supcon.orchid.SESECD.model.vo.broadcast.response.HikvisionBroadcastPoint;
import com.supcon.orchid.SESECD.strategy.AbstratcStrategy.BroadcastStrategy;
import com.supcon.orchid.SESECD.utils.HikvisionRestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("Hikvision")
@Slf4j
public class HikvisionBroadcastStrategy implements BroadcastStrategy {


    @Override
    public String getType() {
        return "Hikvision";
    }

    /**
     * 获取H5实施广播URL
     * @param broadcastVO
     * @return
     */
    @Override
    public BroadcastH5URLResponse broadcastURLs() {
        log.error("获取H5实施广播URL");
        log.error("获取H5实时广播URL接口地址 ：" + HikvisionConstants.H5_URL );
        BroadcastVO broadcastVO = new BroadcastVO();
        String response = HikvisionRestHelper.send(JSON.toJSONString(broadcastVO), HikvisionConstants.H5_URL);
        HikvisionBroadcastH5URLResponse result = JSON.parseObject(response, HikvisionBroadcastH5URLResponse.class);
        if (ObjectUtils.isEmpty(result)){
            log.error("第三方数据为空！！！联系客户方进行调整");
            return null;
        }
        BroadcastH5URLResponse broadcastH5URLResponse = new BroadcastH5URLResponse();
        broadcastH5URLResponse.setCode(result.getCode());
        broadcastH5URLResponse.setMessage(result.getMsg());
        broadcastH5URLResponse.setUrl(result.getData());
        log.error("获取H5实施广播URL获取结束");
        return broadcastH5URLResponse;
    }

    /**
     * 开启广播
     * @param startBroadcastVO
     */
    @Override
    public void startBroadcast(StartBroadcastVO startBroadcastVO) {
        log.error("开启海康设备广播");
        String indexCodes = startBroadcastVO.getIndexCodes().stream().collect(Collectors.joining(","));
        HikvisionStartBroadcastH5URLResponse data = new HikvisionStartBroadcastH5URLResponse();
        data.setSzBroadCastUrl(startBroadcastVO.getUrl());
        data.setSzBroadcastIndexcodes(indexCodes);
        log.error("获取开启广播URL接口地址 ：" + HikvisionConstants.START_BROADCAST );
        String send = HikvisionRestHelper.send(JSON.toJSONString(data), HikvisionConstants.START_BROADCAST);
        log.error("开启广播结束...." + send);
    }

    /**
     * 关闭广播
     */
    @Override
    public void stopBroadcast() {
        log.error("关闭海康设备广播");
        log.error("获取关闭广播URL接口地址 ：" + HikvisionConstants.STOP_BROADCAST );
        String result = HikvisionRestHelper.send(null, HikvisionConstants.START_BROADCAST);
        log.error("关闭广播结束...." + result);
    }

    /**
     * 调节广播音量
     */
    @Override
    public void setAudioVolumeParam(SetBroadcastVolumeVO broadcastVolumeVO) {
        log.error("调节海康设备广播音量");
        HikvisionSetBroadcastVolumeRequest request = new HikvisionSetBroadcastVolumeRequest();
        if (broadcastVolumeVO.getVolume() >10) {
            request.setVolume(10);
        }else if (broadcastVolumeVO.getVolume() < 0){
            request.setVolume(0);
        }else {
            request.setVolume(broadcastVolumeVO.getVolume());
        }
        String indexCodes = broadcastVolumeVO.getIndexCodes().stream().collect(Collectors.joining(","));
        request.setAudioPointIndexCode(indexCodes);
        log.error("获取关闭广播URL接口地址 ：" + HikvisionConstants.SET_VOLUME );
        String result = HikvisionRestHelper.send(JSON.toJSONString(request), HikvisionConstants.START_BROADCAST);
        log.error("调节广播音量结束...." + result);
    }

    /**
     * 调用分页获取广播点接口
     * @param pageVO
     */
    @Override
    public List<BroadcastInfoDTO> fetchAudioChannel(BroadcastPageVO pageVO) {
        log.error("调用分页获取广播点接口!!!");
        HikvisionBroadcastPageVoRequest pageRequest = new HikvisionBroadcastPageVoRequest();
        if (ObjectUtils.isEmpty(pageVO.getPageNo())){
            log.error("初始化默认当前页码：1");
            pageVO.setPageNo("1");
        }
        if (ObjectUtils.isEmpty(pageVO.getPageSize()) || Integer.valueOf(pageVO.getPageSize()) > 1000){
            log.error("初始化默认每页总数：1000");
            pageVO.setPageSize("1000");
        }
        BeanUtils.copyProperties(pageVO,pageRequest);
        String data = JSON.toJSONString(pageRequest);
        log.error("接口地址:" +HikvisionConstants.BROADCAST_POINT_PAGE);
        String response = HikvisionRestHelper.send(data, HikvisionConstants.BROADCAST_POINT_PAGE);
        HikvisionBroadcastPageVoResponse result = JSON.parseObject(response, HikvisionBroadcastPageVoResponse.class);
        List<HikvisionBroadcastPoint> list = result.getData().getList();
        if (CollectionUtils.isEmpty(list)){
            log.error("分页请求结果数量:0");
            return new ArrayList<>();
        }
        log.error("分页请求结果数量:"+list.size());
        List<BroadcastInfoDTO> collect = list.stream().filter(s -> s.getState() == 0).map(s -> convert(s)).collect(Collectors.toList());
        return collect;
    }

    /**
     * 转换
     * @param data
     * @return
     */
    private BroadcastInfoDTO convert(HikvisionBroadcastPoint data){
        BroadcastInfoDTO dto = new BroadcastInfoDTO();
        BeanUtils.copyProperties(data,dto);
        return dto;
    }

//    public static void main(String[] args) {
//        HikvisionSetBroadcastVolumeRequest s = new HikvisionSetBroadcastVolumeRequest();
//        s.setVolume(1);
//        s.setAudioPointIndexCode("748d84750e3a4a5bbad3cd4af9ed5101");
//        String s1 = JSON.toJSONString(s);
//        System.out.println("JSON.toJSONString:"+ s1 );
//        System.out.println("-------------------------------");
//        JSONObject jsonBody = new JSONObject();
//        jsonBody.put("audioPointIndexCode", "748d84750e3a4a5bbad3cd4af9ed5101");
//        jsonBody.put("volume", 0);
//        System.err.println("JSON.JSONObject.toJSONString:"+JSONObject.toJSONString(jsonBody));
//    }

}
