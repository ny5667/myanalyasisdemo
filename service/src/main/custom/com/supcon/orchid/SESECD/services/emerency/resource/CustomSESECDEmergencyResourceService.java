package com.supcon.orchid.SESECD.services.emerency.resource;

import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;

public interface CustomSESECDEmergencyResourceService {

    /**
     * 通过应急事件所拥有的事故类型过滤查询应急专家
     *
     * @param eventId
     * @return
     */
    BaseInfoVO getEmerExportsByEmerEventId(Long eventId);

}
