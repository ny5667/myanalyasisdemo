package com.supcon.orchid.SESECD.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于海康交互对接，安全认证
 */
public class HikvisionRestHelper {

    private static Log log = LogFactory.getLog(HikvisionRestHelper.class);

    /**
     * 调取接口
     * @param body 组装请求参数：JSONObject类型数据
     * @param interfaceUrl 接口地址
     * @return
     */
    public static String send(String body,String interfaceUrl){
        log.error("利用第三方API获取数据");

        //STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
        ArtemisConfig.host = "192.128.100.203:443"; // 平台的ip端口
        ArtemisConfig.appKey = "26104667";  // 密钥appkey
        ArtemisConfig.appSecret = "dTUaAuHip4OJESck85V7";// 密钥appSecret

        //STEP2：设置OpenAPI接口的上下文
        final String ARTEMIS_PATH = "/artemis";

        //STEP3：设置接口的URI地址
        final String url = ARTEMIS_PATH + interfaceUrl;
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", url );//根据现场环境部署确认是http还是https
            }
        };
        log.error("上报地址：" + JSON.toJSONString(path));

        //STEP4：设置参数提交方式
        String contentType = "application/json";

        //STEP5：调用接口
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType , null);// post请求application/json类型参数
        log.error("数据获取结束，result长度："+result.length());
        return result;
    }

//    public static void main(String[] args) {
//        JSONObject jsonBody = new JSONObject();
//        jsonBody.put("cameraIndexCode", "748d84750e3a4a5bbad3cd4af9ed5101");
//        jsonBody.put("streamType", 0);
//        jsonBody.put("protocol", "rtsp");
//        jsonBody.put("transmode", 1);
//        jsonBody.put("expand", "streamform=ps");
//        String body = jsonBody.toJSONString();
//        String interfaceUrl = "*****";
//        String result = send(body,interfaceUrl);
//        System.out.println("result结果示例: " + result);
//    }
}
