package com.supcon.orchid.SESECD.model.vo.emergencyplan;

import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESECD.model.vo.common.PictureFileVO;
import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import com.supcon.orchid.SESECD.model.vo.common.StaffVO;
import com.supcon.orchid.SESECD.model.vo.common.SystemCodeVO;
import com.supcon.orchid.SESECD.model.vo.forfront.IconVO;
import com.supcon.orchid.SESECD.entities.SESECDAlarmAction;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmcPlanAction;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


@Data
public class AlarmActionVO {

    private static final Logger logger = LoggerFactory.getLogger(AlarmActionVO.class);

    /**
     * id
     */
    private Long id;
    /**
     * cid
     */
    private Long cid;
    /**
     * 是否已录入坐标
     */
    private Boolean isMapPoint;
    /**
     * 坐标
     */
    private PointVO mapPoint;
    /**
     * 接警ID
     */
    private String alarmId;
    /**
     * 预案id
     */
    private Long emergencyId;
    /**
     * 图标
     */
    private IconVO icon;
    /**
     * 指令
     */
    private EmcPlanActionVO instructions;
    /**
     * 责任人
     */
    private StaffVO owner;
    /**
     * 行动分类
     */
    private SystemCodeVO actionCatogory;
    /**
     * 指令状态
     */
    private SystemCodeVO commomState;
    /**
     * 行动地点
     */
    private String actionAddress;
    /**
     * 行动描述
     */
    private String actionDescription;
    /**
     * 行动名称
     */
    private String actionName;
    /**
     * 计划耗时(分)
     */
    private Integer timeConsume;

    public static final AlarmActionVO AlarmActionVOPO2VO(SESECDAlarmAction input) {
        if(input == null){
            return null;
        }
        AlarmActionVO vo = new AlarmActionVO();
        vo.setId(input.getId());
        vo.setCid(input.getCid());
        vo.setIsMapPoint(input.getIsMapPoint());
        PointVO pointVO = PointVO.PointPO2VO(input.getMapPoint(), SESECDAlarmAction.MODEL_CODE + "_",  input.getId().toString());
        vo.setMapPoint(pointVO);
        vo.setAlarmId(input.getAlarmId()!=null?input.getAlarmId().getId().toString():null);
        vo.setIcon(IconVO.IconPO2VO(input.getInstructions()!=null?input.getInstructions().getIconPathGis():null));//图标
        vo.setInstructions(EmcPlanActionVO.EmcPlanActionVOPO2VO(input));
        vo.setOwner(StaffVO.StaffPO2VO(Objects.isNull(input.getOwnPerson())? null:input.getOwnPerson().getPersonId()));
//        vo.setOwner(StaffVO.StaffPO2VO(input.getOwner()));
        vo.setActionCatogory(SystemCodeVO.CreateSystemCodeVO(input.getActionCatogory()));
        vo.setCommomState(SystemCodeVO.CreateSystemCodeVO(input.getCommomState()));
        vo.setActionAddress(input.getActionAddress());
        vo.setActionDescription(input.getActionDescription());
        vo.setActionName(input.getActionName());
        vo.setTimeConsume(input.getInstructions()!=null?input.getInstructions().getTimeConsume():null);
        return vo;
    }

    /**
     * 预案指令适配
     * @param input
     * @return
     */
    public static final AlarmActionVO AlarmActionVOPO2VO(SESWssEPEmcPlanAction input) {
        if(input == null){
            return null;
        }
        AlarmActionVO vo = new AlarmActionVO();
        vo.setId(input.getId());
        vo.setCid(input.getCid());
        PointVO pointVO = PointVO.PointPO2VO(input.getIncidentPlanActLayer(), SESWssEPEmcPlanAction.MODEL_CODE + "_",  input.getId().toString());//空间数据库
        vo.setMapPoint(pointVO);
        vo.setEmergencyId(input.getEmergencyPlanId().getId());//预案id
        vo.setIcon(IconVO.IconPO2VO(input.getIconPathGis()));//图标
//        vo.setInstructions(EmcPlanActionVO.EmcPlanActionVOPO2VO(input));
        vo.setOwner(StaffVO.StaffPO2VO(input.getSetionMember() == null? null:input.getSetionMember().getPersonId()));
        vo.setActionCatogory(SystemCodeVO.CreateSystemCodeVO(input.getActionCatogory()));
//        vo.setCommomState(SystemCodeVO.CreateSystemCodeVO(input.getCommomState()));
        vo.setActionAddress(input.getActionAddress());
        vo.setActionDescription(input.getActionDescription());
        vo.setActionName(input.getActionName());
        vo.setTimeConsume(input.getTimeConsume());
        return vo;
    }

    public static final AlarmActionVO PlanActionPO2VO(SESWssEPEmcPlanAction input) {

        if (input == null) {
            return null;
        }
        AlarmActionVO vo = new AlarmActionVO();
        vo.setActionAddress(input.getActionAddress());
        vo.setActionName(input.getActionName());
        vo.setActionDescription(input.getActionDescription());
        vo.setActionCatogory(SystemCodeVO.CreateSystemCodeVO(input.getActionCatogory()));
        vo.setIcon(IconVO.IconPO2VO(input.getIconPathGis()));//图标
        return vo;
    }
}
