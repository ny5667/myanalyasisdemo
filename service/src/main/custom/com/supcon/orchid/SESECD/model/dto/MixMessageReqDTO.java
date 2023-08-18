package com.supcon.orchid.SESECD.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 *     融合通讯请求体
 * </p>
 *
 * @author lufengdong
 * @create 2023-04-28 10:15
 */
@Getter
@Setter
public class MixMessageReqDTO {
    /**
     * 消息接收者集合
     */
    @NotEmpty(message = "接收人不允许为空-这里填写国际化信息")
    private List<Long> receivers;
    /**
     * 发送消息的内容
     */
    @NotBlank(message = "消息内容不允许为空")
    private String content;
    /**
     * 接收消息的类型
     * 可选值  短信-1  邮件-2 应用消息-3
     */
    @NotEmpty(message = "邮件发送类型-这里填写国际化信息")
    private List<Integer> messageTypes;

    /**
     * 小组类型：应急小组-》1 应急专家 -》2
     */
    private Integer teamType;

    /**
     * 接警记录idString
     */
    //@NotEmpty(message = "接警记录id不能为空")
    private Long almAlarmRecordID;
}
