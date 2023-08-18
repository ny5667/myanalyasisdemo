package com.supcon.orchid.SESECD.services.door.access.control;


import com.supcon.orchid.SESECD.model.vo.doorAccessControl.BaseShellVo;
import com.supcon.orchid.SESECD.model.vo.doorAccessControl.EntranceGuardVO;

public interface CustomSESECDEntranceGuardService {


    /**
     * 门禁基本信息
     * @return
     */
    BaseShellVo<EntranceGuardVO> getEntranceGuardInfo();

}
