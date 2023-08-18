package com.supcon.orchid.SESECD.model.vo.forfront;

import com.supcon.orchid.SESECD.model.vo.common.*;
import com.supcon.orchid.SESECD.entities.SESECDEmcAction;
import lombok.Data;
import java.text.SimpleDateFormat;
import java.util.List;

@Data
public class EventActionVO {

    /**
     * id
     */
    private Long id;

    /**
     * 行动描述
     */
    private String description;

    /**
     * 事件
     */
    private EventDetailVO event;

    /**
     * 行动状态
     */
    private SystemCodeVO actionState;

    /**
     * 行动地点
     */
    private String actionAddr;

    /**
     * 坐标
     */
    private PointVO point;

    /**
     * 行动时间
     */
    private String actionTime;

    /**
     * 图标
     */
    private IconVO icon;

    /**
     * 行动分类
     */
    private SystemCodeVO actionCatogory;

    /**
     * 责任人
     */
    private List<MainPeopleVO> ownMainPeople;

    /**
     * 责任单位
     */
    private List<MainDepartVO> ownMainDepart;

    /**
     * 跟踪记录
     */
    private String trackRecord;

    //责任单位
    //附件


    public static EventActionVO   ActionPO2VO(SESECDEmcAction input) {
        SimpleDateFormat MySimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        EventActionVO vo = new EventActionVO();
        vo.setId(input.getId());
        vo.setDescription(input.getDescription());
        vo.setActionState(SystemCodeVO.CreateSystemCodeVO(input.getActionState()));
        vo.setActionAddr(input.getActionAddr());
        ///logger.error(input.getPoint());
        vo.setPoint(PointVO.PointPO2VO(input.getPoint(), SESECDEmcAction.MODEL_CODE + "_", input.getId().toString()));
        /**
         {"coordinates":[{"lon":120.13925978374522,"lat":30.182058619322106,"hei":0.40078941475907687}],"buildingPatchId":null,"floor":null,"layerCode":"emeActionLayer","oldLayerCode":"emeActionLayer"}
         */
        vo.setActionTime(MySimpleDateFormat.format(input.getActionTime()));
        vo.setIcon(IconVO.IconPO2VO(input.getIconObjGis()));
        vo.setActionCatogory(SystemCodeVO.CreateSystemCodeVO(input.getActionCatogory()));
        //vo.setOwnPersonIds(input.getOwnPerson()); 深坑 这个字段不用的
        vo.setTrackRecord(input.getTrackRecord());



        return vo;
    }
}
