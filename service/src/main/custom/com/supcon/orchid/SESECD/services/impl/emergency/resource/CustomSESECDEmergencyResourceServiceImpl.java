package com.supcon.orchid.SESECD.services.impl.emergency.resource;

import com.supcon.orchid.SESECD.daos.SESECDAlarmEnenetrelDao;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.entities.SESECDAlarmEnenetrel;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.SESECD.services.emerency.resource.CustomSESECDEmergencyResourceService;
import com.supcon.orchid.SESWssER.DTO.ExpertDTO;
import com.supcon.orchid.SESWssER.client.ISESWssERExpertClient;
import com.supcon.orchid.ec.services.MsModuleRelationService;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CustomSESECDEmergencyResourceServiceImpl extends BaseServiceImpl implements CustomSESECDEmergencyResourceService {

    @Autowired
    private MsModuleRelationService msModuleRelationService;


    @Autowired
    private SESECDAlmAlarmRecordDao almAlarmRecordDao;

    @Autowired
    private SESECDAlarmEnenetrelDao alarmEnenetrelDao;

    @Autowired
    private ISESWssERExpertClient expertClient;

    /**
     * 通过应急事件所拥有的事故类型过滤查询应急专家
     *
     * @param eventId
     * @return
     */
    @Override
    public BaseInfoVO getEmerExportsByEmerEventId(Long eventId) {
        // 判断应急资源服务是否启动
        if (!msModuleRelationService.checkModuleStatus("SESWssER")) {
            return BaseInfoVO
                    .fail(InternationalResource.get("SESECD.custom.randon1578306204055", getCurrentLanguage()));
        }
        String typeIds = "";
        List<ExpertDTO> emerExpertsByType = null;
        if (null != eventId) {
            SESECDAlmAlarmRecord almAlarmRecord = almAlarmRecordDao.get(eventId);
//            List<SESECDAlarmEnenetrel> AlarmEnenetrels = alarmEnenetrelDao.findByProperty("alarmId", almAlarmRecord);
            List<SESECDAlarmEnenetrel> AlarmEnenetrels = alarmEnenetrelDao.findByHql("from " + SESECDAlarmEnenetrel.JPA_NAME + " where valid = 1 and alarmId = ?0", new Object[]{almAlarmRecord});
            if (null != AlarmEnenetrels && AlarmEnenetrels.size() > 0) {
                for (SESECDAlarmEnenetrel sesecdAlarmEnenetrel : AlarmEnenetrels) {
                    Long id = sesecdAlarmEnenetrel.getEnenetrel().getId();
                    typeIds += id + ",";
                }
                if (typeIds.length() > 0) {
                    typeIds = typeIds.substring(0, typeIds.length() - 1);
                }
                emerExpertsByType = expertClient.getEmerExpertsByType(typeIds);
            }
        }
        return BaseInfoVO.ok(emerExpertsByType);
    }

}
