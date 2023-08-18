package com.supcon.orchid.SESECD.services.impl.outward.screen;

import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.entities.SESECDEcdParamConfig;
import com.supcon.orchid.SESECD.services.outward.screen.CustomSESECDScreenCommandService;
import com.supcon.orchid.SESECD.services.paramconfig.CustomSESECDParamConfigService;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.SESECD.utils.SocketInvokeUtils;
import com.supcon.orchid.SESECD.utils.SocketPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 惠州三方大屏指令
 */
@Service("HZScreenCommand")
@Transactional
@Slf4j
public class HZSESECDScreenCommandServiceImpl implements CustomSESECDScreenCommandService {
    private static final String getScreenCommand = "/0x01:0;";
    private static final String planListCommand = "/0x02:";
    private static final String applyPlanCommand = "/0x03:";

    private static final String GBK = "GBK";
    @Autowired
    private CustomSESECDParamConfigService paramConfigService;

    /**
     * 打开大屏
     */
    @Override
    public void on() {
        Map<String, String> config = getConfig();
        String planName = config.get(ConstDict.SCREEN_COMMAND_PLAN_NAME);
        SocketPoolUtils.init(config.get(ConstDict.SCREEN_COMMAND_IP), Integer.parseInt(config.get(ConstDict.SCREEN_COMMAND_PORT)));
        //1-获取大屏信息指令 获取大屏id;大屏name;  /ack:Test12;智慧大屏;
        String ret;
        Socket socket = SocketPoolUtils.borrowSocket();
        try {
            ret = SocketInvokeUtils.sendCommand(socket, getScreenCommand, GBK, GBK);
            //ret = "/ack:Test12;智慧大屏;";
        } catch (IOException | InterruptedException e) {
            log.error("HZSESECDScreenCommandServiceImpl=====on：sendCommand失败：命令内容：" + getScreenCommand + ", 编码：" + GBK + " 解码： " + GBK);
            e.printStackTrace();
            return;
        }
        SocketPoolUtils.returnSocket(socket);
        //剥离/ack头部拼接/协议命令号得到下一次指令
        //"/ack:Test13;智慧大屏;"
        String idAndName = ret.replace("/ack:", "");
        //2-获取预案列表指令 获取预案id;预案name;(多个)  /0x02:Test13;智慧大屏   /ack:[id;name]  /ack:1;01;2;02;3;aaa;
        String planCommand = planListCommand + idAndName;
        log.error("获取预案列表指令： " + planCommand);
        //每次重新从池中获取，防止服务器主动断开socket
        socket = SocketPoolUtils.borrowSocket();
        try {
            ret = SocketInvokeUtils.sendCommand(socket, planCommand, GBK, GBK);
            //ret = "/ack:1;01;2;02;3;aaa;";
        } catch (IOException | InterruptedException e) {
            log.error("HZSESECDScreenCommandServiceImpl=====on：sendCommand失败：命令内容：" + getScreenCommand + ", 编码：" + GBK + " 解码： " + GBK);
            e.printStackTrace();
            return;
        }
        SocketPoolUtils.returnSocket(socket);
        //预习预案列表，找到属于指挥一张图的
        ret = ret.replace("/ack:", "");
        Map<String, String> idAndNameMap = getNameAndIdMap(ret);
        log.error("获取预案列表map： " + JsonHelper.writeValue(idAndNameMap));
        String planId = idAndNameMap.get(planName);
        //3-应用预案指令 大屏id+大屏name+场景id(预案id)  /0x03:大屏id;大屏名称；预案id
        String applyCommand = applyPlanCommand + idAndName + planId + ";";
        log.error("应用场景指令： " + applyCommand);
        socket = SocketPoolUtils.borrowSocket();
        try {
            ret = SocketInvokeUtils.sendCommand(socket, applyCommand, GBK, GBK);
            //ret = "/ack:0";
        } catch (IOException | InterruptedException e) {
            log.error("HZSESECDScreenCommandServiceImpl=====on：sendCommand失败：命令内容：" + getScreenCommand + ", 编码：" + GBK + " 解码： " + GBK);
            e.printStackTrace();
            return;
        }
        log.error("应用场景指令返回： " + ret);
        if (!ret.equalsIgnoreCase("/ack:0")) {
            log.error("大屏应用返回失败： 返回值：" + ret);
        }
        SocketPoolUtils.returnSocket(socket);
    }

    /**
     * 退出大屏
     */
    @Override
    public void off() {
    }

    /**
     * 获取大屏指令配置
     *
     * @return
     */
    private Map<String, String> getConfig() {
        SESECDEcdParamConfig code = paramConfigService.getConfigByConfigCode(ConstDict.SCREEN_COMMAND);
        Map<String, String> parameterOptions = paramConfigService.getParameterOptions(code);
        return parameterOptions;
    }

    /**
     * 获取预案name，预案id映射
     *
     * @param str
     * @return
     */
    public Map<String, String> getNameAndIdMap(String str) {
        Map<String, String> map = new HashMap<>();
        String[] split = str.split(";");
        for (int i = 0; i < split.length; i += 2) {
            String id = split[i];
            String name = split[i + 1];
            map.put(name, id);
        }

        return map;
    }


}
