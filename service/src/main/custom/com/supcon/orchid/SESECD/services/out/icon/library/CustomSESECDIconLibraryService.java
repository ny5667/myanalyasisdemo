package com.supcon.orchid.SESECD.services.out.icon.library;

//import com.supcon.orchid.SESGISConfig.DTO.EmergencyIconLibraryInfoDTO;

import com.supcon.orchid.SESECD.model.dto.out.icon.library.EmergencyIconLibraryInfoDTO;

import java.util.List;

public interface CustomSESECDIconLibraryService {

    /**
     * 调用地图接口，返回应急图标数据
     *
     * @return
     */
    List<EmergencyIconLibraryInfoDTO> getEmergencyIconLibraryInfo();

}
