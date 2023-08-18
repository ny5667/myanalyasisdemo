package com.supcon.orchid.SESECD.model.vo.common;

import com.supcon.orchid.SESECD.model.vo.common.EabSetionVO;
import lombok.Data;

@Data
public class MainDepartVO {
    /**
     * 责任单位id
     */
    private Long id;

    /**
     * 通讯组对象
     */
    private EabSetionVO eabSetion;
}
