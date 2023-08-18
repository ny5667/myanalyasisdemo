package com.supcon.orchid.SESECD.model.dto.outward.jobctl;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * <p>
 *     惠州石化作业受控响应体
 * </p>
 * @author lufengdong
 * @create 2023-04-21 14:45
 */
@Getter
@Setter
public class HuiZhouWorkResponseDTO {

    /**
     * id
     */
    private BigDecimal posId;
    /**
     * 许可id
     */
    private BigDecimal operationId;
    /**
     * 作业地点名称
     */
    private String operLocation;
    /**
     * 作业内容
     */
    private String operContent;
    /**
     * 作业状态
     * //｛-4取消预约，-2暂存，-1保存，0计划中，1申请，2审批流转中，5完成审批，6审核不通过，7已暂停，8已取消，10已关闭，11后续日未确认｝
     */
    private int curstatus;
    /**
     *  员工名称
     */
    private String uname;
    /**
     *  员工性别
     */
    private String usex;
    /**
     *  员工邮箱
     */
    private String uemail;
    /**
     * 员工电话
     */
    private String umobile;
    /**
     * 经度
     */
    private String px;
    /**
     * 维度
     */
    private String py;
    /**
     * 坐标距离
     */
    private String jl;
}
