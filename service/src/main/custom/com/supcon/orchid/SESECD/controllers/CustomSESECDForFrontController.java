package com.supcon.orchid.SESECD.controllers;

import com.alibaba.fastjson.JSON;
import com.supcon.orchid.SESECD.model.vo.forfront.*;
import com.supcon.orchid.SESECD.model.vo.forfront.icon.IconLibraryVO;
import com.supcon.orchid.SESECD.services.action.CustomSESECDActionForFrontService;
import com.supcon.orchid.SESECD.services.alarm.record.CustomSESECDEventForFrontService;
import com.supcon.orchid.SESECD.services.forfront.CustomSESECDForFrontService;
import com.supcon.orchid.SESECD.services.situation.CustomSESECDSituationForFrontService;
import com.supcon.orchid.SESECD.services.icon.CustomSESECDIconService;
import com.supcon.orchid.support.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @menu 地图前端
 */
@Controller
public class CustomSESECDForFrontController {

    private static final Logger logger = LoggerFactory.getLogger(CustomSESECDForFrontController.class);

    @Autowired
    private CustomSESECDForFrontService forFrontService;

    @Autowired
    private CustomSESECDActionForFrontService customSESECDActionForFrontService;

    @Autowired
    private CustomSESECDSituationForFrontService customSESECDSituationForFrontService;

    @Autowired
    private CustomSESECDEventForFrontService customSESECDEventForFrontService;

    @Autowired
    private CustomSESECDIconService customSESECDIconService;

    /**
     * 事件列表(带详情)
     *
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/almAlarmRecord/eventlist")
    @ResponseBody
    public Result<List<EventDetailVO>> eventList(@RequestParam(name = "accidentName") String accidentName) {
        logger.error("事件list接口被调用");
        List<EventDetailVO> vos = null;
        try {
            vos = customSESECDEventForFrontService.listEvents(accidentName);
            return Result.data(vos);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }


    /**
     * 结束事件
     * @return http://192.168.95.250:8080/msService/SESECD/alarmRecord/alarmRecord/overEvents
     @PostMapping("/SESECD/alarmRecord/almAlarmRecord/endevent")
     @ResponseBody public Result endEvent(@RequestParam("eventId") Long eventId) {
     logger.error("结束事件接口被调用");
     try {
     Integer effetRows = forFrontService.endEvent(eventId);
     return Result.success("success");
     } catch (Exception e) {
     return Result.fail(e.getMessage());
     }
     }
     */

