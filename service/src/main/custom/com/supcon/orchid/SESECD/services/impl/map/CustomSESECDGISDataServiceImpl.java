package com.supcon.orchid.SESECD.services.impl.map;

import com.supcon.orchid.SESECD.client.ICustomEcdGisAnalysisClient;
import com.supcon.orchid.SESECD.constant.enums.FieldTypeEnum;
import com.supcon.orchid.SESECD.daos.SESECDAlarmEnenetrelDao;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.entities.*;
import com.supcon.orchid.SESECD.model.vo.action.EcdActionVo;
import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.SESECD.model.vo.gisdata.AlarmEventVo;
import com.supcon.orchid.SESECD.model.vo.gisdata.InstructionVo;
import com.supcon.orchid.SESECD.model.vo.map.*;
import com.supcon.orchid.SESECD.model.vo.situation.EcdSituationVo;
import com.supcon.orchid.SESECD.services.map.CustomSESECDGISDataService;
import com.supcon.orchid.SESECD.services.map.CustomSESECDSavePointService;
import com.supcon.orchid.SESECD.utils.SqlUtils;
import com.supcon.orchid.SesCommonFunc.dto.ConditionDTO;
import com.supcon.orchid.SesCommonFunc.util.FieldType;
import com.supcon.orchid.SesCommonFunc.util.GISUtils;
import com.supcon.orchid.SesCommonFunc.vo.BaseInfoVo;
import com.supcon.orchid.SesCommonFunc.vo.BaseShellVo;
import com.supcon.orchid.SesCommonFunc.vo.HeadVo;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.i18n.InternationalResource;
import com.supcon.orchid.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomSESECDGISDataServiceImpl extends BaseServiceImpl implements CustomSESECDGISDataService {

    private static String currentLanguage;

    @Autowired
    private SqlUtils sqlUtils;

    @Autowired
    private SESECDAlmAlarmRecordDao alarmRecordDao;

    @Autowired
    private SESECDAlarmEnenetrelDao alarmEnenetrelDao;

    @Autowired
    private ICustomEcdGisAnalysisClient customEcdGisAnalysisClient;

    @Autowired
    private CustomSESECDSavePointService customSESECDSavePointService;

    {
        currentLanguage = getCurrentLanguage();
        log.error("初始化语言：");
        log.error(currentLanguage);
    }

    @Override
    public BaseInfoVo getAlarmWarningInfoInfoByFeatures(ConditionDTO dto) {
        return getAlarmEventInfoInfoByFeaturesByIsIncident(dto, false);
    }

    /**
     * 根据空间数据IDs提供应急事件业务数据服务
     *
     * @param dto
     * @return
     */
    @Override
    public BaseInfoVo getAlarmEventInfoInfoByFeatures(ConditionDTO dto) {
        return getAlarmEventInfoInfoByFeaturesByIsIncident(dto, true);
    }

    @Override
    public BaseInfoVo getInstructionInfoInfoByFeatures(ConditionDTO dto) {
        List<com.supcon.orchid.SesCommonFunc.vo.HeadVo> headVos = new ArrayList<>();
        com.supcon.orchid.SesCommonFunc.vo.BaseShellVo baseShellVo = new BaseShellVo();

        GISUtils.orgData(headVos, "id", FieldTypeEnum.STRING.getType(), "id");//ID
        GISUtils.orgData(headVos, "spatialId", FieldTypeEnum.STRING.getType(), "spatialId");//空间数据ID
        GISUtils.orgData(headVos, "actionName", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlarmAction.actionName", currentLanguage));//行动名称
        GISUtils.orgData(headVos, "actionAddress", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlarmAction.actionAddress", currentLanguage));//行动地点
        GISUtils.orgData(headVos, "actionCatogory", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlarmAction.actionCatogory", currentLanguage));//行动分类
        GISUtils.orgData(headVos, "actionDescription", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlarmAction.actionDescription", currentLanguage));//行动描述
        GISUtils.orgData(headVos, "commomState", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlarmAction.commomState", currentLanguage));//指令状态
        GISUtils.orgData(headVos, "owners", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.ActionOwners.owner", currentLanguage));//负责人
        baseShellVo.setHead(headVos);

        List<InstructionVo> InstructionVos = new ArrayList<>();
        String prefix = SESECDAlmAlarmRecord.MODEL_CODE + "_";
        List<Object> list = new ArrayList<>();
        String hql = GISUtils.getHql(SESECDAlarmAction.JPA_NAME, dto, list);
       // hql += sqlUtils.getSqlPartByCID();
        List<SESECDAlarmAction> alarmActions = new ArrayList<>();
        try {
            alarmActions = alarmRecordDao.findByHql(hql, list.toArray());
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            log.info("--------------------" + hql + "-------------------------");
            log.info("----------------------------搜索条件字段类型不正确----------------------------------");
        }
        if (null == alarmActions || alarmActions.isEmpty()) {
            return BaseInfoVo.ok(baseShellVo);
        }
        for (SESECDAlarmAction alarmAction : alarmActions) {
            InstructionVo instructionVo = getInstructionVo(prefix, alarmAction);
            InstructionVos.add(instructionVo);
        }

        baseShellVo.setData(InstructionVos);
        return BaseInfoVo.ok(baseShellVo);
    }

    @Override
    public BaseInfoVo getEcdActionInfoInfoByFeatures(ConditionDTO dto) {
        List<com.supcon.orchid.SesCommonFunc.vo.HeadVo> headVos = new ArrayList<>();
        com.supcon.orchid.SesCommonFunc.vo.BaseShellVo baseShellVo = new BaseShellVo();

        GISUtils.orgData(headVos, "id", FieldTypeEnum.STRING.getType(), "id");// ID
        GISUtils.orgData(headVos, "spatialId", FieldTypeEnum.STRING.getType(), "spatialId");// 空间数据ID
        GISUtils.orgData(headVos, "actionTime", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.emcAction.EmcAction.actionTime", currentLanguage));// 行动时间
        GISUtils.orgData(headVos, "actionAddr", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.emcAction.EmcAction.actionAddr", currentLanguage));// 行动地点
        GISUtils.orgData(headVos, "actionCatogory", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.alarmRecord.AlarmAction.actionCatogory", currentLanguage));// 行动分类
        GISUtils.orgData(headVos, "actionState", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.emcAction.EmcAction.actionState", currentLanguage));// 行动状态
        GISUtils.orgData(headVos, "description", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.alarmRecord.AlarmAction.actionDescription", currentLanguage));// 行动描述
        GISUtils.orgData(headVos, "planTime", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.emcAction.EmcAction.planTime", currentLanguage));// 预计耗时
        GISUtils.orgData(headVos, "beginTime", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.emcAction.EmcAction.beginTime", currentLanguage));// 实际开始时间
        GISUtils.orgData(headVos, "endTime", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.emcAction.EmcAction.endTime", currentLanguage));// 实际结束时间
        GISUtils.orgData(headVos, "trackRecord", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.emcAction.EmcAction.trackRecord", currentLanguage));// 跟踪记录
        GISUtils.orgData(headVos, "owner", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.alarmRecord.ActionOwners.owner", currentLanguage));// 负责人
        GISUtils.orgData(headVos, "ownCompanys", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.emcAction.MainDepartment.ownDepartment", currentLanguage));// 责任单位
        baseShellVo.setHead(headVos);


        List<Object> list = new ArrayList<>();
        String hql = GISUtils.getHql(SESECDEmcAction.JPA_NAME, dto, list);
        hql += sqlUtils.getSqlPartByCID();

        List<SESECDEmcAction> ecdActions = new ArrayList<>();
        try {
            ecdActions = alarmRecordDao.findByHql(hql, list.toArray());
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            log.info("--------------------" + hql + "-------------------------");
            log.info("----------------------------搜索条件字段类型不正确----------------------------------");
        }
        if (null == ecdActions || ecdActions.isEmpty()) {
            return BaseInfoVo.ok(baseShellVo);
        }

        List<EcdActionVo> EcdActionVos = new ArrayList<>();
        String prefix = SESECDEmcAction.MODEL_CODE + "_";
        for (SESECDEmcAction ecdAction : ecdActions) {
            com.supcon.orchid.SESECD.model.vo.action.EcdActionVo EcdActionVo = getEcdActionVo(prefix, ecdAction);
            EcdActionVos.add(EcdActionVo);
        }
        baseShellVo.setData(EcdActionVos);
        return BaseInfoVo.ok(baseShellVo);
    }

    @Override
    public BaseInfoVo getEcdSituationnfoInfoByFeatures(ConditionDTO dto) {
        List<com.supcon.orchid.SesCommonFunc.vo.HeadVo> headVos = new ArrayList<>();
        com.supcon.orchid.SesCommonFunc.vo.BaseShellVo baseShellVo = new BaseShellVo();

        GISUtils.orgData(headVos, "id", FieldTypeEnum.STRING.getType(), "id");// ID
        GISUtils.orgData(headVos, "spatialId", FieldTypeEnum.STRING.getType(), "spatialId");// 空间数据ID
        GISUtils.orgData(headVos, "describtion", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.emcSituation.EmcSituation.describtion", currentLanguage));//态势描述
        GISUtils.orgData(headVos, "position", FieldTypeEnum.STRING.getType(),
                "事发地点");//发生地点
        GISUtils.orgData(headVos, "occursTime", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.happenTime", currentLanguage));//发生时间
        GISUtils.orgData(headVos, "reportPerson", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.alarmPerson", currentLanguage));//上报人
        GISUtils.orgData(headVos, "source", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.emcSituation.EmcSituation.source", currentLanguage));//来源
        GISUtils.orgData(headVos, "woundedPerson", FieldTypeEnum.INT.getType(),
                InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.wounderPerson", currentLanguage));//受伤人数
        GISUtils.orgData(headVos, "deathPerson", FieldTypeEnum.INT.getType(),
                InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.deathPerson", currentLanguage));//死亡人数
        GISUtils.orgData(headVos, "deathPerson", FieldTypeEnum.STRING.getType(),
                InternationalResource.get("SESECD.emcSituation.EmcSituation.situationType", currentLanguage));//死亡人数
        baseShellVo.setHead(headVos);

        List<EcdSituationVo> EcdSituationVos = new ArrayList<>();
        String prefix = SESECDEmcSituation.MODEL_CODE + "_";
        List<Object> list = new ArrayList<>();
        String hql = GISUtils.getHql(SESECDEmcSituation.JPA_NAME, dto, list);
        hql += sqlUtils.getSqlPartByCID();
        List<SESECDEmcSituation> ecdStatius = new ArrayList<>();
        try {
            ecdStatius = alarmRecordDao.findByHql(hql, list.toArray());
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            log.info("--------------------" + hql + "-------------------------");
            log.info("----------------------------搜索条件字段类型不正确----------------------------------");
        }
        if (null == ecdStatius || ecdStatius.isEmpty()) {
            return BaseInfoVo.ok(baseShellVo);
        }
        for (SESECDEmcSituation ecdStatiu : ecdStatius) {
            //SESECD_situation_type/002 为未发布，过滤出已发布态势
            if (Objects.isNull(ecdStatiu.getSituationType()) || "SESECD_situation_type/002".equals(ecdStatiu.getSituationType().getId())) {
                continue;
            }
            EcdSituationVo ecdSituationVo = getEcdSituationVo(prefix, ecdStatiu);
            EcdSituationVos.add(ecdSituationVo);
        }
        baseShellVo.setData(EcdSituationVos);
        return BaseInfoVo.ok(baseShellVo);

    }

    @Override
    public BaseInfoVo getAlertRecordInfoInfoByFeatures(ConditionDTO dto) {
        List<HeadVo> headVos = new ArrayList<>();
        BaseShellVo baseShellVo = new BaseShellVo();
        GISUtils.orgData(headVos, "id", FieldType.STRING.getType(), "id");//ID
        GISUtils.orgData(headVos, "spatialId", FieldType.STRING.getType(), "spatialId");//空间数据ID
        GISUtils.orgData(headVos, "alarmDeviceName", FieldType.STRING.getType(), InternationalResource.get("SESECD.propertydisplayName.randon1685597883455", currentLanguage));//行动名称
        GISUtils.orgData(headVos, "alarmContent", FieldType.STRING.getType(), InternationalResource.get("SESECD.propertydisplayName.randon1685597869509", currentLanguage));//行动地点

        baseShellVo.setHead(headVos);

        List<AlertRecordVO> alertRecordVOS = new ArrayList<>();
        String prefix = SESECDEcdAlertRecord.MODEL_CODE + "_";

        List<Object> list = new ArrayList<>();
        String hql = GISUtils.getHql(SESECDEcdAlertRecord.JPA_NAME, dto, list);
        hql += sqlUtils.getSqlPartByCID();
        List<SESECDEcdAlertRecord> alertRecords = new ArrayList<>();
        try {
            alertRecords = alarmRecordDao.findByHql(hql, list.toArray());
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            log.info("--------------------" + hql + "-------------------------");
            log.info("----------------------------搜索条件字段类型不正确----------------------------------");
        }

        if (alertRecords.isEmpty()) {
            return BaseInfoVo.ok(baseShellVo);
        }
        for (SESECDEcdAlertRecord alertRecord : alertRecords) {
            AlertRecordVO alertRecordVO = getAlertRecordVO(prefix, alertRecord);
            alertRecordVOS.add(alertRecordVO);
        }
        baseShellVo.setData(alertRecordVOS);
        return BaseInfoVo.ok(baseShellVo);
    }

    @Override
    public List<AlarmRecordGisVO> listAlarmEventByGisAndRadius(AlarmEventQueryVO query) {
        Integer pageNo = query.getPageNo();
        Integer pageSize = query.getPageSize();
        Integer fromIndex = (pageNo - 1) * pageSize;

        //查询周边应急事件
        com.supcon.orchid.SESGISConfig.DTO.GisAnalysisDTO dto = new com.supcon.orchid.SESGISConfig.DTO.GisAnalysisDTO();
        dto.setLon(query.getLon());
        dto.setLat(query.getLat());
        dto.setRadius(query.getRadius());
        dto.setLayers("incidentLayer");
        List<GeometryAnalysisVO> geometryAnalysisVOS = customSESECDSavePointService.GetBufferedResourceList(dto);

        List<Long> alarmIds = new ArrayList<>();
        for (GeometryAnalysisVO item :
                geometryAnalysisVOS) {
            Long aLong = Long.valueOf(item.getId().substring(item.getId().lastIndexOf("_") + 1));
            alarmIds.add(aLong);
        }
//        geometryAnalysisVOS.stream().forEach(c->{
//            Long aLong = Long.valueOf(c.getId().substring(c.getId().lastIndexOf("_") + 1));
//            alarmIds.add(aLong);
//        });

        //查询接警数据
        StringBuilder builder = new StringBuilder();
        builder.append("from ").append(SESECDAlmAlarmRecord.JPA_NAME).append(" where cid = ").append(getCurrentCompanyId()).append(" and valid = 1 and id in (:ids)");
        List<SESECDAlmAlarmRecord> SESECDAlmAlarmRecords = alarmRecordDao.createQuery(builder.toString()).setParameterList("ids", alarmIds).setFirstResult(fromIndex).setMaxResults(pageSize).list();

        //查询预案数据
        builder.delete(0, builder.length());
        builder.append("from ").append(SESECDEmePlanObj.JPA_NAME).append(" where valid = 1 and ALARM_ID in (:ids)");
        List<SESECDEmePlanObj> planObjList = alarmRecordDao.createQuery(builder.toString()).setParameterList("ids", alarmIds).list();
        Map<Long, List<SESECDEmePlanObj>> planGroup = planObjList.stream().collect(Collectors.groupingBy(c -> c.getAlarmId().getId()));

        String prefix = SESECDAlmAlarmRecord.MODEL_CODE + "_";
        List<AlarmRecordGisVO> alarmEventVoList = new ArrayList<>();
        for (SESECDAlmAlarmRecord alarmRecord : SESECDAlmAlarmRecords) {
            AlarmRecordGisVO alarmEventVo = getAlarmRecordGisVO(prefix, planGroup, alarmRecord);
            alarmEventVoList.add(alarmEventVo);
        }

        return alarmEventVoList;
    }



    /*--------------------------------------------公共方法-----------------------------------------------------*/

    /**
     * 根据空间数据IDs提供应急事件业务数据服务
     *
     * @param dto
     * @param isIncident
     * @return
     */
    public BaseInfoVo getAlarmEventInfoInfoByFeaturesByIsIncident(ConditionDTO dto, Boolean isIncident) {
        List<com.supcon.orchid.SesCommonFunc.vo.HeadVo> headVos = new ArrayList<>();
        com.supcon.orchid.SesCommonFunc.vo.BaseShellVo baseShellVo = new BaseShellVo();

        GISUtils.orgData(headVos, "id", FieldTypeEnum.STRING.getType(), "id");//ID
        GISUtils.orgData(headVos, "spatialId", FieldTypeEnum.STRING.getType(), "spatialId");//空间数据ID
        GISUtils.orgData(headVos, "accidentName", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.accidentName", currentLanguage));//事件名称
        GISUtils.orgData(headVos, "receiver", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.receiver", currentLanguage));//接警人
        GISUtils.orgData(headVos, "rectime", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.rectime", currentLanguage));//接警时间
        GISUtils.orgData(headVos, "position", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.position", currentLanguage));//事发地点
        GISUtils.orgData(headVos, "description", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.description", currentLanguage));//事件描述
        GISUtils.orgData(headVos, "happenTime", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.propertyshowName.randon1576650110363.flag", currentLanguage));//事发时间
        GISUtils.orgData(headVos, "hpnCompany", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.company", currentLanguage));//事发单位
        GISUtils.orgData(headVos, "accidentType", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.accidentType", currentLanguage));//事故类型
        GISUtils.orgData(headVos, "processState", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.processState", currentLanguage));//处理状态
        GISUtils.orgData(headVos, "isIncident", FieldTypeEnum.BOOLEAN.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.isIncident", currentLanguage));//是否应急事件
        GISUtils.orgData(headVos, "alarmPerson", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.alarmPerson", currentLanguage));//上报人
        GISUtils.orgData(headVos, "alarmPhone", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.alarmPhone", currentLanguage));//上报人电话
        GISUtils.orgData(headVos, "alarmLevel", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.propertyshowName.randon1576742727771.flag", currentLanguage));//事件等级
        GISUtils.orgData(headVos, "wounderPerson", FieldTypeEnum.INT.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.wounderPerson", currentLanguage));//受伤人数
        GISUtils.orgData(headVos, "deathPerson", FieldTypeEnum.INT.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.deathPerson", currentLanguage));//死亡人数
        GISUtils.orgData(headVos, "process", FieldTypeEnum.STRING.getType(), InternationalResource.get("SESECD.alarmRecord.AlmAlarmRecord.process", currentLanguage));//处理记录
        baseShellVo.setHead(headVos);

        List<Object> list = new ArrayList<>();
        String hql = GISUtils.getHql(SESECDAlmAlarmRecord.JPA_NAME, dto, list);
        hql += sqlUtils.getSqlPartByCID();
        List<SESECDAlmAlarmRecord> SESECDAlmAlarmRecords = new ArrayList<>();
        try {
            SESECDAlmAlarmRecords = alarmRecordDao.findByHql(hql, list.toArray());
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            log.info("--------------------" + hql + "-------------------------");
            log.info("----------------------------搜索条件字段类型不正确----------------------------------");
        }
        if (SESECDAlmAlarmRecords == null || SESECDAlmAlarmRecords.isEmpty()) {
            return BaseInfoVo.ok(baseShellVo);
        }

        List<AlarmEventVo> alarmEventVos = new ArrayList<>();
        String prefix = SESECDAlmAlarmRecord.MODEL_CODE + "_";
        for (SESECDAlmAlarmRecord alarmRecord : SESECDAlmAlarmRecords) {
            //过滤事件结束的事件
            if (alarmRecord.getIsOver() != null && alarmRecord.getIsOver()) {
                continue;
            }
            AlarmEventVo alarmEventVo = convertToVO(prefix, alarmRecord);
            alarmEventVos.add(alarmEventVo);
        }
        baseShellVo.setData(alarmEventVos);
        return BaseInfoVo.ok(baseShellVo);
    }

    /**
     * 转换成事件VO
     *
     * @param prefix      模型前缀
     * @param alarmRecord 事件PO
     */
    private AlarmEventVo convertToVO(String prefix, SESECDAlmAlarmRecord alarmRecord) {
        AlarmEventVo alarmEventVo = new AlarmEventVo();
        alarmEventVo.setId(alarmRecord.getId().toString());//id
        alarmEventVo.setSpatialId(prefix + alarmRecord.getId().toString());//空间数据ID
        alarmEventVo.setAccidentName(alarmRecord.getAccidentName());//事件名称
        alarmEventVo.setDescription(alarmRecord.getDescription());//事件描述
        alarmEventVo.setAlarmPerson(alarmRecord.getAlarmPerson() == null ? "" : alarmRecord.getAlarmPerson().getName());//上报人
        alarmEventVo.setAlarmPhone(alarmRecord.getAlarmPhone());//上报人电话
        alarmEventVo.setWounderPerson(alarmRecord.getWounderPerson());//受伤人数
        alarmEventVo.setDeathPerson(alarmRecord.getDeathPerson());//死亡人数
        List<SESECDAlarmEnenetrel> AlarmEnenetrels = alarmEnenetrelDao.findByHql("from " + SESECDAlarmEnenetrel.JPA_NAME + " where valid = 1 and alarmId = ?0", new Object[]{alarmRecord});
        if (null != AlarmEnenetrels && AlarmEnenetrels.size() > 0) {
            String accidentTypes = "";
            for (SESECDAlarmEnenetrel AlarmEnenetrel : AlarmEnenetrels) {
                if (null != AlarmEnenetrel.getEnenetrel()) {
                    accidentTypes += AlarmEnenetrel.getEnenetrel().getName() + ",";
                }
            }
            accidentTypes = accidentTypes.substring(0, accidentTypes.length() - 1);
            alarmEventVo.setAccidentType(accidentTypes);
        }
        SystemCode accidentLevel = alarmRecord.getAlarmLevel();
        if (null != accidentLevel) {
            alarmEventVo.setAlarmLevel(InternationalResource.get(accidentLevel.getValue(), currentLanguage));//事件等级
        }
        alarmEventVo.setHpnCompany(alarmRecord.getHpnCompany() == null ? "" : alarmRecord.getHpnCompany().getName());//事发单位
        alarmEventVo.setHappenTime(alarmRecord.getHappenTime() == null ? "" : com.supcon.orchid.SESECD.utils.DateUtils.MySimpleDateFormat.format(alarmRecord.getHappenTime()));//事发时间
        alarmEventVo.setIsIncident(alarmRecord.getIsIncident());//是否应急事件
        alarmEventVo.setPosition(Objects.nonNull(alarmRecord.getLocationIncident()) ? alarmRecord.getLocationIncident().getName() : "无");//事发地点
        alarmEventVo.setProcess(alarmRecord.getProcess());//处理记录
        alarmEventVo.setReceiver(alarmRecord.getReceiver() == null ? "" : alarmRecord.getReceiver().getName());//接警人
        alarmEventVo.setRectime(alarmRecord.getRectime() == null ? "" : com.supcon.orchid.SESECD.utils.DateUtils.MySimpleDateFormat.format(alarmRecord.getRectime()));//接警时间
        SystemCode ProcessState = alarmRecord.getProcessState();//处理状态
        if (null != ProcessState) {
            alarmEventVo.setProcessState(InternationalResource.get(ProcessState.getValue(), currentLanguage));//处理状态
        }
        return alarmEventVo;
    }

    /**
     * 获取指令VO
     *
     * @param prefix      前缀
     * @param alarmAction 指令po
     * @return 指令VO
     */
    private InstructionVo getInstructionVo(String prefix, SESECDAlarmAction alarmAction) {
        InstructionVo instructionVo = new InstructionVo();
        instructionVo.setId(alarmAction.getId().toString());//id
        instructionVo.setSpatialId(prefix + alarmAction.getId().toString());//空间数据ID
        instructionVo.setActionAddress(alarmAction.getActionAddress());//行动地点
        instructionVo.setActionDescription(alarmAction.getActionDescription());//行动描述
        instructionVo.setActionName(alarmAction.getActionName());//行动名称
        SystemCode actionCatogory = alarmAction.getActionCatogory();
        if (null != actionCatogory) {
            instructionVo.setActionCatogory(InternationalResource.get(actionCatogory.getValue(), currentLanguage));//行动分类
        }
        SystemCode commomState = alarmAction.getCommomState();
        if (null != commomState) {
            instructionVo.setCommomState(InternationalResource.get(commomState.getValue(), currentLanguage));//指令状态
        }
        //负责人
        List<SESECDActionOwners> SESECDActionOwners2 = alarmRecordDao.findByHql("from " + SESECDActionOwners.JPA_NAME + " where valid = 1 and actionId = ?0 ", new Object[]{alarmAction});
        if (null != SESECDActionOwners2 && SESECDActionOwners2.size() > 0) {
            String owners = "";
            for (SESECDActionOwners actionOwner : SESECDActionOwners2) {
                if (null != actionOwner.getOwnPerson() && null != actionOwner.getOwnPerson().getPersonId()) {
                    owners += actionOwner.getOwnPerson().getPersonId().getName() + ",";
                }
            }
            if (owners.length() > 0) {
                owners = owners.substring(0, owners.length() - 1);
            }
            instructionVo.setOwners(owners);
        }
        return instructionVo;
    }


    /**
     * 获取应急行动
     *
     * @param prefix    地图前缀
     * @param ecdAction 行动po
     * @return
     */
    private EcdActionVo getEcdActionVo(String prefix, SESECDEmcAction ecdAction) {
        EcdActionVo EcdActionVo = new EcdActionVo();
        EcdActionVo.setId(ecdAction.getId().toString());// id
        EcdActionVo.setSpatialId(prefix + ecdAction.getId().toString());// 空间数据ID
        EcdActionVo.setActionAddr(ecdAction.getActionAddr());// 行动地点
        EcdActionVo.setDescription(ecdAction.getDescription());// 行动描述
        EcdActionVo.setTrackRecord(ecdAction.getTrackRecord());// 跟踪记录
        EcdActionVo.setBeginTime(ecdAction.getBeginTime() == null ? ""
                : com.supcon.orchid.SESECD.utils.DateUtils.MySimpleDateFormat.format(ecdAction.getBeginTime()));// 行动时间
        EcdActionVo.setEndTime(ecdAction.getEndTime() == null ? ""
                : com.supcon.orchid.SESECD.utils.DateUtils.MySimpleDateFormat.format(ecdAction.getEndTime()));// 实际结束时间
        EcdActionVo.setActionTime(ecdAction.getActionTime() == null ? ""
                : com.supcon.orchid.SESECD.utils.DateUtils.MySimpleDateFormat.format(ecdAction.getActionTime()));// 实际开始时间
        EcdActionVo.setPlanTime(ecdAction.getPlanTime() == null ? "" : ecdAction.getPlanTime().toString());// 预计耗时
        SystemCode actionCatogory = ecdAction.getActionCatogory();
        if (null != actionCatogory) {
            EcdActionVo.setActionCatogory(InternationalResource.get(actionCatogory.getValue(), currentLanguage));// 行动分类
        }
        SystemCode actionState = ecdAction.getActionState();
        if (null != actionState) {
            EcdActionVo.setActionState(InternationalResource.get(actionState.getValue(), currentLanguage));// 行动状态
        }
        // 负责人
        List<SESECDMainPeople> mainPeoples = alarmRecordDao.findByHql("from " + SESECDMainPeople.JPA_NAME + " where valid = 1 and actionId = ?0", new Object[]{ecdAction});
        if (null != mainPeoples && mainPeoples.size() > 0) {
            String owners = "";
            for (SESECDMainPeople mainPeople : mainPeoples) {
                if (null != mainPeople.getOwnPerson() && null != mainPeople.getOwnPerson().getPersonId()) {
                    owners += mainPeople.getOwnPerson().getPersonId().getName() + ",";
                }
            }
            if (!"".equals(owners)) {
                owners = owners.substring(0, owners.length() - 1);
            }
            EcdActionVo.setOwner(owners);
        }
        // 责任单位
        List<SESECDMainDepartment> mainDepartments = alarmRecordDao.findByHql("from " + SESECDMainDepartment.JPA_NAME + " where valid = 1 and actionId = ?0", new Object[]{ecdAction});
        if (null != mainDepartments && mainDepartments.size() > 0) {
            String departments = "";
            for (SESECDMainDepartment mainDepartment : mainDepartments) {
                if (null != mainDepartment.getOwnDepartmentN() && null != mainDepartment.getOwnDepartmentN().getBelongDepartment()) {
                    departments += mainDepartment.getOwnDepartmentN().getBelongDepartment().getName() + ",";
                }
            }
            if (!"".equals(departments)) {
                departments = departments.substring(0, departments.length() - 1);
            }
            EcdActionVo.setOwnCompanys(departments);
        }
        return EcdActionVo;
    }

    /**
     * 获取应急态势
     *
     * @param prefix    地图空间前缀
     * @param ecdStatiu 应急态势po
     * @return 应急态势VO
     */
    private EcdSituationVo getEcdSituationVo(String prefix, SESECDEmcSituation ecdStatiu) {
        EcdSituationVo ecdSituationVo = new EcdSituationVo();
        if (ecdStatiu.getSituationType() != null) {
            ecdSituationVo.setSituationType(InternationalResource.get(ecdStatiu.getSituationType().getValue(), currentLanguage));
        }
        ecdSituationVo.setId(ecdStatiu.getId().toString());// id
        ecdSituationVo.setSpatialId(prefix + ecdStatiu.getId().toString());// 空间数据ID
        ecdSituationVo.setDeathPerson(ecdStatiu.getDeathPerson());// 死亡人数
        ecdSituationVo.setWoundedPerson(ecdStatiu.getWoundedPerson());// 受伤人数
        ecdSituationVo.setDescribtion(ecdStatiu.getDescribtion());// 态势描述
        ecdSituationVo.setPosition(ecdStatiu.getPosition());// 事发地点
        ecdSituationVo.setReportPerson(
                ecdStatiu.getReportPerson() == null ? "" : ecdStatiu.getReportPerson().getName());// 上报人
        ecdSituationVo.setOccursTime(ecdStatiu.getOccursTime() == null ? ""
                : com.supcon.orchid.SESECD.utils.DateUtils.MySimpleDateFormat.format(ecdStatiu.getOccursTime()));// 发生时间
        SystemCode source = ecdStatiu.getSource();
        if (null != source) {
            ecdSituationVo.setSource(InternationalResource.get(source.getValue(), currentLanguage));// 来源
        }
        return ecdSituationVo;
    }

    /**
     * 获取预案详情
     *
     * @param prefix      空间点前缀
     * @param planGroup   预案数据
     * @param alarmRecord 接警po
     * @return 接警VO
     */
    private AlarmRecordGisVO getAlarmRecordGisVO(String prefix, Map<Long, List<SESECDEmePlanObj>> planGroup, SESECDAlmAlarmRecord alarmRecord) {
        AlarmRecordGisVO alarmEventVo = new AlarmRecordGisVO();
        alarmEventVo.setId(alarmRecord.getId().toString());//id
        alarmEventVo.setSpatialId(prefix + alarmRecord.getId().toString());//空间数据ID
        alarmEventVo.setAccidentName(alarmRecord.getAccidentName());//事件名称
        alarmEventVo.setDescription(alarmRecord.getDescription());//事件描述
        alarmEventVo.setAlarmPerson(alarmRecord.getAlarmPerson() == null ? "" : alarmRecord.getAlarmPerson().getName());//上报人
        alarmEventVo.setAlarmPhone(alarmRecord.getAlarmPhone());//上报人电话
        alarmEventVo.setWounderPerson(alarmRecord.getWounderPerson());//受伤人数
        alarmEventVo.setDeathPerson(alarmRecord.getDeathPerson());//死亡人数
        //事故类型
        List<SESECDAlarmEnenetrel> AlarmEnenetrels = alarmEnenetrelDao.findByHql("from " + SESECDAlarmEnenetrel.JPA_NAME + " where valid = 1 and alarmId = ?0", new Object[]{alarmRecord});
        if (null != AlarmEnenetrels && AlarmEnenetrels.size() > 0) {
            String accidentTypes = "";
            for (SESECDAlarmEnenetrel AlarmEnenetrel : AlarmEnenetrels) {
                if (null != AlarmEnenetrel.getEnenetrel()) {
                    accidentTypes += AlarmEnenetrel.getEnenetrel().getName() + ",";
                }
            }
            accidentTypes = accidentTypes.substring(0, accidentTypes.length() - 1);
            alarmEventVo.setAccidentType(accidentTypes);
        }
        SystemCode accidentLevel = alarmRecord.getAccidentLevel();
        if (null != accidentLevel) {
            alarmEventVo.setAlarmLevel(InternationalResource.get(accidentLevel.getValue(), currentLanguage));//事件等级
        }
        alarmEventVo.setHpnCompany(alarmRecord.getHpnCompany() == null ? "" : alarmRecord.getHpnCompany().getName());//事发单位
        alarmEventVo.setHappenTime(alarmRecord.getHappenTime() == null ? "" : com.supcon.orchid.SESECD.utils.DateUtils.MySimpleDateFormat.format(alarmRecord.getHappenTime()));//事发时间
        alarmEventVo.setIsIncident(alarmRecord.getIsIncident());//是否应急事件
        alarmEventVo.setPosition(alarmRecord.getPosition());//事发地点
        alarmEventVo.setProcess(alarmRecord.getProcess());//处理记录
        alarmEventVo.setReceiver(alarmRecord.getReceiver() == null ? "" : alarmRecord.getReceiver().getName());//接警人
        alarmEventVo.setRectime(alarmRecord.getRectime() == null ? "" : com.supcon.orchid.SESECD.utils.DateUtils.MySimpleDateFormat.format(alarmRecord.getRectime()));//接警时间
        SystemCode ProcessState = alarmRecord.getProcessState();//处理状态
        if (null != ProcessState) {
            alarmEventVo.setProcessState(InternationalResource.get(ProcessState.getValue(), currentLanguage));//处理状态
        }

        List<SESECDEmePlanObj> sesecdEmePlanObjs = planGroup.get(alarmRecord.getId());
        if (sesecdEmePlanObjs != null && !sesecdEmePlanObjs.isEmpty()) {
            alarmEventVo.setEmergencyPlanVoList(new ArrayList<>());
            for (SESECDEmePlanObj item : sesecdEmePlanObjs) {
                EmergencyPlanVO planVo = new EmergencyPlanVO();
                planVo.setId(item.getId().toString());//ID
                if (Objects.nonNull(item.getPlanObj())) {
                    planVo.setPlanName(item.getPlanObj().getPlanName());//预案名称
                    if (Objects.nonNull(item.getPlanObj().getPlanType())) {
                        planVo.setPlanType(InternationalResource.get(item.getPlanObj().getPlanType().getValue(), currentLanguage));//预案类型
                    }
                    planVo.setPlanVersion(item.getPlanObj().getPlanVersion());//预案版本号
                }
                alarmEventVo.getEmergencyPlanVoList().add(planVo);
            }
        }
        return alarmEventVo;
    }


    /**
     * 获取报警记录VO
     * @param prefix
     * @param alertRecord
     * @return
     */
    private AlertRecordVO getAlertRecordVO(String prefix, SESECDEcdAlertRecord alertRecord) {
        AlertRecordVO alertRecordVO = new AlertRecordVO();
        alertRecordVO.setId(alertRecord.getId().toString());//id
        alertRecordVO.setSpatialId(prefix + alertRecord.getId().toString());//空间数据ID
        alertRecordVO.setAlarmDeviceName(alertRecord.getAlarmDeviceName());//报警装置名称
        alertRecordVO.setAlarmContent(alertRecord.getAlarmContent());//报警详情
        return alertRecordVO;
    }

}
