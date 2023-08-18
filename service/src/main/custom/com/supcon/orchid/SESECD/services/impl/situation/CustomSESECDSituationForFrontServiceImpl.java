package com.supcon.orchid.SESECD.services.impl.situation;

import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESECD.component.ChangeLogEvent;
import com.supcon.orchid.SESECD.constant.enums.ChangeLogBizTypeEnum;
import com.supcon.orchid.SESECD.daos.SESECDEmcSituationDao;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDEmcSituation;
import com.supcon.orchid.SESECD.model.dto.common.ChangeLogDTO;
import com.supcon.orchid.SESECD.model.vo.common.SystemCodeVO;
import com.supcon.orchid.SESECD.model.vo.forfront.EventSituationVO;
import com.supcon.orchid.SESECD.services.impl.forfront.CustomSESECDForFrontServiceImpl;
import com.supcon.orchid.SESECD.services.situation.CustomSESECDSituationForFrontService;
import com.supcon.orchid.SESECD.services.map.CustomSESECDSavePointService;
import com.supcon.orchid.SESECD.services.situation.CustomSESECDEmcSituationService;
import com.supcon.orchid.SESGISConfig.client.ICustomGisAnalysisClient;
import com.supcon.orchid.SESGISConfig.entities.SESGISConfigIconLibrary;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.services.SystemCodeService;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.orm.entities.ISystemCode;
import com.supcon.orchid.services.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomSESECDSituationForFrontServiceImpl extends BaseServiceImpl implements CustomSESECDSituationForFrontService {


    private static final Logger logger = LoggerFactory.getLogger(CustomSESECDForFrontServiceImpl.class);

    @Autowired
    private SESECDEmcSituationDao situationDao;

    @Autowired
    private ICustomGisAnalysisClient iCustomGisAnalysisClient;

    @Autowired
    private CustomSESECDEmcSituationService customSESECDEmcSituationService;

    @Autowired
    private CustomSESECDSavePointService customSESECDSavePointService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SystemCodeService systemCodeService;

    @Override
    public List<EventSituationVO> listEventSituation(Long eventId) {
        List<EventSituationVO> eventSituationVOS = new ArrayList<>();

        //查询应急态势
        StringBuilder emergencySituationHQL = new StringBuilder();
        emergencySituationHQL.append(" from ").append(SESECDEmcSituation.JPA_NAME).append(" where valid = 1 ").append(" and event_id = ").append(eventId);
        List<SESECDEmcSituation> situationList = situationDao.findByHql(emergencySituationHQL.toString());
        if (CollectionUtils.isEmpty(situationList)) {
            logger.error("应急态势数量为空");
        } else {
            logger.error("应急态势数量:{}", situationList.size());
            eventSituationVOS = situationList.stream().map(
                    (SESECDEmcSituation input) -> EventSituationVO.EventSituationPO2VO(input)
            ).collect(Collectors.toList());
        }
        return eventSituationVOS;
    }

    /**
     * 增加应急态势
     *
     * @param inputVO
     * @return
     * @throws Exception
     */
    @Override
    public Integer addSituation(EventSituationVO inputVO) throws Exception {

        inputVO.setId(null);//保证是add而不是update

        SESECDEmcSituation situationPO = new SESECDEmcSituation();
        //上報人 必填
        if (null == inputVO.getReportPerson()) {
            throw new Exception("report person is missing");
        }
        Staff reportPerson = new Staff();
        reportPerson.setId(inputVO.getReportPerson().getId());
        situationPO.setReportPerson(reportPerson);

        situationPO.setDescribtion(inputVO.getDescription());

        //来源
        if (null != inputVO.getSource() && null != inputVO.getSource().getId()) {
            situationPO.setSource(SystemCodeVO.CreateSystemCodePOById(inputVO.getSource().getId()));
        }
        //發生時間,必填
        if (!StringUtils.isEmpty(inputVO.getOccursTime())) {
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date occursTimeDate = fmt.parse(inputVO.getOccursTime());
            situationPO.setOccursTime(occursTimeDate);
        } else {
            throw new Exception(" missing occurstime");
        }

        if (null == inputVO.getEvent()) {
            throw new Exception("eventid missing");
        }
        SESECDAlmAlarmRecord event = new SESECDAlmAlarmRecord();//事件
        event.setId(inputVO.getEvent().getId());
        situationPO.setEventId(event);
        situationPO.setWoundedPerson(inputVO.getWoundedPerson());//受傷人數
        situationPO.setDeathPerson(inputVO.getDeathPerson());//死亡人數
        situationPO.setPosition(inputVO.getPosition());

        //先保存態勢，拿到id然後spatialid就是"mode_"加態勢id
        //{"coordinates":[{"lon":120.13865283703612,"lat":30.181726220261947,"hei":0.40510041720307344}],"buildingPatchId":null,"floor":null,"layerCode":"emeSituationLayer","oldLayerCode":"emeSituationLayer"}
        //PointVO dbPointJSOn = PointVO.PointVO2DBJSONVO(inputVO.getPoint());
        if (null != inputVO.getPoint()) {//坐标非必须
            situationPO.setPoint(JSON.toJSONString(inputVO.getPoint()));//這個point的string保存的就是這個vo的json字符串,但真正存儲經緯度是在gisanalysis服務裏存儲，到pg中
        }

        //图标 必填
        if (null != inputVO.getIcon() && null != inputVO.getIcon().getId()) {
            SESGISConfigIconLibrary iconObj = new SESGISConfigIconLibrary();
            iconObj.setId(inputVO.getIcon().getId());
            situationPO.setIconObjGis(iconObj);
        } else {
            throw new Exception("missing icon");
        }
        String status = "-";
        if (null != inputVO.getSituationType()) {
            situationPO.setSituationType(SystemCodeVO.CreateSystemCodePOById(inputVO.getSituationType().getId()));
            //"code": "002", "entityCode": "SESECD_situation_type",
            ISystemCode systemCodeByID = systemCodeService.getSystemCodeByID(inputVO.getSituationType().getId());
            status = InternationalResource.get(systemCodeByID.getValue(), getCurrentLanguage());
        }
        situationPO.setCid(getCurrentCompanyId());
        logger.error("保存前態勢的日志:{}", JSON.toJSONString(situationPO));
        //開始保存態勢
        situationDao.save(situationPO);
        situationDao.flush();
        logger.error("态势保存完成");
        logger.error("态势的id:{}", situationPO.getId());

        if (null == situationPO.getId()) {
            logger.error("未获取到保存的态势id,点不保存了");
            throw new Exception("save failed cannot get situation id");
        }

        logger.error("开始保存点");
        if (null != inputVO.getPoint()) {
            String spatialId = SESECDEmcSituation.MODEL_CODE + "_" + situationPO.getId();
            customSESECDSavePointService.savePoint2PG(spatialId, inputVO.getPoint(), "emeSituationLayer");
        }
        //新增态势操作日志
        ChangeLogDTO logDTO = ChangeLogDTO.builder()
                .content("【应急态势】："+ inputVO.getDescription() +", 受伤人数： "+inputVO.getWoundedPerson()+", 死亡人数： "+inputVO.getDeathPerson()+",  状态： "+status)
                .eventId(event.getId())
                .type(ChangeLogBizTypeEnum.SITUATION.getCode())
                .realId(situationPO.getId()).build();
        applicationContext.publishEvent(new ChangeLogEvent(this,logDTO));
        //发送消息
        customSESECDEmcSituationService.customAfterSaveEmcSituation(situationPO, null);
        return 1;
    }

    @Override
    public Integer updateSituation(EventSituationVO inputVO) throws Exception {
        String content = "【应急态势】：";
        if (inputVO.getId() == null) {
            return 0;
        }
        //先查出来
        StringBuilder emergencySituationHQL = new StringBuilder();
        emergencySituationHQL.append(" from ").append(SESECDEmcSituation.JPA_NAME).append(" where valid = 1 ").append(" and id = ").append(inputVO.getId());
        List<SESECDEmcSituation> situationList = situationDao.findByHql(emergencySituationHQL.toString());
        if (CollectionUtils.isEmpty(situationList)) {
            logger.error("未查询到该应急态势");
            return 0;
        }
        SESECDEmcSituation po = situationList.get(0);
        logger.error("态势的id:{}", po.getId());
        //已发布态势无法修改  ESECD_situation_type/001 已发布  ESECD_situation_type/002未发布
        if (po.getSituationType().getId().equals("ESECD_situation_type/001")) {
            logger.error("该态势已发布，不允许修改");
            throw new Exception("已发布态势无法修改");
        }

        //开始执行修改
        //上報人  ,如果不为空,执行修改
        if (null != inputVO.getReportPerson()) {
            Staff reportPerson = new Staff();
            reportPerson.setId(inputVO.getReportPerson().getId());
            po.setReportPerson(reportPerson);
        }

        if (!StringUtils.isEmpty(inputVO.getDescription())) {
            po.setDescribtion(inputVO.getDescription());
        }

        //发生时间
        if (!StringUtils.isEmpty(inputVO.getOccursTime())) {
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date occursTimeDate = fmt.parse(inputVO.getOccursTime());
            po.setOccursTime(occursTimeDate);
        }

        //态势所属的事件不需要修改，因为不可能会修改所属事件 //situationPO.setEventId(event);
        if (null != inputVO.getWoundedPerson()) {
            po.setWoundedPerson(inputVO.getWoundedPerson());//受傷人數
            content += " 受伤人数：" + inputVO.getWoundedPerson() + ", ";
        }
        if (null != inputVO.getDeathPerson()) {
            po.setDeathPerson(inputVO.getDeathPerson());//死亡人數
            content += " 死亡人数：" + inputVO.getDeathPerson() + ", ";
        }
        if (!StringUtils.isEmpty(inputVO.getPosition())) {
            po.setPosition(inputVO.getPosition());
        }

        if (null != inputVO.getIcon() && null != inputVO.getIcon().getId()) {
            SESGISConfigIconLibrary iconObj = new SESGISConfigIconLibrary();
            iconObj.setId(inputVO.getIcon().getId());
            po.setIconObjGis(iconObj);
        }

        if (null != inputVO.getSituationType() && !StringUtils.isEmpty(inputVO.getSituationType().getId())) {
            po.setSituationType(SystemCodeVO.CreateSystemCodePOById(inputVO.getSituationType().getId()));
            //"code": "002", "entityCode": "SESECD_situation_type",
            ISystemCode systemCodeByID = systemCodeService.getSystemCodeByID(inputVO.getSituationType().getId());
            content += "状态：" + InternationalResource.get(systemCodeByID.getValue(), getCurrentLanguage());
        }

        if (!StringUtils.isEmpty(inputVO.getPoint())) {
            logger.error("修改了点");
            po.setPoint(JSON.toJSONString(inputVO.getPoint()));//這個point的string保存的就是這個vo的json字符串,但真正存儲經緯度是在gisanalysis服務裏存儲，到pg中
        }


        logger.error("修改态势的日志:{}", JSON.toJSONString(po));
        //開始保存態勢
        situationDao.save(po);
        situationDao.flush();
        logger.error("态势修改完成");

        //态势更新操作日志
        ChangeLogDTO logDTO = ChangeLogDTO.builder().content(content).eventId(po.getEventId().getId()).type(ChangeLogBizTypeEnum.SITUATION.getCode()).realId(po.getId()).build();
        applicationContext.publishEvent(new ChangeLogEvent(this, logDTO));
        //发送消息
        customSESECDEmcSituationService.customAfterSaveEmcSituation(po);

		/*
		logger.error("开始保存点");
		String spatialId =  SESECDEmcSituation.MODEL_CODE+"_" +situationPO.getId();
		DoFeatureDto pointPO = new DoFeatureDto();
		pointPO.setLayerName("Common_Point");
		pointPO.setId(spatialId);
		pointPO.setLayer("emeSituationLayer");
		List<PointDto> coordinates  = PointVO.PointVO2PO(inputVO.getPoint());
		pointPO.setPoints(coordinates);

		if (null != inputVO.getPoint().getHeight()) {
			pointPO.setHeight( new BigDecimal(inputVO.getPoint().getHeight()));
		}
		if (null != inputVO.getPoint().getFloor()) {
			pointPO.setFloor(Double.valueOf(inputVO.getPoint().getFloor()));
		}
		logger.error("点的内容如下");
		logger.error(JSON.toJSONString(pointPO));
		BaseInfoVo addPointResult = iCustomGisAnalysisClient.addPointFeature(pointPO);
		//{"errMsg":"","result":"空间数据点保存成功！","success":true}
		if (!addPointResult.isSuccess()) {
			throw new Exception("save point failed");
		}
		logger.error("点的保存结果:");
		logger.error(JSON.toJSONString(addPointResult));
		 */
        return 1;
    }



    @Override
    public Integer deleteSituation(Long situationId) {
        logger.error(String.valueOf(situationId));
        //先查出来
        StringBuilder emergencySituationHQL = new StringBuilder();
        emergencySituationHQL.append(" from ").append(SESECDEmcSituation.JPA_NAME).append(" where valid = 1 ").append(" and id = ").append(situationId);
        List<SESECDEmcSituation> situationList = situationDao.findByHql(emergencySituationHQL.toString());
        if (CollectionUtils.isEmpty(situationList)) {
            logger.error("未查询到该应急态势");
            return 0;
        }
        SESECDEmcSituation po = situationList.get(0);
        po.setValid(false);
        //開始修改態勢
        situationDao.save(po);
        situationDao.flush();
        logger.error("态势删除完成");
        return 1;
    }


    /**
     * 发布态势
     *
     * @param situationId
     * @return
     */
    @Override
    public Integer releaseSituation(Long situationId) {
        if (null == situationId) {
            logger.error("参数有误");
            return 0;
        }
        logger.error("发布的应急态势的id:{}", situationId);
        StringBuilder emergencySituationHQL = new StringBuilder();
        emergencySituationHQL.append(" from ").append(SESECDEmcSituation.JPA_NAME).append(" where valid = 1 ").append(" and id = ").append(situationId);
        List<SESECDEmcSituation> situationList = situationDao.findByHql(emergencySituationHQL.toString());
        if (CollectionUtils.isEmpty(situationList)) {
            logger.error("未查询到该应急态势");
            return 0;
        }
        SESECDEmcSituation po = situationList.get(0);
        po.setSituationType(new SystemCode("SESECD_situation_type/001"));
        ISystemCode systemCodeByID = systemCodeService.getSystemCodeByID("SESECD_situation_type/001");
        situationDao.save(po);
        situationDao.flush();
        logger.error("态势发布完成");
        //发布态势日志
        ChangeLogDTO changeLogDTO = ChangeLogDTO.builder().content("【应急态势】 : "+po.getDescribtion() + ", 状态：" + InternationalResource.get(systemCodeByID.getValue(), getCurrentLanguage())).type(ChangeLogBizTypeEnum.SITUATION.getCode()).eventId(po.getEventId().getId()).realId(po.getId()).build();
        ChangeLogEvent changeEvent = new ChangeLogEvent(this, changeLogDTO);
        applicationContext.publishEvent(changeEvent);
        //发送消息
        customSESECDEmcSituationService.customAfterSaveEmcSituation(po, null);
        return 1;
    }


    /*----------------------------------------公共功能------------------------------------------------*/


}
