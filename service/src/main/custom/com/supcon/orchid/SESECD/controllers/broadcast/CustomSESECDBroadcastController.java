package com.supcon.orchid.SESECD.controllers.broadcast;

import com.supcon.orchid.SESECD.model.vo.broadcast.*;
import com.supcon.orchid.SESECD.services.broadcast.CustomSESECDBroadcastService;
import com.supcon.orchid.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


@RestController
public class CustomSESECDBroadcastController {

    @Autowired
    CustomSESECDBroadcastService broadcastService;

    /**
     * 页面加载获取广播列表
     * @return
     */
    @PostMapping("/public/SESECD/broadcastIntercom/broadcastInfo/getBroadcastInfoList")
    public Result getBroadcastInfoList(@RequestBody BroadcastPageVO pageVO){
        BroadcastPageResult result = broadcastService.getBroadcastInfoList(pageVO);
        return Result.data(result);
    }


    /**
     * 用海康获取H5实时广播URL接口
     * @param broadcastVO
     * @return
     */
    @PostMapping("/public/SESECD/broadcastIntercom/broadcastInfo/broadcastURLs")
    public Result broadcastURLs(){
        BroadcastH5URLResponse responseResult = broadcastService.broadcastURLs();
        Result<String> result = new Result<>();
        if (ObjectUtils.isEmpty(responseResult)){
            return Result.fail("无法获取第三方接口收据");
        }
        result.setData(responseResult.getUrl());
        result.setCode(200);
        result.setMessage(responseResult.getMessage());
        return result;
    }

    /**
     * 开启广播点位
     * @param startBroadcastVO
     * @return
     */
    @PostMapping("/public/SESECD/broadcastIntercom/broadcastInfo/broadcastURLs/startBroadcast")
    public Result startBroadcast(@RequestBody StartBroadcastVO startBroadcastVO){
        broadcastService.startBroadcast(startBroadcastVO);
        return Result.data("");
    }

    /**
     * 关闭广播
     * @return
     */
    @GetMapping("/public/SESECD/broadcastIntercom/broadcastInfo/broadcastURLs/stopBroadcast")
    public Result stopBroadcast(){
        broadcastService.stopBroadcast();
        return Result.data("");
    }

    /**
     * 设置广播音量
     * @param broadcastVolumeVO
     * @return
     */
    @GetMapping("/public/SESECD/broadcastIntercom/broadcastInfo/broadcastURLs/setAudioVolumeParam")
    public Result setAudioVolumeParam(@RequestBody SetBroadcastVolumeVO broadcastVolumeVO){
        broadcastService.setAudioVolumeParam(broadcastVolumeVO);
        return Result.data("");
    }
}
