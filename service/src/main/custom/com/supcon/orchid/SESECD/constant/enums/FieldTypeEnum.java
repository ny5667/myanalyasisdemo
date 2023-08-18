package com.supcon.orchid.SESECD.constant.enums;

public enum FieldTypeEnum {
	STRING,
    BOOLEAN,
    INT;
    public String getType() {
        return toString().toLowerCase();
    }
}
