package com.supcon.orchid.SESECD.controllers;

import com.supcon.orchid.SESECD.model.vo.ConversationVO;
import com.supcon.orchid.SESECD.services.converged.comm.CustomSESECDCommunicationService;
import com.supcon.orchid.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: xulong2
 * @create: 2021-03-18 15:00
 * @description  融合环境通信
 **/
@Controller("CustomSESECDCommunicationController")
public class CustomSESECDCommunicationController {

    @Autowired
    private CustomSESECDCommunicationService communicationService;


    /**
     * 融合通信号码呼叫或挂断
     * @param vo
     * @return
     */
    @ResponseBody
    @PostMapping("/SESECD/communication/communication/conversation")
    public Result<String>  conversation(@RequestBody ConversationVO vo){
        return communicationService.conversation(vo);
    }
}
