package com.supcon.orchid.SESECD.model.vo.alertRecord;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestVO {

    @NotNull(message = "pageSize is null")
    private Integer pageSize;

    @NotNull(message = "pageNo is null")
    private Integer pageNo;

}