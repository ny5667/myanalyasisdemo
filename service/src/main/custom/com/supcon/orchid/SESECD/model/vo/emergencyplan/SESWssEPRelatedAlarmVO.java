package com.supcon.orchid.SESECD.model.vo.emergencyplan;

import com.supcon.orchid.SESECD.entities.SESECDEmePlanObj;
import com.supcon.orchid.SESECD.model.vo.common.CompanyVO;
import com.supcon.orchid.SESECD.model.vo.common.DepartmentVO;
import com.supcon.orchid.SESECD.model.vo.common.StaffVO;
import com.supcon.orchid.SESECD.model.vo.common.SystemCodeVO;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmergencyPlan;
import com.supcon.orchid.foundation.entities.SystemCode;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Data
public class SESWssEPRelatedAlarmVO {

    /**
     * id
     */
    private Long id;
    /**
     * cid
     */
    private Long cid;

    /**
     * 预案名称
     */
    private String planName;

    /**
     * 单据状态
     */
    private String documentStatus;

    /**
     * 编制时间
     */
    private String editTime;

    /**
     * 预案类型
     */
    private SystemCodeVO planType;

    /**
     * 预案版本号
     */
    private String planVersion;

    /**
     * 审核人
     */
    private StaffVO verifyPerson;

    /**
     * 关联指令
     */
    List<AlarmActionVO> alarmAction;

    /**
     * 预案匹配值
     */
    private Integer score = 0;

    /**
     * 预案等级
     */
    private SystemCode planRank;

    /**
     *  事件是否关联预案   1:关联    2: 未关联
     */
    private Integer isConnected =2;

    private Boolean isClose = false;

    /**
     * 批准人
     */
    private StaffVO approvePerson;
    /**
     * 所属公司
     */
    private CompanyVO belongCompany;
    /**
     * 所属部门
     */
   private DepartmentVO belongDepartment;
    /**
     * 生成审核预案
     */
    private Boolean createAuditPlan;
    /**
     * 编制部门
     */
    private DepartmentVO editDepartment;
    /**
     * 签发人
     */
    private StaffVO issuePerson;
    /**
     * 预案附件
     */
//    private String planFile;
//    /**
//     * 通讯组
//     */
//    List<EabSectionVO> eabSectionVOS;

    private static final  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SESWssEPRelatedAlarmVO SESWssEPRelatedAlarmPO2VO(SESWssEPEmergencyPlan input, List<AlarmActionVO> alarmActionVOS) {
        if(input == null){
            return null;
        }
        SESWssEPRelatedAlarmVO vo = new SESWssEPRelatedAlarmVO();
        vo.setId(input.getId());
        vo.setCid(input.getCid());
        vo.setPlanName(input.getPlanName());
        vo.setDocumentStatus(input.getDocumentStatus());
        vo.setEditTime(null!=input.getEditTime()?simpleDateFormat.format(input.getEditTime()):null);
        vo.setDocumentStatus(input.getDocumentStatus());
        vo.setPlanType(SystemCodeVO.CreateSystemCodeVO(input.getPlanType()));
        vo.setPlanVersion(input.getPlanVersion());
        vo.setVerifyPerson(StaffVO.StaffPO2VO(input.getVerifyPerson()));
        vo.setScore(1);
        vo.setAlarmAction(alarmActionVOS);
               vo.setCreateAuditPlan(input.getCreateAuditPlan());
        vo.setApprovePerson(StaffVO.StaffPO2VO(input.getApprovePerson()));
        vo.setBelongCompany(CompanyVO.CompanyVOPO2VO(input.getBelongCompany()));
        vo.setBelongDepartment(DepartmentVO.DepartmentPO2VO(input.getBelongDepartment()));
        vo.setEditDepartment(DepartmentVO.DepartmentPO2VO(input.getEditDepartment()));
        vo.setIssuePerson(StaffVO.StaffPO2VO(input.getIssuePerson()));
//vo.setPlanFile();
//vo.setEabSectionVOS(eabSectionVOS.get(input.getId()));
        return vo;
    }
    public static final SESWssEPRelatedAlarmVO SESWssEPRelatedAlarmPO2VO(SESWssEPEmergencyPlan input, List<AlarmActionVO> alarmActionVOS,Map<Long, List<SESECDEmePlanObj>> planObjMap ) {
        if(input == null){
            return null;
        }
        SESWssEPRelatedAlarmVO vo = new SESWssEPRelatedAlarmVO();
        vo.setId(input.getId());
        vo.setCid(input.getCid());
        vo.setPlanName(input.getPlanName());
        vo.setDocumentStatus(input.getDocumentStatus());
        vo.setEditTime(null!=input.getEditTime()?simpleDateFormat.format(input.getEditTime()):null);
        vo.setPlanType(SystemCodeVO.CreateSystemCodeVO(input.getPlanType()));
        vo.setPlanVersion(input.getPlanVersion());
        vo.setVerifyPerson(StaffVO.StaffPO2VO(input.getVerifyPerson()));
        vo.setScore(1);
        vo.setIsConnected(1);
        vo.setAlarmAction(alarmActionVOS);
        Boolean isClose = planObjMap.get(input.getId()).get(0).getIsClose();
        vo.setIsClose( isClose == null ? false :isClose);
        if (!ObjectUtils.isEmpty(isClose) && isClose)  vo.setScore(vo.getScore() +1); //开启状态的预案优先展示
       vo.setCreateAuditPlan(input.getCreateAuditPlan());
       vo.setApprovePerson(StaffVO.StaffPO2VO(input.getApprovePerson()));
        vo.setBelongCompany(CompanyVO.CompanyVOPO2VO(input.getBelongCompany()));
        vo.setBelongDepartment(DepartmentVO.DepartmentPO2VO(input.getBelongDepartment()));
        vo.setEditDepartment(DepartmentVO.DepartmentPO2VO(input.getEditDepartment()));
        vo.setIssuePerson(StaffVO.StaffPO2VO(input.getIssuePerson()));
//vo.setPlanFile();
//vo.setEabSectionVOS(eabSectionVOS.get(input.getId()));
        return vo;
    }

