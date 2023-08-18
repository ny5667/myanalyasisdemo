package com.supcon.orchid.SESECD.services.notify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.SesCommonFunc.dto.WebSocketParamDTO;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.supfusion.framework.cloud.common.context.RpcContext;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import com.supcon.supfusion.notification.apiserver.api.SendNoticeV2InternalApi;
import com.supcon.supfusion.notification.apiserver.api.dto.RangeDTO;
import com.supcon.supfusion.notification.apiserver.api.dto.SendWithTopicRequestDTO;
import com.supcon.supfusion.notification.apiserver.api.dto.SendWithTopicResponseDTO;
import com.supcon.supfusion.notification.common.bean.RangeType;
import com.supcon.supfusion.ws.client.NoticeApiClient;
import com.supcon.supfusion.ws.client.dto.WebSocketResponseDTO;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 平台默认实现消息通知
 * 平台默认实现无需实现NotifyWay接口，由平台配置决定
 */
@Service("defaultNotify")
public class DefaultNotifyImpl extends BaseServiceImpl implements NotifyStrategy, NotifyWay {

    @Autowired
    private SendNoticeV2InternalApi sendNoticeV2InternalApi;

    @Autowired
    private NoticeApiClient noticeApiClient;

    @Override
    public String notify(MsgModelDTO msgModel) {
        log.error("Enter sendMsg Method:");
        log.error(JsonHelper.writeValue(msgModel));
        if (Objects.isNull(msgModel)) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isEmpty(msgModel.getMsgType())) {
            log.error("MsgType 为空");
            return StringUtils.EMPTY;
        }
        if (CollectionUtils.isEmpty(msgModel.getReceivers())) {
            log.error("Receivers 为空");
            return StringUtils.EMPTY;
        }

        SendWithTopicRequestDTO dto = getDto(msgModel.getMsgType(), JsonHelper.writeValue(msgModel.getParam()), msgModel.getReceivers());
        log.error("调用发送主题接口:");
        log.error(JsonHelper.writeValue(dto));
        try{
            Result<SendWithTopicResponseDTO> topic = sendNoticeV2InternalApi.topic(dto);
            log.error("Exiting sendMsg Method:");
            log.error(JsonHelper.writeValue(topic));
            return topic.toString();
        }catch (Exception ex){
            log.error("发送平台消息报错");
            log.error(ex.getMessage(),ex);
            return "fail";
        }
    }


    @Override
    public void sendWebSocket(WebSocketParamDTO param) {
        log.error("进入：发送webSocket方法");
        if (Objects.isNull(param)) {
            return;
        }
        if (StringUtils.isEmpty(param.getMessage())) {
            return;
        }
        if (Collections.isEmpty(param.getGroupList())) {
            return;
        }
        log.error(param.getGroupList().toString());
        log.error(param.getMessage());

        String tenantId = RpcContext.getContext().getTenantId();
        log.error("tenantId = " + tenantId);
        for (String topic : param.getGroupList()) {
            WebSocketResponseDTO webSocketResponseDTO = noticeApiClient.pushTopicMessages(topic, tenantId, param.getMessage());
            log.error(">>> webSocket接口返回:" + JSON.toJSONString(webSocketResponseDTO));
        }
        log.error("退出：发送webSocket方法");
    }

    private SendWithTopicRequestDTO getDto(String type, String param, List<String> staffCode) {
        SendWithTopicRequestDTO dto = new SendWithTopicRequestDTO();
        dto.setBsmodCode("SESECD");
        dto.setBsmodName("消息提醒");
        dto.setTopicCode(type);
        dto.setParam(JSONObject.parseObject(param));
        if (!Objects.isNull(staffCode) && staffCode.size() > 0) {
            RangeDTO rangeDTO = new RangeDTO();
            rangeDTO.setRangeType(RangeType.STAFF);
            rangeDTO.setCodes(staffCode);
            List<RangeDTO> rangeDTOList = new ArrayList();
            rangeDTOList.add(rangeDTO);
            dto.setReceivers(rangeDTOList);
        }
        return dto;
    }


}