    /**
     * 应急事件详情接口
     *
     * @param eventId 事件id
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/almAlarmRecord/eventdetail")
    @ResponseBody
    public Result<EventDetailVO> eventDetail(@RequestParam("eventId") Long eventId) {
        if (null == eventId) {
            return Result.fail("input param invalid");
        }
        logger.error("事件详情接口被调用,入参:{}", eventId);
        EventDetailVO vo = customSESECDEventForFrontService.getEventDetail(eventId);
        return Result.data(vo);
    }

    /**
     * 事件动态 接口
     *
     * @param eventId 事件id
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/almAlarmRecord/eventdynamictrend")
    @ResponseBody
    public Result<List<EventDynamicTrendVO>> dynamicEvents(@RequestParam("eventId") Long eventId) {
        if (null == eventId) {
            return Result.fail("input param invalid");
        }
        logger.error("事件动态接口被调用,入参:{}", eventId);
        List<EventDynamicTrendVO> vos = forFrontService.listEventDynamicTrends(eventId);
        return Result.data(vos);
    }

    /**
     * 查询某个事件下的应急态势 list 接口
     *
     * @param eventId 事件id
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/almAlarmRecord/situation/list")
    @ResponseBody
    public Result<List<EventSituationVO>> listSituation(@RequestParam("eventId") Long eventId) {
        logger.error("应急态势列表接口被调用,入参:{}", eventId);
        if (null == eventId) {
            return Result.fail("input param invalid");
        }
        List<EventSituationVO> vos = customSESECDSituationForFrontService.listEventSituation(eventId);
        return Result.data(vos);
    }


    /**
     * 应急态势 新增  接口
     *
     * @param eventSituationVO 态势
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/almAlarmRecord/situation/add")
    @ResponseBody
    public Result addSituation(@RequestBody EventSituationVO eventSituationVO) {
        logger.error("新增态势接口被调用,入参:");
        logger.error(JSON.toJSONString(eventSituationVO));
        if (null == eventSituationVO || null == eventSituationVO.getReportPerson()
                || StringUtils.isEmpty(eventSituationVO.getOccursTime()) || null == eventSituationVO.getEvent()
                || null == eventSituationVO.getIcon() || null == eventSituationVO.getSituationType()
                || null == eventSituationVO.getPoint() || CollectionUtils.isEmpty(eventSituationVO.getPoint().getCoordinates())
                || null == eventSituationVO.getSource() || StringUtils.isEmpty(eventSituationVO.getSource().getId())
        ) {
            return Result.fail("input param invalid");
        }
        Integer effetRows = null;
        try {
            effetRows = customSESECDSituationForFrontService.addSituation(eventSituationVO);
        } catch (Exception e) {
            return Result.fail(e.getMessage().toString());
        }
        if (effetRows > 0) {
            return Result.success("success");
        } else {
            return Result.fail("添加失败");
        }
    }

    /**
     * 应急态势 修改  接口
     *
     * @param eventSituationVO 态势
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/almAlarmRecord/situation/update")
    @ResponseBody
    public Result updateSituation(@RequestBody EventSituationVO eventSituationVO) {
        logger.error("修改态势接口被调用");
        if (null == eventSituationVO || null == eventSituationVO.getId()) {
            return Result.fail("input param invalid");
        }
        Integer effetRows = null;
        try {
            effetRows = customSESECDSituationForFrontService.updateSituation(eventSituationVO);
        } catch (Exception e) {
            return Result.fail(e.getMessage().toString());
        }
        if (effetRows > 0) {
            return Result.success("success");
        } else {
            return Result.fail("修改失败");
        }
    }

    /**
     * 应急态势 发布  接口
     *
     * @param situationId 态势id
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/almAlarmRecord/situation/release")
    @ResponseBody
    public Result releaseSituation(@RequestParam("situationId") Long situationId) {
        logger.error("发布态势接口被调用");
        if (situationId == null) {
            return Result.fail("input param invalid");
        }
        Integer effetRows = null;
        try {
            effetRows = customSESECDSituationForFrontService.releaseSituation(situationId);
        } catch (Exception e) {
            return Result.fail(e.getMessage().toString());
        }
        if (effetRows > 0) {
            return Result.success("success");
        } else {
            return Result.fail("修改失败");
        }
    }

    /**
     * 应急态势 删除  接口
     *
     * @param situationId 态势id
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/almAlarmRecord/situation/delete")
    @ResponseBody
    public Result deleteSituation(@RequestParam("situationId") Long situationId) {
        logger.error("删除态势接口被调用,态势id:{}", situationId);
        if (situationId == null) {
            return Result.fail("input param invalid");
        }
        Integer effetRows = customSESECDSituationForFrontService.deleteSituation(situationId);
        if (effetRows > 0) {
            return Result.success("success");
        } else {
            return Result.fail("删除失败");
        }
    }

    /**
     * 查询某个事件下的应急行动 list 接口
     *
     * @param eventId 事件id
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/almAlarmRecord/action/list")
    @ResponseBody
    public Result<List<EventActionVO>> listActions(@RequestParam("eventId") Long eventId) {
        logger.error("应急行动列表接口被调用,入参:{}", eventId);
        if (null == eventId) {
            return Result.fail("input param invalid");
        }
        List<EventActionVO> vos = customSESECDActionForFrontService.listEventAction(eventId);
        return Result.data(vos);
    }

    /**
     * 应急行动 新增  接口
     *
     * @param actionVO 行动
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/almAlarmRecord/action/add")
    @ResponseBody
    public Result addAction(@RequestBody EventActionVO actionVO) {
        logger.error("新增行动接口被调用");
        if (null == actionVO) {
            return Result.fail("input param invalid");
        }
        if (StringUtils.isEmpty(actionVO.getActionTime()) || null == actionVO.getEvent() || null == actionVO.getEvent().getId()
                || CollectionUtils.isEmpty(actionVO.getOwnMainPeople())
        ) {
            return Result.fail("input param invalid");
        }
        Integer effetRows = null;
        try {
            effetRows = customSESECDActionForFrontService.addAction(actionVO);
        } catch (Exception e) {
            return Result.fail(e.getMessage().toString());
        }
        if (effetRows > 0) {
            return Result.success("success");
        } else {
            return Result.fail("添加失败");
        }
    }


    /**
     * 应急行动 修改  接口
     *
     * @param actionVO 行动
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/almAlarmRecord/action/update")
    @ResponseBody
    public Result updateAction(@RequestBody EventActionVO actionVO) throws Exception {
        logger.error("修改应急行动接口被调用");
        //行动id为空,则报错
        if (null == actionVO || null == actionVO.getId()) {
            return Result.fail("input param invalid, missing action id");
        }

        Integer effetRows = null;
        //try {
            effetRows = customSESECDActionForFrontService.updateAction(actionVO);
        //} catch (Exception e) {
        //    return Result.fail(e.getMessage().toString());
        //}

        if (effetRows > 0) {
            return Result.success("success");
        } else {
            return Result.fail("修改失败");
        }
    }

    /**
     * 应急行动 删除  接口
     *
     * @param actionId
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/almAlarmRecord/action/delete")
    @ResponseBody
    public Result deleteAction(@RequestParam("actionId") Long actionId) {
        logger.error("删除应急行动接口被调用");
        if (actionId == null) {
            return Result.fail("input param invalid");
        }
        Integer effetRows = null;
        try {
            effetRows = customSESECDActionForFrontService.deleteAction(actionId);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
        if (effetRows > 0) {
            return Result.success("success");
        } else {
            return Result.fail("删除失败");
        }
    }

    /**
     * 新增接警
     *
     * @param
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/almAlarmRecord/event/add")
    @ResponseBody
    public Result addEvent(@RequestBody EventDetailVO eventVO) throws Exception {
        logger.error("新增接警事件接口被调用");
        if (null == eventVO || StringUtils.isEmpty(eventVO.getAccidentName()) || StringUtils.isEmpty(eventVO.getDescription())
                || null == eventVO.getReceiver() || null == eventVO.getReceiver().getId()
                || StringUtils.isEmpty(eventVO.getHappenTime())

        ) {
            logger.error("参数有误");
            return Result.fail("input param invalid");
        }

        return customSESECDEventForFrontService.addEvent(eventVO);
    }


    /**
     * 截图发送
     * @param screenSendVO
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/SESECD/alarmRecord/screenshotSend", produces = "application/json")
    @ResponseBody
    public Result<String> screenshotSend(@RequestBody ScreenSendVO screenSendVO) throws IOException {
        customSESECDEventForFrontService.screenshotSend(screenSendVO);
        return Result.data(null);
    }

    @GetMapping("/SESECD/alarmRecord/alarmRecord/icon/list")
    @ResponseBody
    public Result<Map<String, List<IconLibraryVO>>> getAlertRecordInfoInfoByFeatures() {
        return customSESECDIconService.getEmergencyIconLibraryInfo();
    }

    /**
     * 右侧动态面板操作记录
     * @param eventId
     * @return
     */
    @GetMapping("/SESECD/alarmRecord/changeLog")
    @ResponseBody
    public Result<List<ChangeLogVO>> changeLog(@RequestParam(value = "eventId") Long eventId,
                                               @RequestParam(value = "page",required = false) Long page,
                                               @RequestParam(value = "pageSize",required = false) Long pageSize
                                               ) {
        List<ChangeLogVO> logVOS = forFrontService.changeLog(eventId);
        return Result.data(logVOS);
    }
    /**
     * 应急行动更新
     * @param trackVO
     * @return
     */
    @PostMapping("/SESECD/emcAction/track")
    @ResponseBody
    public Result<Void> track(@RequestBody TrackVO trackVO) {
        customSESECDActionForFrontService.track(trackVO);
        return Result.success("ok");

    }

}
