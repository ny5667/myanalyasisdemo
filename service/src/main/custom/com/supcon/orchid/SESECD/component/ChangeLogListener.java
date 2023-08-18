package com.supcon.orchid.SESECD.component;

import com.supcon.orchid.SESECD.daos.SESECDChangeLogDao;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDChangeLog;
import com.supcon.orchid.SESECD.model.dto.common.ChangeLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 操作记录变更事件监听器
 */
@Component
@Slf4j
public class ChangeLogListener {
    //SESECD/alarmRecord/alarmRecord/releaseCommom  下达指令
    @Autowired
    private SESECDChangeLogDao changeLogDao;

    @EventListener(ChangeLogEvent.class)
    public void register(ChangeLogEvent userRegisterEvent) {
        log.error("ChangeLogListener 事件监听start");
        ChangeLogDTO changeLogDTO = userRegisterEvent.getChangeLogDTO();
        SESECDChangeLog changeLog = new SESECDChangeLog();
        SESECDAlmAlarmRecord record = new SESECDAlmAlarmRecord();
        record.setId(changeLogDTO.getEventId());
        changeLog.setEventId(record);
        changeLog.setBisType(changeLogDTO.getType());
        changeLog.setContent(changeLogDTO.getContent());
        changeLog.setAsscioateId(changeLogDTO.getRealId());
        changeLogDao.save(changeLog);
        log.error("ChangeLogListener 事件监听end");
    }

}
