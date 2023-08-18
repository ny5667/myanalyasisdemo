package com.supcon.orchid.SESECD.model.vo.forfront;

import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import com.supcon.orchid.SESECD.model.vo.common.StaffVO;
import com.supcon.orchid.SESECD.model.vo.common.SystemCodeVO;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import lombok.Data;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 事件详情
 */
@Data
public class EventDetailVO {

    public static final SimpleDateFormat MySimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * id
     */
    private Long id;

    /**
     * 事件名称
     */
    private String accidentName;


    /**
     * 是否已结束
     */
    private Boolean isOver;

    /**
     * 事发时间
     */
    private String happenTime;

    /**
     * 事件类型
     */
    private SystemCodeVO eventType;


    /**
     * 事件描述
     */
    private String description;

    /**
     * 受伤人数
     */
    private Integer wounderPerson; //受伤人数

    /**
     * 接警人
     */
    private StaffVO receiver;

    /**
     * 处理状态
     */
    private SystemCodeVO processState;

    /**
     * 接警时间
     */
    private String rectime;

    /**
     * 发生位置
     */
    private String position;
    //事发公司hpmCompany

    /**
     * 事发公司
     */
    private String hmpCompanyName;

    /**
     * 事发部门
     */
    private String hmpDepartmentName;

    /**
     * 点位
     */
    private PointVO point;

    /**
     * 预案Id列表
     */
    private List<Long> planIds;

    /**
     * 转接警Id
     */
    private Long alarmId;

    /**
     * 事件来源  001 人工填报  002 人员SOS报警   003视频监控报警  004危险源监控报警
     */
    private String source;

    //事件po转vo
    public static EventDetailVO EventPO2VO(SESECDAlmAlarmRecord input) {
        EventDetailVO result = new EventDetailVO();
        result.setId(input.getId());
        result.setEventType(SystemCodeVO.CreateSystemCodeVO(input.getEventType()));
        result.setAccidentName(input.getAccidentName());
        if (null != input.getHappenTime()) {
            result.setHappenTime(MySimpleDateFormat.format(input.getHappenTime()));
        }
        result.setIsOver(input.getIsOver());
        result.setDescription(input.getDescription());
        result.setWounderPerson(input.getWounderPerson());
        if (null != input.getRectime()) {
            result.setRectime(MySimpleDateFormat.format(input.getRectime()));
        }
        if (null != input.getReceiver()) {
            result.setReceiver(StaffVO.StaffPO2VO(input.getReceiver()));
        }
        if (null != input.getProcessState()) {
            result.setProcessState(SystemCodeVO.CreateSystemCodeVO(input.getProcessState()));
        }
        result.setPosition(input.getPosition());
        if (null != input.getHpmDepartment()) {
            result.setHmpDepartmentName(input.getHpmDepartment().getName());
        }
        if (null != input.getHpnCompany()) {
            result.setHmpCompanyName(input.getHpnCompany().getName());
        }

        return result;
    }

}
