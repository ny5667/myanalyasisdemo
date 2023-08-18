package com.supcon.orchid.SESECD.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supcon.orchid.SESECD.model.dto.message.SendMessageDto;
import com.supcon.orchid.SESECD.services.CustomSESECDWsService;

@Controller
public class CustomSESECDMessageController {
	
	@Autowired
	private CustomSESECDWsService customSESECDWsService;

    @PostMapping("/SESECD/alarmRecord/alarmRecord/getMsgDataByEventId")
    @ResponseBody
    public SendMessageDto getMsgDataByEventId(@RequestParam Long id){
        return customSESECDWsService.getMsgDataByEventId(id);
    }

	@PostMapping("/SESECD/alarmRecord/alarmRecord/sendMsgTo")
    @ResponseBody
    public String sendMsgToPerson(@RequestBody SendMessageDto sendMessageDto){
        return customSESECDWsService.sendMsgToPerson(sendMessageDto);
    }
}
