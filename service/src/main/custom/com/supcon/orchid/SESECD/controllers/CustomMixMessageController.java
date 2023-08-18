package com.supcon.orchid.SESECD.controllers;


import com.supcon.orchid.SESECD.model.dto.MixMessageReqDTO;
import com.supcon.orchid.SESECD.services.mixmessage.ICustomMixMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *     融合通信接口
 * </p>
 *
 * @author lufengdong
 * @create 2023-04-28 10:13
 */
@RestController
public class CustomMixMessageController {


    @Autowired
    private ICustomMixMessageService customMixMessageService;

    //@Autowired
    //private NotifyFacade noticeServiceSupporter;
    /**
     * PC端融合通信，消息推送t
     * @param requestDTO
     */
    @PostMapping("/SESECD/alarmRecord/telecom/sendMsg")
    public void sendMessage(@RequestBody @Valid MixMessageReqDTO requestDTO){
        customMixMessageService.sendMsg(requestDTO);
    }


    /**
     * 借地测试
     */
    @PostMapping("/SESECD/alarmRecord/telecom/send")
    public void send(){
        //NoticeServiceRequest request = new NoticeServiceRequest();
        //// 发送短信
        //request.setTopicCode("AlarmActionNotice");
        //request.setStaffCodes(Arrays.asList("wcz"));
        //Map<String, String> modelParams = new HashMap<>(2);
        //modelParams.put("accidentName", "512");
        //request.setModelParams(modelParams);
        //noticeServiceSupporter.sendSmsMsg(request);
        //
        //// 发送邮件
        //Map<String, Object> extraParams = new HashMap<>(2);
        //extraParams.put("title", "接警事件生成通知");
        //request.setExtraParams(extraParams);
        //noticeServiceSupporter.sendEmailMsg(request);
    }
}
