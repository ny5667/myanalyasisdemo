package com.supcon.orchid.SESECD.services.impl.mixmessage;

import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.daos.SESECDCommunicationDao;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDCommunication;
import com.supcon.orchid.SESECD.model.dto.MixMessageReqDTO;
import com.supcon.orchid.SESECD.services.mixmessage.CustomSESECDSendMessageService;
import com.supcon.orchid.SESECD.utils.MsgTypeUtils;
import com.supcon.orchid.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Transactional
@Service("CustomSendMessageServiceImpl")
public class CustomSESECDSendMessageServiceImpl extends BaseServiceImpl<SESECDAlmAlarmRecord> implements CustomSESECDSendMessageService {

    @Autowired
    SESECDCommunicationDao sesecdCommunicationDao;
    @Autowired
    SESECDAlmAlarmRecordDao sesecdAlmAlarmRecordDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void customAfterSendingMsg(MixMessageReqDTO requestDTO) {
        //保存消息发送记录--不管成不成功都会保存，所以新开事务
        log.error("----------->" + requestDTO.getAlmAlarmRecordID().toString());
        SESECDCommunication sesecdCommunication = new SESECDCommunication();
        //取预警记录对象，对象为空或报错直接结束方法
        try {
            SESECDAlmAlarmRecord sesecdAlmAlarmRecord = sesecdAlmAlarmRecordDao.get(requestDTO.getAlmAlarmRecordID());
            sesecdCommunication.setAlmAlarmRecord(sesecdAlmAlarmRecord);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return;
        }
        //存入要求字段
        sesecdCommunication.setMessageContent(requestDTO.getContent());
        sesecdCommunication.setSendingTime(new Date());
        List<Integer> messageTypes = requestDTO.getMessageTypes();
        StringBuffer type = new StringBuffer();
        messageTypes.forEach(e -> {
            String s = MsgTypeUtils.MSG_TYPE.get(e);
            type.append(s).append("，");
        });
        sesecdCommunication.setSendingMethod(type.toString());
        //保存结束
        sesecdCommunicationDao.save(sesecdCommunication);
        sesecdCommunicationDao.flush();
        log.error("----------->通讯记录结束" + JSON.toJSONString(sesecdCommunication));
    }


    //-------------------------------------------------公共功能--------------------------------------------------


}
