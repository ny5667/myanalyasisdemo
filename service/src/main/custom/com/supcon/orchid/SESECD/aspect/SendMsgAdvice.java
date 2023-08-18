package com.supcon.orchid.SESECD.aspect;

import com.supcon.orchid.SESECD.model.dto.MixMessageReqDTO;
import com.supcon.orchid.SESECD.services.mixmessage.CustomSESECDSendMessageService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Aspect
@Component
@Slf4j
public class SendMsgAdvice {
    @Autowired
    private CustomSESECDSendMessageService customSendMessageService;



    @Before("execution(* com.supcon.orchid.SESECD.services.mixmessage.ICustomMixMessageService.sendMsg(..))")
    public void sendMsg(JoinPoint joinPoint){
        MixMessageReqDTO reqDTO = (MixMessageReqDTO)joinPoint.getArgs()[0];
        customSendMessageService.customAfterSendingMsg(reqDTO);
    }
}
