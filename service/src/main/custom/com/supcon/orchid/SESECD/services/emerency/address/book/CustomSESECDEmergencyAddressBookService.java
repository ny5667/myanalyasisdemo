package com.supcon.orchid.SESECD.services.emerency.address.book;

import com.supcon.orchid.SESECD.model.vo.common.BaseInfoVO;

public interface CustomSESECDEmergencyAddressBookService {

    /**
     * 通过应急事件查询应急通讯小组成员信息
     *
     * @param eventId
     * @return
     */
    BaseInfoVO getEmerStaffByEmerEventId(Long eventId);


}