    public static final SESWssEPRelatedAlarmVO SESWssEPRelatedAlarmPO2VO(SESWssEPEmergencyPlan input, List<AlarmActionVO> alarmActionVOS ,Integer score) {
        if(input == null){
            return null;
        }
        SESWssEPRelatedAlarmVO vo = new SESWssEPRelatedAlarmVO();
        vo.setId(input.getId());
        vo.setCid(input.getCid());
        vo.setPlanName(input.getPlanName());
        vo.setDocumentStatus(input.getDocumentStatus());
        vo.setEditTime(null!=input.getEditTime()?simpleDateFormat.format(input.getEditTime()):null);
        vo.setDocumentStatus(input.getDocumentStatus());
        vo.setPlanType(SystemCodeVO.CreateSystemCodeVO(input.getPlanType()));
        vo.setPlanVersion(input.getPlanVersion());
        vo.setVerifyPerson(StaffVO.StaffPO2VO(input.getVerifyPerson()));
        vo.setScore(score);
        vo.setAlarmAction(alarmActionVOS);
        vo.setCreateAuditPlan(input.getCreateAuditPlan());
        vo.setApprovePerson(StaffVO.StaffPO2VO(input.getApprovePerson()));
        vo.setBelongCompany(CompanyVO.CompanyVOPO2VO(input.getBelongCompany()));
        vo.setBelongDepartment(DepartmentVO.DepartmentPO2VO(input.getBelongDepartment()));
        vo.setEditDepartment(DepartmentVO.DepartmentPO2VO(input.getEditDepartment()));
        vo.setIssuePerson(StaffVO.StaffPO2VO(input.getIssuePerson()));
//vo.setPlanFile();
//vo.setEabSectionVOS(eabSectionVOS.get(input.getId()));
        return vo;
    }

    public static final SESWssEPRelatedAlarmVO SESWssEPRelatedAlarmPO2VO(SESWssEPEmergencyPlan input) {
        if(input == null){
            return null;
        }
        SESWssEPRelatedAlarmVO vo = new SESWssEPRelatedAlarmVO();
        vo.setId(input.getId());
        vo.setCid(input.getCid());
        vo.setPlanType(SystemCodeVO.CreateSystemCodeVO(input.getPlanType()));
        vo.setPlanVersion(input.getPlanVersion());
        vo.setVerifyPerson(StaffVO.StaffPO2VO(input.getVerifyPerson()));
        vo.setPlanName(input.getPlanName());
        vo.setDocumentStatus(input.getDocumentStatus());
        vo.setEditTime(null!=input.getEditTime()?simpleDateFormat.format(input.getEditTime()):null);
        vo.setDocumentStatus(input.getDocumentStatus());
//        vo.setPlanFile();
        vo.setIssuePerson(StaffVO.StaffPO2VO(input.getIssuePerson()));
        vo.setEditDepartment(DepartmentVO.DepartmentPO2VO(input.getEditDepartment()));
        vo.setApprovePerson(StaffVO.StaffPO2VO(input.getApprovePerson()));
        vo.setBelongCompany(CompanyVO.CompanyVOPO2VO(input.getBelongCompany()));
        vo.setBelongDepartment(DepartmentVO.DepartmentPO2VO(input.getBelongDepartment()));
//        vo.setEabSectionVOS(eabSectionVOS.get(input.getId()));
        vo.setCreateAuditPlan(input.getCreateAuditPlan());
        return vo;
    }


    public static final SESWssEPRelatedAlarmVO SESWssEPRelatedAlarmPO2VO(SESWssEPEmergencyPlan input,Integer connected) {
        if(input == null){
            return null;
        }
        SESWssEPRelatedAlarmVO vo = new SESWssEPRelatedAlarmVO();
        vo.setId(input.getId());
        vo.setCid(input.getCid());
        vo.setPlanName(input.getPlanName());
        vo.setDocumentStatus(input.getDocumentStatus());
        vo.setEditTime(null!=input.getEditTime()?simpleDateFormat.format(input.getEditTime()):null);
        vo.setPlanType(SystemCodeVO.CreateSystemCodeVO(input.getPlanType()));
        vo.setPlanVersion(input.getPlanVersion());
        vo.setVerifyPerson(StaffVO.StaffPO2VO(input.getVerifyPerson()));
        vo.setIsConnected(connected);
        vo.setCreateAuditPlan(input.getCreateAuditPlan());
        vo.setApprovePerson(StaffVO.StaffPO2VO(input.getApprovePerson()));
        vo.setBelongCompany(CompanyVO.CompanyVOPO2VO(input.getBelongCompany()));
        vo.setBelongDepartment(DepartmentVO.DepartmentPO2VO(input.getBelongDepartment()));
        vo.setEditDepartment(DepartmentVO.DepartmentPO2VO(input.getEditDepartment()));
        vo.setIssuePerson(StaffVO.StaffPO2VO(input.getIssuePerson()));
        return vo;
    }
}
