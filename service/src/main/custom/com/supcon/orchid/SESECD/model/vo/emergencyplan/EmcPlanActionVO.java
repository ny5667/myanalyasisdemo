package com.supcon.orchid.SESECD.model.vo.emergencyplan;

import com.supcon.orchid.SESECD.model.vo.common.PictureFileVO;
import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import com.supcon.orchid.SESECD.model.vo.common.SystemCodeVO;
import com.supcon.orchid.SESECD.model.vo.forfront.IconVO;
import com.supcon.orchid.SESECD.entities.SESECDAlarmAction;
import com.supcon.orchid.SESGISConfig.entities.SESGISConfigIconLibrary;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmcPlanAction;
import lombok.Data;

@Data
public class EmcPlanActionVO {
    /**
     * id
     */
    private Long id;
    /**
     * cid
     */
    private Long cid;
    /**
     * 指令坐标
      */
    private PointVO incidentPlanActLayer;
    /**
     * 行动地点
     */
    private String actionAddress;
    /**
     * 行动分类
     */
    private SystemCodeVO actionCatogory;
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
    /**
     * 行动图标
     */
    private IconVO iconPath;
    /**
     * 通讯组Id
     */
    private String sectionId;

    public static EmcPlanActionVO EmcPlanActionVOPO2VO(SESECDAlarmAction input) {
        if(input == null){
            return null;
        }
        SESWssEPEmcPlanAction action = input.getInstructions();
        EmcPlanActionVO vo = new EmcPlanActionVO();
        vo.setId(input.getId());
        vo.setCid(input.getCid());
        vo.setSectionId(action.getSectionId());
        vo.setIncidentPlanActLayer(PointVO.PointPO2VO(action.getIncidentPlanActLayer(), SESWssEPEmcPlanAction.MODEL_CODE  + "_", action.getId().toString()));
        vo.setActionAddress(action.getActionAddress());
        vo.setActionCatogory(SystemCodeVO.CreateSystemCodeVO(action.getActionCatogory()));
        vo.setActionDescription(action.getActionDescription());
        vo.setActionName(action.getActionName());
        vo.setIconPath(IconVO.IconPO2VO(action.getIconPathGis()));//图标
        vo.setTimeConsume(action.getTimeConsume());
        return vo;
    }
}
