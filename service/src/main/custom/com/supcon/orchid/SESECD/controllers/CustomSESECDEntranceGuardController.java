package com.supcon.orchid.SESECD.controllers;


import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;
import com.supcon.orchid.SESECD.model.vo.doorAccessControl.BaseShellVo;
import com.supcon.orchid.SESECD.model.vo.doorAccessControl.EntranceGuardVO;
import com.supcon.orchid.SESECD.services.door.access.control.CustomSESECDEntranceGuardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 门禁信息控制层
 */
@RestController

public class CustomSESECDEntranceGuardController {

    @Autowired
    private CustomSESECDEntranceGuardService entranceGuardService;

    /**
     * 门禁基本信息
     * @return
     */
    @PostMapping("/public/VxECD/doorAccessControl/EntranceGuard/getEntranceGuardInfo")
    @ResponseBody
    public BaseInfoVO getEntranceGuardInfo(){
        BaseShellVo<EntranceGuardVO> result = entranceGuardService.getEntranceGuardInfo();
        return BaseInfoVO.ok(result);
    }
}
