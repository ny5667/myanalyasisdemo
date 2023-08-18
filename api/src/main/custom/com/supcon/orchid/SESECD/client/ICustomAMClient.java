package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.AlarmApiListDto;
import com.supcon.orchid.SESECD.DTO.AlarmIdIncidentIdDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "SESWssAM" )
public interface ICustomAMClient {

    String API_PREFIX = "/v1/SESWssAM/accidentReport/accidentReport";

    /**
     * 转事故
     * @param alarmApiListDto
     */
    @GetMapping(value = API_PREFIX + "/alarmTurnAccident", produces = "application/json")
    List<AlarmIdIncidentIdDto> alarmTurnAccident(@RequestBody AlarmApiListDto alarmApiListDto);

}

