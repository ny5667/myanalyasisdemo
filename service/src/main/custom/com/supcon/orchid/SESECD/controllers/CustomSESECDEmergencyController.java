package com.supcon.orchid.SESECD.controllers;


import com.supcon.orchid.SESECD.model.vo.emergencyplan.AllEmerMemberVO;
import com.supcon.orchid.SESECD.services.relate.plan.CustomSESECDRelatePlanService;
import com.supcon.orchid.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @version video 6.0
 * @name
 * @author： luoheng
 * @date： 2021-03-20 15:43
 */
@Controller
public class CustomSESECDEmergencyController {


    @Autowired
    private CustomSESECDRelatePlanService customSESECDQueryEmergencyService;


    @GetMapping("/SESECD/emergency/emergency/query")
    @ResponseBody
    public Result<List<AllEmerMemberVO>> queryEmergency() {
        return Result.data(customSESECDQueryEmergencyService.queryEmergency());
    }


}
