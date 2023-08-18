package com.supcon.orchid.SESECD.services.impl.emergency.address.book;

import com.supcon.orchid.SESEAB.DTO.MemberDTO;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.daos.SESECDEmePlanObjDao;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDEmePlanObj;
import com.supcon.orchid.SESECD.model.vo.MemberVO;
import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.SESECD.services.emerency.address.book.CustomSESECDEmergencyAddressBookService;
import com.supcon.orchid.SESECD.services.converged.comm.CustomSESECDCommunicationService;
import com.supcon.orchid.SESWssEP.client.ISESWssEPEmePeopleGroupClient;
import com.supcon.orchid.ec.services.MsModuleRelationService;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CustomSESECDEmergencyAddressBookServiceImpl extends BaseServiceImpl implements CustomSESECDEmergencyAddressBookService {

    @Autowired
    private MsModuleRelationService msModuleRelationService;


    @Autowired
    private SESECDAlmAlarmRecordDao almAlarmRecordDao;

    @Autowired
    private SESECDEmePlanObjDao emePlanObjDao;

    @Autowired
    private ISESWssEPEmePeopleGroupClient emePeopleGroupClient;


    @Autowired
    private CustomSESECDCommunicationService communicationService;

    /**
     * 通过应急事件查询应急通讯小组成员信息
     *
     * @param eventId
     * @return
     */
    @Override
    public BaseInfoVO getEmerStaffByEmerEventId(Long eventId) {
        // 判断应急预案服务是否启动
        if (!msModuleRelationService.checkModuleStatus(ConstDict.MODULE_CODE_EP)) {
            return BaseInfoVO
                    .fail(InternationalResource.get("SESECD.custom.randon1578295940691", getCurrentLanguage()));
        }
        List<MemberDTO> staffIdsByPlanIds = null;
        List<MemberVO> personOnlineStatus = null;
        SESECDAlmAlarmRecord almAlarmRecord = almAlarmRecordDao.get(eventId);
        if (null != almAlarmRecord) {
//            List<SESECDEmePlanObj> emePlanObjs = emePlanObjDao.findByProperty("alarmId", almAlarmRecord);
            List<SESECDEmePlanObj> emePlanObjs = emePlanObjDao.findByHql("from " + SESECDEmePlanObj.JPA_NAME + " where valid = 1 and alarmId = ?0", new Object[]{almAlarmRecord});
            if (null != emePlanObjs && emePlanObjs.size() > 0) {
                String planIds = "";
                for (SESECDEmePlanObj sesecdEmePlanObj : emePlanObjs) {
                    if (null != sesecdEmePlanObj && null != sesecdEmePlanObj.getPlanObj()) {
                        planIds = sesecdEmePlanObj.getPlanObj().getId() + ",";
                    }
                }
                if (planIds.length() > 0) {
                    planIds = planIds.substring(0, planIds.length() - 1);
                }
                staffIdsByPlanIds = emePeopleGroupClient.getMembersByPlanIds(planIds);
                personOnlineStatus = communicationService.getPersonOnlineStatus(staffIdsByPlanIds);
            }
        }
        return BaseInfoVO.ok(personOnlineStatus);
    }

}
