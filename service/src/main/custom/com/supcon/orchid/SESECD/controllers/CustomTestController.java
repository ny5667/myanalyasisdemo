package com.supcon.orchid.SESECD.controllers;

import com.supcon.orchid.SESECD.annotation.LocalCache;
import com.supcon.orchid.SESECD.annotation.StopWatchTime;
import com.supcon.orchid.SESECD.component.ChangeLogEvent;
import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.model.dto.common.ChangeLogDTO;
import com.supcon.orchid.SESECD.services.impl.outward.screen.ScreenCommandFacade;
import com.supcon.orchid.SESECD.services.notify.DefaultNotifyImpl;
import com.supcon.orchid.SESECD.services.notify.MsgModelDTO;
import com.supcon.orchid.SESECD.services.notify.NotifyFacade;
import com.supcon.orchid.utils.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 单元测试
 */
@RestController
public class CustomTestController {
    private MsgModelDTO modelDTO ;
    {

        Map<String, String> modelParam = new HashedMap();
        modelParam.put("accidentName", "测试事故名称");
        modelParam.put("actionAddr", "钱塘江边");
        modelParam.put("actionTime", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        modelParam.put("actionState", "待执行");
        modelParam.put("description", "发生大水");
        Map<String, Object> extraParams = new HashMap<>(2);
        extraParams.put("title", "应急行动通知");
        modelDTO = MsgModelDTO.builder().msgType(ConstDict.MSG_TYPE_ALARM_ACTION).param(modelParam).receivers(Arrays.asList("wcz")).build();
    }
    @Autowired
    private NotifyFacade notifyFacade;
    @Autowired
    private DefaultNotifyImpl defaultNotify;

    /**
     * <p>
     * 测试SESECD模块下使用使用VXECD路径是否能访问
     *
     * 经测试 在本地可以访问  但部署到平台路径携带msService会报404错误 网关找不到
     * </p>
     * @return
     */
    @PostMapping("/VXECD/alarmRecord/alarmRecord/test")
    public String sendMsgToPerson() {
        return "ok";
    }

    /**
     * 测试配置消息提醒
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/alarmRecord/notify")
    public String notifyMes() {

        String s = notifyFacade.handleNotify(modelDTO);
        System.out.println(s);
        return "ok";
    }

    /**
     * 测试手动指定平台消息
     * @return
     */
    @PostMapping("/SESECD/alarmRecord/alarmRecord/pltest")
    public String notifydef() {

        //测试指定平台通知
        String notify = defaultNotify.notify(modelDTO);
        System.out.println(notify);
        return "ok";
    }

    /**
     * 用于测试接口统计耗时切面
     * @return
     * @throws InterruptedException
     */
    @StopWatchTime
    @PostMapping("/SESECD/alarmRecord/alarmRecord/testCost")
    @LocalCache(period = 600)
    public String testCost() throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
        return "ok";
    }

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 测试事件发布
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/SESECD/alarmRecord/alarmRecord/testlis")
    public String testlis() throws InterruptedException {
        ChangeLogDTO logDTO = ChangeLogDTO.builder().content("aaa").build();
        ChangeLogEvent changeLogEvent = new ChangeLogEvent(logDTO);

        applicationContext.publishEvent(changeLogEvent);
        return "ok";
    }


    @Autowired
    ScreenCommandFacade screenCommandFacade;
    /**
     * 测试三方大屏指令
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/SESECD/alarmRecord/alarmRecord/testCommand")
    public String testCommand()  {
        screenCommandFacade.on();
        return "ok";
    }





}
