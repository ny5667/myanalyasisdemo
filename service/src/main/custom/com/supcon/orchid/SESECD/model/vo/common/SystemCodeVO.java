package com.supcon.orchid.SESECD.model.vo.common;

import com.supcon.orchid.SESECD.services.impl.forfront.CustomSESECDForFrontServiceImpl;
import com.supcon.orchid.foundation.entities.SystemCode;
import com.supcon.orchid.i18n.InternationalResource;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class SystemCodeVO {
    /**
     * id
     */
    private String id;
    /**
     * type
     */
    private String type;
    /**
     * code
     */
    private String code;
    /**
     * entitycode
     */
    private String entityCode;
    /**
     * 原始值
     */
    private String value;
    /**
     * 中文值
     */
    private String cnValue;
    /**
     * attribute
     */
    private boolean attribute;

    public static SystemCodeVO CreateSystemCodeVO(SystemCode input) {
        if (null == input) {
             return null;
        }
        SystemCodeVO vo = new SystemCodeVO();
        vo.setId(input.getId());
        vo.setType(input.getType());
        vo.setCode(input.getCode());
        vo.setEntityCode(input.getEntityCode());
        vo.setValue(InternationalResource.get(input.getValue(), CustomSESECDForFrontServiceImpl.CurrentLanguage));
        vo.setCnValue(InternationalResource.get(input.getValue(), CustomSESECDForFrontServiceImpl.CurrentLanguage));
        return vo;
    }

    /*
    public static SystemCode CreateSystemCodePO(SystemCodeVO input) {
        if (null == input) {
            return null;
        }
        SystemCode po = new SystemCode();
        po.setType(input.getType());
        po.setCode(input.getCode());
        po.setEntityCode(input.getEntityCode());
        po.setValue(input.getValue());
        SystemCode systemCode = new SystemCode()
        return po;
    }
     */


    public static SystemCode CreateSystemCodePOById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        SystemCode systemCodePO = new SystemCode(id);
        return systemCodePO;
    }
}
