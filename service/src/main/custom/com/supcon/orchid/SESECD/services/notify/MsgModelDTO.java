package com.supcon.orchid.SESECD.services.notify;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 该类作为所有通知类型接受大类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgModelDTO implements Serializable {


    //***********************************************通用参数*******************************************//
    /**
     * 消息主题编号
     */
    private String msgType;

    /**
     * 消息接收者编码数组
     * 平台人员staffCode
     */
    private List<String> receivers;

    /**
     * 模板内容需要填充的值
     */
    private Map<String, String> param;


    /**
     * 扩展数据
     * <p>
     * 当除了模板内容所需参数之外仍需传递额外参数时，放在该字段中 如
     * 邮件标题：key：title
     * </p>
     */
    private Map<String, Object> extraParams;

    public static MsgModelDTO build(String msgType, List<String> receivers, Map<String, String> param, Map<String, Object> extraParams) {
        return MsgModelDTO.builder().msgType(msgType).receivers(receivers).param(param).extraParams(extraParams).build();
    }
}



