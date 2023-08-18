package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESWssEP.entities.SESWssEPEmergencyPlan;
import com.supcon.orchid.support.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "SESWssER")
public interface CustomSESWssERClient {

    String API_PREFIX = "/SESWssEP/emergencyPlan/emergencyPlan/getPlanActions";

//    /**
//     * 获取全部状态是已完成的预案列表
//     */
//    @GetMapping(API_PREFIX + "/getAllEmergencyPlan")
//    Result<List<SESWssEPEmergencyPlan>> getAllEmergencyPlan(String name);
}