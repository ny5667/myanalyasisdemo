package com.supcon.orchid.SESECD.model.vo.forfront;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class ScreenSendVO {
    /**
     * 图片base64码
     */
    @NotNull(message = "VxECD.custom.random1684310700049")
    private String base64;

    /**
     * 事件ID
     */
    @NotNull(message = "VxECD.custom.random1684310638747")
    private Long eventId;

    /**
     * 接收人
     */
    private List<String> staffCodes;


}
