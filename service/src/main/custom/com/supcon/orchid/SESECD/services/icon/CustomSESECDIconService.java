package com.supcon.orchid.SESECD.services.icon;

import com.supcon.orchid.SESECD.model.vo.forfront.icon.IconLibraryVO;
import com.supcon.orchid.support.Result;

import java.util.List;
import java.util.Map;

public interface CustomSESECDIconService {

    /**
     * 返回图标库数据给前端调用
     * @return
     */
    Result<Map<String, List<IconLibraryVO>>> getEmergencyIconLibraryInfo();

}
