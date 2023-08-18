package com.supcon.orchid.SESECD.services.impl.out.icon.library;

import com.supcon.orchid.SESECD.constant.consist.ConstDict;
import com.supcon.orchid.SESECD.model.dto.out.icon.library.EmergencyIconLibraryInfoDTO;
import com.supcon.orchid.SESECD.services.out.icon.library.CustomSESECDIconLibraryService;
import com.supcon.orchid.SESECD.utils.JsonHelper;
import com.supcon.orchid.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class CustomSESECDIconLibraryServiceImpl extends BaseServiceImpl implements CustomSESECDIconLibraryService {

//    @Autowired
//    private ICustomSESECDIconLibraryClient customSESECDIconLibraryClient;


//    @Autowired
//    private ICustomSESGISConfigClient customSESGISConfigClient;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<EmergencyIconLibraryInfoDTO> getEmergencyIconLibraryInfo() {
        List<EmergencyIconLibraryInfoDTO> emergencyIconLibraryInfo = jdbcTemplate.query(ConstDict.SQL_ICON_LIBRARY, CustomSESECDIconLibraryServiceImpl::mapRow);
//        List<EmergencyIconLibraryInfoDTO> emergencyIconLibraryInfo = customSESGISConfigClient.getEmergencyIconLibraryInfo();
        log.error("应急指挥图标库返回：");
        log.error(JsonHelper.writeValue(emergencyIconLibraryInfo));
        return emergencyIconLibraryInfo;
    }

    private static EmergencyIconLibraryInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EmergencyIconLibraryInfoDTO(rs.getLong("id"),
                rs.getLong("cid"),
                rs.getString("name"),
                rs.getString("url"),
                rs.getLong("typeId"),
                rs.getString("typeName"));
    }

}
