package com.supcon.orchid.SESECD.DTO;

import java.util.List;

/**
 * @author: xulong2
 * @create: 2021-03-12 10:06
 * @description
 **/
public class AlarmApiListDto {
    private Long cid;
    private List<AlarmApiDto> alarmApiDtoList;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public List<AlarmApiDto> getAlarmApiDtoList() {
        return alarmApiDtoList;
    }

    public void setAlarmApiDtoList(List<AlarmApiDto> alarmApiDtoList) {
        this.alarmApiDtoList = alarmApiDtoList;
    }
}