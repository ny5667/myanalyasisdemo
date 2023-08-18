package com.supcon.orchid.SESECD.model.vo.forfront;

import com.supcon.orchid.SESECD.model.vo.common.PictureFileVO;
import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import com.supcon.orchid.SESECD.model.vo.common.StaffVO;
import com.supcon.orchid.SESECD.model.vo.common.SystemCodeVO;
import com.supcon.orchid.SESECD.entities.SESECDEmcSituation;
import lombok.Data;
import java.text.SimpleDateFormat;

@Data
public class EventSituationVO {

    //private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    /**
     * id
     */
    private Long id;

    /**
     * 上报人
     */
    private StaffVO reportPerson;

    /**
     * 发生时间
     */
    private String occursTime;


    /**
     * 事件
     */
    private EventDetailVO event;


    /**
     * 事态描述
     */
    private String description;

    /**
     * 受伤人数
     */
    private Integer woundedPerson;

    /**
     * 死亡人数
     */
    private Integer deathPerson;

    /**
     * 发生地点
     */
    private String position;

    /**
     * 坐标
     */
    private PointVO point;

    /**
     * 图标
     */
    private IconVO icon;

    /**
     * 态势状态
     */
    private SystemCodeVO situationType;

    /**
     * cid
     */
    private Long cid;

    /**
     * 来源
     */
    private SystemCodeVO source;

    private static final  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final EventSituationVO EventSituationPO2VO(SESECDEmcSituation input) {
        EventSituationVO vo  = new EventSituationVO();
        //String prefix = SESWssEREquipment.MODEL_CODE+"_";
        //equipmentVO.setSpatialId(prefix + equipment.getId());

        vo.setReportPerson(StaffVO.StaffPO2VO(input.getReportPerson()));
        vo.setOccursTime(simpleDateFormat.format(input.getOccursTime()));
        vo.setEvent(EventDetailVO.EventPO2VO(input.getEventId()));
        vo.setDescription(input.getDescribtion());
        vo.setWoundedPerson(input.getWoundedPerson());
        vo.setDeathPerson(input.getDeathPerson());
        vo.setPosition(input.getPosition());
        vo.setPoint(PointVO.PointPO2VO(input.getPoint(), SESECDEmcSituation.MODEL_CODE + "_" , input.getId().toString()));
        vo.setIcon(IconVO.IconPO2VO(input.getIconObjGis()));//图标
        vo.setSituationType(SystemCodeVO.CreateSystemCodeVO(input.getSituationType()));
        vo.setSource(SystemCodeVO.CreateSystemCodeVO(input.getSource()));//来源
        vo.setCid(input.getCid());
        vo.setId(input.getId());
        return vo;
    }


}
