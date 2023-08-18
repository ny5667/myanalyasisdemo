package com.supcon.orchid.SESECD.services.impl;

import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.model.dto.message.SendMessageDto;
import com.supcon.orchid.SESECD.services.CustomSESECDWsService;
import com.supcon.orchid.SESECD.services.notify.NotifyFacade;
import com.supcon.orchid.SesCommonFunc.client.SesCommFuncMsgServiceClient;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.services.SystemCodeService;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomSESECDWsServiceImpl extends BaseServiceImpl<SESECDAlmAlarmRecord> implements CustomSESECDWsService {

    @Autowired
    private SesCommFuncMsgServiceClient msgServiceClient;

    @Autowired
    private SESECDAlmAlarmRecordDao almAlarmRecordDao;
    @Autowired
    private SystemCodeService systemCodeService;

    @Autowired
    private NotifyFacade notifyFacade;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    /**
     * 获取消息标题和内容
     *
     * @param id
     * @return
     */
    @Override
    public SendMessageDto getMsgDataByEventId(Long id) {

        SendMessageDto messageDto = new SendMessageDto();

        SESECDAlmAlarmRecord alarmRecord = almAlarmRecordDao.get(id);

        String accidentName = alarmRecord.getAccidentName();//事件名称
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date happenTime = alarmRecord.getHappenTime();  //发生时间
        String dateString = "无";
        if (happenTime != null) {
            dateString = formatter.format(happenTime);
        }
        String position = (Objects.nonNull(alarmRecord.getLocationIncident()) ? alarmRecord.getLocationIncident().getName() : "无");
        // Company hpnCompany = alarmRecord.getHpnCompany();  //事发公司
        Integer wounderPerson = (Objects.nonNull(alarmRecord.getWounderPerson()) ? alarmRecord.getWounderPerson() : 0);//事发人数
        String description = alarmRecord.getDescription();   //事件简述
        Boolean isIncident = alarmRecord.getIsIncident();   //是否是应急事件
        String emergency = null;
        //TODO  演练事件 和应急事件 都时应急事件
//        if (isIncident){
//            emergency = "（是应急事件）";
//        }else {
//            emergency = "（不是应急演练）";
//        }
        SystemCode eventType = alarmRecord.getEventType();
        List<SystemCode> msgTitleList = systemCodeService.getSystemCodeListByEntityCode("SESECD_eventTypeMsgTitle");
        List<SystemCode> collect = msgTitleList.stream().filter(m -> m.getCode().equals(eventType.getCode())).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            emergency = InternationalResource.get(collect.get(0).getValue(), getCurrentLanguage());
        }
        StringBuffer content = new StringBuffer();
        content.append(InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.accidentName", getCurrentLanguage()) + ":" + accidentName + "\n");
        content.append(InternationalResource.get("SESECD.propertyshowName.randon1576650110363.flag", getCurrentLanguage()) + ":" + dateString + "\n");
        content.append(InternationalResource.get("SESECD.propertyshowName.randon1576720785713.flag", getCurrentLanguage()) + ":" + position + "\n");
        //content.append(InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.company", getCurrentLanguage()) + ":" + hpnCompany + " ");
        content.append(InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.wounderPerson", getCurrentLanguage()) + ":" + wounderPerson + "\n");
        content.append(InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.description", getCurrentLanguage()) + ":" + description);

        messageDto.setMsgTitle(accidentName + emergency);
        messageDto.setMsgContent(content.toString());

        return messageDto;
    }

    /**
     * 向应急通讯小组成员推送应急指挥的消息
     *
     * @param sendMessageDto
     */
    @Override
    public String sendMsgToPerson(SendMessageDto sendMessageDto) {
        log.info("应急小组发送消息开始");
        String returnMsg = "";
        List<String> codes = sendMessageDto.getCodes();
        if (null == codes || codes.size() == 0) {
            returnMsg = "FAULT";
            return returnMsg;
        }

        String msgTitle = sendMessageDto.getMsgTitle();
        String msgContent = sendMessageDto.getMsgContent();
        log.info("开始发送消息***********************");
        Map<String, String> mapData = new HashMap<>();
        mapData.put("msgTitle", msgTitle);
        mapData.put("msgContent", msgContent);
        com.supcon.orchid.SESECD.services.notify.MsgModelDTO build = com.supcon.orchid.SESECD.services.notify.MsgModelDTO.builder().param(mapData).msgType(ConstDict.MSG_TYPE_EMC_ACTION).receivers(codes).build();
        executor.execute(() -> {
            try {
                notifyFacade.handleNotify(build);
            } catch (Exception ex) {
                log.error("远程调用sendMsgModel Error，参数：" + JSON.toJSONString(build), ex);
            }
        });

        returnMsg = "SUCCESS";
        return returnMsg;
    }
}
