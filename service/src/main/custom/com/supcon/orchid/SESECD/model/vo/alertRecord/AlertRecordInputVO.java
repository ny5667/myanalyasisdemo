package com.supcon.orchid.SESECD.model.vo.alertRecord;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AlertRecordInputVO {


    private BigDecimal upperLimit; // 上限


    private String alarmCode; // 报警编码


    private String alarmDeviceName; // 报警装置名称


    private Date alarmTime; // 报警时间


    private BigDecimal deviceLocationX; // 报警装置坐标经度


    private String alarmSource; // 报警来源


    private BigDecimal deviceLocationY; // 报警装置坐标纬度


    private String alarmContent; // 报警内容


    private BigDecimal realTimeValue; // 实时值


    private Integer duration; // 报警持续时间（单位秒）


    private BigDecimal upperUpperLimit; // 上上限


    private BigDecimal lowerLowerLimit; // 下下限


    private BigDecimal lowerLimit; // 下限

    /**
     * 公司编码
     */
    private String companyCode;

    /**
     * 图片
     */
    private List<String> images;

    /**
     * 视频
     */
    private List<String> videos;

}
