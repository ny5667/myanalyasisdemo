package com.supcon.orchid.SESECD.controllers;

import com.supcon.orchid.SESECD.model.dto.common.BroadcastDTO;
import com.supcon.orchid.SESECD.model.dto.common.BroadcastGroupDTO;
import com.supcon.orchid.SESECD.model.dto.common.MsgNotifyDTO;
import com.supcon.orchid.SESECD.model.vo.common.BroadcastVO;
import com.supcon.orchid.SESECD.model.vo.common.CallPhoneVO;
import com.supcon.orchid.SESECD.model.vo.common.SourceTerminalVO;
import com.supcon.orchid.SESECD.services.converged.comm.ConvergedCommStrategyService;
import com.supcon.orchid.api.annotation.CustomRequest;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @menu 融合通信
 * @Create by lwten on 2022/11/10 下午4:30
 */
@Controller
@RequestMapping(value = "/SESECD/convrged/comm/")
@CustomRequest
public class ConvergedCommController {

    @Autowired
    private ConvergedCommStrategyService convergedCommService;

    /**
     * 语音呼叫
     *
     * @param callPhone
     */
    @PostMapping("callPhones")
    @ResponseBody
    public Result callPhones(@RequestBody CallPhoneVO callPhone) {
        if (CollectionUtils.isEmpty(callPhone.getDestCaller())) {
            return Result.fail();
        }
        convergedCommService.callPhone(callPhone.getId(), callPhone.getTerminalNo(), callPhone.getDestCaller());
        return Result.success();
    }

    /**
     * 获取广播组
     *
     * @param id
     * @return
     */
    @PostMapping("broadcast/list/{configId}")
    @ResponseBody
    public List<BroadcastGroupDTO> listGroup(@PathVariable("configId") Long id) {
        return convergedCommService.listBroadcastGroup(id);
    }

    /**
     * 发起广播
     *
     * @param broadcast
     */
    @PostMapping("broadcast/send")
    @ResponseBody
    public void sendBroadcast(@RequestBody BroadcastVO broadcast) {
        BroadcastDTO dto = new BroadcastDTO();
        dto.setAction(1);
        dto.setContent(broadcast.getContent());
        dto.setGroupIds(broadcast.getGroupIds());
        convergedCommService.sendBroadcast(broadcast.getConfigId(), dto);
    }

    /**
     * 停止广播
     *
     * @param broadcast
     */
    @PostMapping("broadcast/stop")
    @ResponseBody
    public void stopBroadcast(@RequestBody BroadcastVO broadcast) {
        BroadcastDTO dto = new BroadcastDTO();
        dto.setAction(0);
        dto.setContent(broadcast.getContent());
        dto.setGroupIds(broadcast.getGroupIds());
        convergedCommService.sendBroadcast(broadcast.getConfigId(), dto);
    }

    /**
     * 总机终端列表
     *
     * @return
     */
    @GetMapping("sourceTerminal/list")
    @ResponseBody
    public List<SourceTerminalVO> listTerminal() {
        return convergedCommService.listTerminal();
    }

    /**
     * 消息通知
     * @param msgNotify
     */
    @PostMapping("msg/notify")
    @ResponseBody
    public void notifyMsg(@RequestBody MsgNotifyDTO msgNotify){
        convergedCommService.notifyMsg(null,msgNotify);
    }
}
