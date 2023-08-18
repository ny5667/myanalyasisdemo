package com.supcon.orchid.SESECD.services.impl.project.sgm.report.event;

import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.daos.SESECDAlarmEnenetrelDao;
import com.supcon.orchid.SESECD.daos.SESECDAlmAlarmRecordDao;
import com.supcon.orchid.SESECD.daos.SESECDEmePlanObjDao;
import com.supcon.orchid.SESECD.entities.SESECDAlarmEnenetrel;
import com.supcon.orchid.SESECD.entities.SESECDAlmAlarmRecord;
import com.supcon.orchid.SESECD.entities.SESECDEmePlanObj;
import com.supcon.orchid.SESECD.model.vo.common.*;
import com.supcon.orchid.SESECD.model.vo.project.sgm.report.event.SESECDAlarmRecordVO;
import com.supcon.orchid.SESECD.model.vo.project.sgm.report.event.SESWssEPEmergencyPlanVO;
import com.supcon.orchid.SESECD.model.vo.project.sgm.report.event.SESWssERAccidentClassVO;
import com.supcon.orchid.SESECD.services.alarm.action.CustomSESECDAlarmActionService;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDAlarmRecordService;
import com.supcon.orchid.SESECD.services.map.CustomSESECDSavePointService;
import com.supcon.orchid.SESECD.services.notify.MsgModelDTO;
import com.supcon.orchid.SESECD.services.notify.NotifyFacade;
import com.supcon.orchid.SESECD.services.paramconfig.CustomSESECDParamConfigService;
import com.supcon.orchid.SESECD.services.project.sgm.report.event.CustomSESECDProjectSgmReportEventService;
import com.supcon.orchid.SESECD.utils.HttpInvokeUtils;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.SESWssEP.entities.SESWssEPEmergencyPlan;
import com.supcon.orchid.SESWssER.entities.SESWssERAccidentClass;
import com.supcon.orchid.foundation.entities.Company;
import com.supcon.orchid.foundation.entities.Department;
import com.supcon.orchid.foundation.entities.Staff;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.foundation.services.CompanyService;
import com.supcon.orchid.foundation.services.DepartmentService;
import com.supcon.orchid.foundation.services.StaffService;
import com.supcon.orchid.foundation.services.SystemCodeService;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.support.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomSESECDProjectSgmReportEventServiceImpl extends BaseServiceImpl implements CustomSESECDProjectSgmReportEventService {


    private static final Long DEFAULT_COMPANY_ID = 1000L;
    private static final String SGM_RECORD_SYNC = "SGM_RECORD_SYNC";

    private static final String SYNC_URL = "/msService/public/SESECD/alarmRecord/almAlarmRecord/sgmReportEvent/addOrUpdate";

    /**
     * 公司级别列表
     */
    private static final Set<String> COMPANY_LEVEL = new HashSet<>();

    @Autowired
    private SESECDAlmAlarmRecordDao almAlarmRecordDao;

    @Autowired
    private CustomSESECDParamConfigService customSESECDParamConfigService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SystemCodeService systemCodeService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private CustomSESECDAlarmRecordService customSESECDAlarmRecordService;

    @Autowired
    private NotifyFacade notifyFacade;

    @Autowired
    private SESECDEmePlanObjDao emePlanObjDao;

    @Autowired
    private SESECDAlarmEnenetrelDao alarmEnenetrelDao;

    @Autowired
    private CustomSESECDAlarmActionService customSESECDAlarmActionService;

    @Autowired
    private CustomSESECDSavePointService customSESECDSavePointService;

    static {
        COMPANY_LEVEL.add("1");
        COMPANY_LEVEL.add("2");
        COMPANY_LEVEL.add("3");
    }

    /**
     * 接警数据上报
     *
     * @param alarmRecordId 接警id
     * @return 上报结果
     */
    @Override
    public Result<String> Report(Long alarmRecordId) {
        SESECDAlmAlarmRecord po = almAlarmRecordDao.get(alarmRecordId);
        Assert.notNull(po, "alarmRecordId can not find entity");

        Map<String, String> parameterOptions = customSESECDParamConfigService.getParameterOptions(SGM_RECORD_SYNC);
        Long currentCompanyId = getCurrentCompanyId();
        log.error("当前公司id：");
        log.error(currentCompanyId + "");
        if (currentCompanyId == null) {
            currentCompanyId = DEFAULT_COMPANY_ID;
        }
        Company company = companyService.get(currentCompanyId);
        log.error("当前公司编码为：");
        log.error(company.getCode());
        String configValue = parameterOptions.get(company.getCode());
        Assert.hasLength(configValue, company.getCode() + "找不到对应配置项：" + SGM_RECORD_SYNC + "，需要到应急指挥，数据维护，参数配置增加本公司相关配置");
        String[] split = configValue.split(",");
        Assert.isTrue(split.length == 3, "配置项需要包含两条配置");
        String companyLevel = split[0];
        String parentCompanyCode = split[1];
        String baseUrl = split[2];

        Assert.isTrue(COMPANY_LEVEL.contains(companyLevel), "公司级别：" + companyLevel + "，该配置项不正确");

        StringBuilder builder = new StringBuilder();
        builder.append("from ").append(SESECDAlarmEnenetrel.JPA_NAME).append(" where valid = 1 and alarmId = ?0");
        List<SESECDAlarmEnenetrel> alarmEnenetrelList = almAlarmRecordDao.findByHql(builder.toString(), new Object[]{po});

        builder.delete(0, builder.length());
        builder.append("from ").append(SESECDEmePlanObj.JPA_NAME).append(" where valid = 1 and alarmId = ?0");
        List<SESECDEmePlanObj> emePlanObjList = almAlarmRecordDao.findByHql(builder.toString(), new Object[]{po});

        List<SESWssERAccidentClassVO> accidentClassVOS = new ArrayList<>();
        alarmEnenetrelList.forEach(c -> {
            SESWssERAccidentClassVO vo = new SESWssERAccidentClassVO();
            BeanUtils.copyProperties(c.getEnenetrel(), vo);
            accidentClassVOS.add(vo);
        });

        List<SESWssEPEmergencyPlanVO> planVOS = new ArrayList<>();
        emePlanObjList.forEach(c -> {
            SESWssEPEmergencyPlanVO sesWssEPEmergencyPlanVO = new SESWssEPEmergencyPlanVO();
            BeanUtils.copyProperties(c.getPlanObj(), sesWssEPEmergencyPlanVO);
            planVOS.add(sesWssEPEmergencyPlanVO);
        });


        // 创建一个SESECDAlarmRecordVO对象
        SESECDAlarmRecordVO vo = new SESECDAlarmRecordVO();
        copy2AlarmRecordVO(po, accidentClassVOS, planVOS, vo, companyLevel, parentCompanyCode);

        // 打印vo对象，以验证属性值是否设置正确
        log.error("上报数据：");
        log.error(JsonHelper.writeValue(vo));

        log.error("调用接口参数：");
        log.error("baseUrl:");
        log.error(baseUrl);
        log.error("sync_url:");
        log.error(SYNC_URL);
        log.error("接口参数：");
        log.error(JsonHelper.writeValue(vo));


        ResponseEntity<Result> post = HttpInvokeUtils.post(baseUrl, SYNC_URL, vo, Result.class);
        long endTime = System.nanoTime();

        log.error("调用接口返回：");
        log.error(JsonHelper.writeValue(post.getBody()));
        Result body = post.getBody();

        log.error("发送通知开始：");
        long startTime = System.nanoTime();

        sendNotify(po);

        log.error("调用接口耗时：");
        log.error(((endTime - startTime) / 1_000_000) + "");

        return Result.data("数据上报成功");
    }


    /**
     * 接警数据接受
     *
     * @param input 接警事件
     * @return 数据保存成功信息
     */
    @Override
    public Result<String> AddOrUpdate(SESECDAlarmRecordVO input) throws Exception {
        log.error("进入保存应急事件方法");

//        log.error("保存数据开始：");
//        long startTime = System.nanoTime();

        Company companyByCode = companyService.getCompanyByCode(input.getParentCompanyCode());
        Assert.notNull(companyByCode, "父公司编码：" + input.getParentCompanyCode() + ",父公司编码为空");

        SESECDAlmAlarmRecord po = new SESECDAlmAlarmRecord();
        StringBuilder builder = new StringBuilder();
        builder.append("from ").append(SESECDAlmAlarmRecord.JPA_NAME).append(" where valid = 1 and reportEventId = ?0");
        List<SESECDAlmAlarmRecord> records = almAlarmRecordDao.findByHql(builder.toString(), new Object[]{input.getId()});
        if (records != null && !records.isEmpty()) {
            po = records.get(0);
        }

        copy2AlarmRecordPO(input, po, companyByCode);

        almAlarmRecordDao.save(po);
        almAlarmRecordDao.flush();

        savePlanAndAccidentType(input, companyByCode, po);

        String spatialId = SESECDAlmAlarmRecord.MODEL_CODE + "_" + po.getId().toString();
        log.error("地图信息：");
        log.error(input.getMapPoint());
        PointVO pointVO = PointVO.PointPO2VO(input.getMapPoint(), SESECDAlmAlarmRecord.MODEL_CODE + "_", po.getId().toString());
        customSESECDSavePointService.batchDeleteFeatureInfo(spatialId,ConstDict.incidentLayer);//删除应急事件点
        customSESECDSavePointService.savePoint2PG(spatialId, pointVO, ConstDict.warningLayer);

        customSESECDAlarmActionService.generateInstructions(po);//生成接到警报行动

//        long endTime = System.nanoTime();
//        log.error("接警数据接受 保存数据结束：");
//        log.error(((endTime - startTime) / 1_000_000) + "");

        log.error("保存完应急事件，退出");
        return Result.data("数据保存成功");
    }


    /*----------------------------------------公共功能---------------------------------------------*/


    /**
     * 创建应急事件vo
     *
     * @param po                应急事件po
     * @param accidentClassVOS  事件类型vos
     * @param planVOS           预案vos
     * @param vo                应急事件vo
     * @param companyLevel      公司级别
     * @param parentCompanyCode 父公司编码
     */
    private void copy2AlarmRecordVO(
            SESECDAlmAlarmRecord po,
            List<SESWssERAccidentClassVO> accidentClassVOS,
            List<SESWssEPEmergencyPlanVO> planVOS,
            SESECDAlarmRecordVO vo,
            String companyLevel,
            String parentCompanyCode
    ) {
        // 设置vo对象的属性值并添加相关注释
        vo.setId(po.getId()); // id
        vo.setAccidentName(po.getAccidentName()); // 事件名称
        vo.setHappenTime(po.getHappenTime()); // 事发时间
        vo.setPosition(po.getPosition()); // 发生位置
        vo.setWounderPerson(po.getWounderPerson()); // 受伤人数
        vo.setDescription(po.getDescription()); // 事件描述
        vo.setMapPoint(po.getMapPoint()); // 坐标
        vo.setReceiver(StaffVO.StaffPO2VO(po.getReceiver())); // 接警人
        vo.setRectime(po.getRectime()); // 接警时间
        vo.setAccidentClasses(accidentClassVOS); // 事故类型
        vo.setHpnCompany(CompanyVO.CompanyVOPO2VO(po.getHpnCompany())); // 事发公司
        vo.setProcessState(SystemCodeVO.CreateSystemCodeVO(po.getProcessState())); // 处理状态
        vo.setAlarmLevel(SystemCodeVO.CreateSystemCodeVO(po.getAlarmLevel())); // 报警等级
        vo.setEmePlans(planVOS); // 应急预案
        vo.setAlarmPerson(StaffVO.StaffPO2VO(po.getAlarmPerson())); // 接警人
        vo.setAlarmPhone(po.getAlarmPhone()); // 上报人电话
        vo.setHpmDepartment(DepartmentVO.DepartmentPO2VO(po.getHpmDepartment())); // 事发部门
        vo.setDeathPerson(po.getDeathPerson()); // 死亡人数
        vo.setProcess(po.getProcess()); // 处理记录


        /*-----------------------------------------省国贸上报字段，非数据库字段，开始----------------------------------------*/

        vo.setParentCompanyCode(parentCompanyCode);

        /*-----------------------------------------省国贸上报字段，非数据库字段，结束----------------------------------------*/


        /*-----------------------------------------省国贸上报字段，数据库字段，开始----------------------------------------*/

        vo.setTwoEPInfo(po.getTwoEPInfo());//二级公司应急预案
        vo.setTwoCompany(CompanyVO.CompanyVOPO2VO(po.getTwoCompany()));//二级公司
        vo.setThreeEPInfo(po.getThreeEPInfo());//三级公司应急预案
        vo.setThreeCompany(CompanyVO.CompanyVOPO2VO(po.getThreeCompany()));//三级公司


        //根据级别设置字段
        Company currentCompany = (Company) getCurrentCompany();
        CompanyVO companyVO = CompanyVO.CompanyVOPO2VO(currentCompany);
        String planNames = planVOS.stream().map(SESWssEPEmergencyPlanVO::getPlanName).collect(Collectors.joining(","));
        switch (companyLevel) {
            case "1":
                break;
            case "2":
                vo.setTwoEPInfo(planNames);
                vo.setTwoCompany(companyVO);
                break;
            case "3":
                vo.setThreeEPInfo(planNames);
                vo.setThreeCompany(companyVO);
                break;
        }
        /*-----------------------------------------省国贸上报字段，数据库字段，结束----------------------------------------*/

    }

    private void copy2AlarmRecordPO(SESECDAlarmRecordVO vo, SESECDAlmAlarmRecord po, Company company) {
        // 设置vo对象的属性值并添加相关注释
        po.setReportEventId(vo.getId());//上报事件id
        po.setAccidentName(vo.getAccidentName()); // 事件名称
        po.setHappenTime(vo.getHappenTime()); // 事发时间
        po.setPosition(vo.getPosition()); // 发生位置
        po.setWounderPerson(vo.getWounderPerson()); // 受伤人数
        po.setDescription(vo.getDescription()); // 事件描述
        po.setMapPoint(vo.getMapPoint()); // 坐标
        po.setReceiver(getStaffByVO(vo.getReceiver())); // 接警人
        po.setRectime(vo.getRectime()); // 接警时间
//        po.setAccidentClasses(accidentClassVOS); // 事故类型
        po.setHpnCompany(getCompanyByVO(vo.getHpnCompany())); // 事发公司

        po.setProcessState(getSystemCodeByVO(vo.getProcessState())); // 处理状态
        po.setProcessState(new SystemCode("SESECD_processState/002")); // 处理状态

        po.setAlarmLevel(getSystemCodeByVO(vo.getAlarmLevel())); // 报警等级
//        po.setEmePlans(planVOS); // 应急预案
        po.setAlarmPerson(getStaffByVO(vo.getAlarmPerson())); // 接警人
        po.setAlarmPhone(vo.getAlarmPhone()); // 上报人电话
        po.setHpmDepartment(getDepartmentByVO(vo.getHpmDepartment())); // 事发部门
        po.setDeathPerson(vo.getDeathPerson()); // 死亡人数
        po.setProcess(vo.getProcess()); // 处理记录

        po.setIsIncident(false);//是否为应急事件
        po.setEventSource(new SystemCode("SESECD_eventSource/002"));//事件来源：SESECD_eventSource/001 表示本公司，SESECD_eventSource/002表示夏季公司上报

        po.setCompany(company);
        po.setCid(company.getId());//设置公司id

        /*-----------------------------------------省国贸上报字段，数据库字段，开始----------------------------------------*/

        po.setTwoEPInfo(vo.getTwoEPInfo());//二级公司应急预案
        po.setTwoCompany(getCompanyByVO(vo.getTwoCompany()));//二级公司
        po.setThreeEPInfo(vo.getThreeEPInfo());//三级公司应急预案
        po.setThreeCompany(getCompanyByVO(vo.getThreeCompany()));//三级公司

        /*-----------------------------------------省国贸上报字段，数据库字段，结束----------------------------------------*/
    }


    /**
     * 保存预案和事故类型
     *
     * @param vo            报警记录vo
     * @param companyByCode 父公司
     * @param po            报警记录po
     */
    private void savePlanAndAccidentType(SESECDAlarmRecordVO vo, Company companyByCode, SESECDAlmAlarmRecord po) {
        StringBuilder builder = new StringBuilder();
        builder.append("from ").append(SESWssEPEmergencyPlan.JPA_NAME).append(" where valid = 1");
        List<SESWssEPEmergencyPlan> plans = almAlarmRecordDao.findByHql(builder.toString());

        builder.delete(0, builder.length());

        vo.getEmePlans().forEach(c -> {
            Optional<SESWssEPEmergencyPlan> first = plans.stream().filter(p -> p.getId().equals(c.getId())).findFirst();
            if (!first.isPresent()) {
                return;
            }
            SESECDEmePlanObj planObj = new SESECDEmePlanObj();

            SESWssEPEmergencyPlan plan = new SESWssEPEmergencyPlan();
            plan.setId(first.get().getId());
            planObj.setPlanObj(plan);//预案

            planObj.setAlarmId(po);
            planObj.setCompany(companyByCode);
            planObj.setCid(companyByCode.getId());//设置公司

            emePlanObjDao.save(planObj);
            emePlanObjDao.flush();
        });

        builder.append("from ").append(SESWssERAccidentClass.JPA_NAME).append(" where valid = 1");
        List<SESWssERAccidentClass> accidentClassList = almAlarmRecordDao.findByHql(builder.toString());

        vo.getAccidentClasses().forEach(c -> {
            Optional<SESWssERAccidentClass> first = accidentClassList.stream().filter(a -> a.getId().equals(c.getId())).findFirst();
            if (!first.isPresent()) {
                return;
            }
            SESECDAlarmEnenetrel enenetrel = new SESECDAlarmEnenetrel();

            SESWssERAccidentClass accidentClass = new SESWssERAccidentClass();
            accidentClass.setId(first.get().getId());
            enenetrel.setEnenetrel(accidentClass);//事故类型

            enenetrel.setAlarmId(po);
            enenetrel.setCompany(companyByCode);
            enenetrel.setCid(companyByCode.getId());//设置公司

            alarmEnenetrelDao.save(enenetrel);
            alarmEnenetrelDao.flush();
        });
    }


    /**
     * 发送通知消息
     *
     * @param po 应急事件po
     */
    private void sendNotify(SESECDAlmAlarmRecord po) {
        //通知通讯录人员
        List<String> staffCodes = customSESECDAlarmRecordService.getStaffCodes(po);
        if (staffCodes != null && !staffCodes.isEmpty()) {
            Map<String, String> stringStringMap = Collections.singletonMap("msgContent", po.getAccidentName());
            MsgModelDTO build = MsgModelDTO.builder().param(stringStringMap).msgType(ConstDict.MSG_TYPE_EMC_ACTION).receivers(staffCodes).build();
            notifyFacade.handleNotify(build);
        }
    }

    /**
     * 更具员工VO找到远东po
     *
     * @param staffVO
     * @return
     */
    private Staff getStaffByVO(StaffVO staffVO) {
        if (staffVO == null) {
            log.error("员工为空");
            return null;
        }
        if (staffVO.getCode() != null && !staffVO.getCode().isEmpty()) {
            log.error("员工编码查找人员");
            Staff staffByCode = staffService.getStaffByCode(staffVO.getCode());
            if (staffByCode != null) {
                log.error("返回按照编码查找到的人员");
                return staffByCode;
            }
        }
        return null;
    }

    /**
     * 更具公司vo查找公司po
     *
     * @param companyVO
     * @return
     */
    private Company getCompanyByVO(CompanyVO companyVO) {
        if (companyVO == null) {
            log.error("公司vo为空");
            return null;
        }
        if (companyVO.getCode() != null && !companyVO.getCode().isEmpty()) {
            Company companyByCode = companyService.getCompanyByCode(companyVO.getCode());
            if (companyByCode != null) {
                return companyByCode;
            }
        }
        return null;
    }


    private Department getDepartmentByVO(DepartmentVO vo) {
        if (vo == null) {
            log.error("部门vo为空");
            return null;
        }
        if (vo.getCode() != null && !vo.getCode().isEmpty()) {
            log.error("更具部门编码查找人员");
            Department departmentByCode = departmentService.getDepartmentByCode(vo.getCode());
            if (departmentByCode != null) {
                return departmentByCode;
            }
        }
        return null;
    }

    /**
     * 更具系统编码vo查找系统编码po
     *
     * @param systemCodeVO
     * @return
     */
    private SystemCode getSystemCodeByVO(SystemCodeVO systemCodeVO) {
        if (systemCodeVO == null) {
            log.error("系统编码vo为空");
            return null;
        }
        if (systemCodeVO.getId() != null && !systemCodeVO.getId().isEmpty()) {
            SystemCode systemCode = (SystemCode) systemCodeService.getSystemCode(systemCodeVO.getId());
            return systemCode;
        }
        return null;
    }

}
