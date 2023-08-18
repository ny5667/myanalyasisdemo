package com.supcon.orchid.SESECD.services.impl.forfront;

import com.supcon.orchid.SESECD.component.ChangeLogEvent;
import com.supcon.orchid.SESECD.constant.enums.ChangeLogBizTypeEnum;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.daos.SESECDChangeLogDao;
import com.supcon.orchid.SESECD.daos.SESECDEmcActionDao;
import com.supcon.orchid.SESECD.daos.SESECDEmcSituationDao;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDChangeLog;
import com.supcon.orchid.SESECD.entities.SESECDEmcAction;
import com.supcon.orchid.SESECD.entities.SESECDEmcSituation;
import com.supcon.orchid.SESECD.model.dto.common.ChangeLogDTO;
import com.supcon.orchid.SESECD.model.vo.common.PointVO;
import com.supcon.orchid.SESECD.model.vo.forfront.ChangeLogVO;
import com.supcon.orchid.SESECD.model.vo.forfront.EventDynamicTrendVO;
import com.supcon.orchid.SESECD.model.vo.forfront.TrackVO;
import com.supcon.orchid.SESECD.services.forfront.CustomSESECDForFrontService;
import com.supcon.orchid.SESECD.utils.DateUtils;
import com.supcon.orchid.services.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomSESECDForFrontServiceImpl extends BaseServiceImpl implements CustomSESECDForFrontService {

    private static final Logger logger = LoggerFactory.getLogger(CustomSESECDForFrontServiceImpl.class);

    @Autowired
    private SESECDAlmAlarmRecordDao eventdao;

    @Autowired
    private SESECDEmcActionDao actionDao;

    @Autowired
    private SESECDEmcSituationDao situationDao;

    @Autowired
    private SESECDChangeLogDao changeLogDao;

    @Autowired
    private ApplicationContext applicationContext;

    public static String CurrentLanguage = "";
    private static final String EventDynamicTrendTypeAction = "action";

    private static final String EventDynamicTrendTypeSituation = "situation";

    public static final SimpleDateFormat MySimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    {
        CurrentLanguage = this.getCurrentLanguage();
        logger.error("初始化语言:{}", CurrentLanguage);
    }

    /**
     * 获取某个事件的事件动态(应急行动和应急态势)
     *
     * @param eventId
     * @return
     */
    @Override
    public List<EventDynamicTrendVO> listEventDynamicTrends(Long eventId) {

        List<EventDynamicTrendVO> result = new ArrayList<>();

        //查询应急行动
        StringBuilder emergencyActionHQL = new StringBuilder();
        emergencyActionHQL.append(" from ").append(SESECDEmcAction.JPA_NAME).append(" where valid = 1 ").append(" and event_id = ").append(eventId);
        List<SESECDEmcAction> actionList = eventdao.findByHql(emergencyActionHQL.toString());
        if (CollectionUtils.isEmpty(actionList)) {
            logger.error("应急行动数量为空");
        } else {
            logger.error("应急行动数量:{}", actionList.size());
            actionList.stream().forEach(e -> {
                EventDynamicTrendVO vo = new EventDynamicTrendVO();
                vo.setEventTrendType(EventDynamicTrendTypeAction);
                vo.setContent(e.getDescription());//行动描述
                if (!StringUtils.isEmpty(e.getPoint())) { //点位
                    PointVO pointObj = PointVO.PointPO2VO(e.getPoint(), SESECDEmcAction.MODEL_CODE + "_", e.getId().toString());
                    vo.setPoint(pointObj);
                }
                if (null != e.getActionTime()) {
                    vo.setOccurTime(MySimpleDateFormat.format(e.getActionTime()));
                    vo.setOccurTimeForSort(e.getActionTime());
                }
                vo.setContentId(e.getId());
                result.add(vo);
            });
        }

        //查询应急态势
        StringBuilder emergencySituationHQL = new StringBuilder();
        emergencySituationHQL.append(" from ").append(SESECDEmcSituation.JPA_NAME).append(" where valid = 1 ").append(" and event_id = ").append(eventId);
        List<SESECDEmcSituation> situationList = situationDao.findByHql(emergencySituationHQL.toString());
        if (CollectionUtils.isEmpty(situationList)) {
            logger.error("应急态势数量为空");
        } else {
            logger.error("应急态势数量:{}", situationList.size());
            situationList.stream().forEach(e -> {
                EventDynamicTrendVO vo = new EventDynamicTrendVO();
                vo.setEventTrendType(EventDynamicTrendTypeSituation);
                vo.setContent(e.getDescribtion()); //态势描述
                if (!StringUtils.isEmpty(e.getPoint())) { //点位
                    PointVO pointObj = PointVO.PointPO2VO(e.getPoint(), SESECDEmcSituation.MODEL_CODE + "_", e.getId().toString());
                    vo.setPoint(pointObj);
                }
                if (null != e.getOccursTime()) {
                    vo.setOccurTime(MySimpleDateFormat.format(e.getOccursTime()));
                    vo.setOccurTimeForSort(e.getOccursTime());
                }
                vo.setContentId(e.getId());
                result.add(vo);
            });
        }

        result.sort((t1, t2) -> t2.getOccurTimeForSort().compareTo(t1.getOccurTimeForSort()));
        return result;
    }

    /**
     * 右侧动态面板操作记录
     * @param eventId
     * @return
     */
    @Override
    public List<ChangeLogVO> changeLog(Long eventId) {
        //获取该事件相关的操作记录
        StringBuilder hql = new StringBuilder();
        hql.append(" from ").append(SESECDChangeLog.JPA_NAME).append(" where valid = 1 ").append(" and event_id = ").append(eventId).append("order by create_time desc");
        List<SESECDChangeLog> logs = changeLogDao.findByHql(hql.toString());
        List<ChangeLogVO> ret = new ArrayList<>();
        for (SESECDChangeLog sesecdChangeLog : logs) {
            ChangeLogVO changeLogVO = new ChangeLogVO();
            changeLogVO.setBizType(sesecdChangeLog.getBisType());
            changeLogVO.setCreateTime(DateUtils.date2Str(sesecdChangeLog.getCreateTime()));
            changeLogVO.setContent(sesecdChangeLog.getContent());
            changeLogVO.setRealId(sesecdChangeLog.getRelateId());
            changeLogVO.setEventId(eventId);
            //若行动 态势相关 则取出相关的点  其他的暂不关联点
            if (sesecdChangeLog.getAsscioateId() != null && sesecdChangeLog.getBisType() != null) {
                if (sesecdChangeLog.getBisType().equalsIgnoreCase(ChangeLogBizTypeEnum.ACTION.getCode())) {
                    //关联行动的点
                    SESECDEmcAction sesecdEmcAction = actionDao.get(sesecdChangeLog.getAsscioateId());
                    if (null != sesecdEmcAction && !StringUtils.isEmpty(sesecdEmcAction.getPoint())) { //点位
                        PointVO pointObj = PointVO.PointPO2VO(sesecdEmcAction.getPoint(), SESECDEmcAction.MODEL_CODE + "_", sesecdEmcAction.getId().toString());
                        changeLogVO.setPoint(pointObj);
                    }
                } else if (sesecdChangeLog.getBisType().equalsIgnoreCase(ChangeLogBizTypeEnum.SITUATION.getCode())) {
                    SESECDEmcSituation situation = situationDao.get(sesecdChangeLog.getAsscioateId());
                    if (null != situation && !StringUtils.isEmpty(situation.getPoint())) { //点位
                        PointVO pointObj = PointVO.PointPO2VO(situation.getPoint(), SESECDEmcAction.MODEL_CODE + "_", situation.getId().toString());
                        changeLogVO.setPoint(pointObj);
                    }
                }
            }

            ret.add(changeLogVO);
        }
        return ret;
    }


    //-----------------------------------------------公共方法区---------------------------------------------


}
