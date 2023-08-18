package com.supcon.orchid.SESECD.model.vo.project.sgm.report.event;

import com.supcon.orchid.SESECD.model.vo.common.CompanyVO;
import com.supcon.orchid.SESECD.model.vo.common.DepartmentVO;
import com.supcon.orchid.SESECD.model.vo.common.StaffVO;
import com.supcon.orchid.SESECD.model.vo.common.SystemCodeVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SESECDAlarmRecordVO {

    /**
     * id
     */
    private Long id;

    /**
     * 事件名称
     */
    private String accidentName;

    /**
     * 事发时间
     */
    private Date happenTime;

    /**
     * 发生位置
     */
    private String position;

    /**
     * 受伤人数
     */
    private Integer wounderPerson;

    /**
     * 事件描述
     */
    private String description;

    /**
     * 坐标
     */
    private String mapPoint;

    /**
     * 接警人
     */
    private StaffVO receiver;

    /**
     * 接警时间
     */
    private Date rectime;

    /**
     * 事发公司
     */
    private CompanyVO hpnCompany;

    /**
     * 处理状态
     */
    private SystemCodeVO processState;

    /**
     * 报警等级
     */
    private SystemCodeVO alarmLevel;

    /**
     * 接警人
     */
    private StaffVO alarmPerson;

    /**
     * 上报人电话
     */
    private String alarmPhone;

    /**
     * 事发部门
     */
    private DepartmentVO hpmDepartment;

    /**
     * 死亡人数
     */
    private Integer deathPerson;

    /**
     * 处理记录
     */
    private String process;

    /**
     * 事故类型
     */
    private List<SESWssERAccidentClassVO> accidentClasses;

    /**
     * 应急预案
     */
    private List<SESWssEPEmergencyPlanVO> emePlans;

    /*-----------------------------------------省国贸上报字段，非数据库字段，开始----------------------------------------*/

    /**
     * 上级公司编码
     */
    private String parentCompanyCode;

    /*-----------------------------------------省国贸上报字段，非数据库字段，结束----------------------------------------*/

    /*-----------------------------------------省国贸上报字段，数据库字段，开始----------------------------------------*/

    /**
     * 二级公司应急预案
     */
    private String twoEPInfo;

    /**
     * 二级公司
     */
    private CompanyVO twoCompany;

    /**
     * 三级公司应急预案
     */
    private String threeEPInfo;

    /**
     * 三级公司
     */
    private CompanyVO threeCompany;

    /*-----------------------------------------省国贸上报字段，数据库字段，结束----------------------------------------*/

}
