package com.supcon.orchid.SESECD.services.impl.icon;

import com.supcon.orchid.SESECD.model.dto.out.icon.library.EmergencyIconLibraryInfoDTO;
import com.supcon.orchid.SESECD.model.vo.forfront.icon.IconLibraryVO;
import com.supcon.orchid.SESECD.services.icon.CustomSESECDIconService;
import com.supcon.orchid.SESECD.services.out.icon.library.CustomSESECDIconLibraryService;
import com.supcon.orchid.services.BaseServiceImpl;
import com.supcon.orchid.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomSESECDIconServiceImpl extends BaseServiceImpl implements CustomSESECDIconService {

    @Autowired
    private CustomSESECDIconLibraryService customSESECDIconLibraryService;

    @Override
    public Result<Map<String, List<IconLibraryVO>>> getEmergencyIconLibraryInfo() {
        List<EmergencyIconLibraryInfoDTO> emergencyIconLibraryInfo = customSESECDIconLibraryService.getEmergencyIconLibraryInfo();
        List<IconLibraryVO> list = new ArrayList<>();
        for (EmergencyIconLibraryInfoDTO item :
                emergencyIconLibraryInfo) {
            IconLibraryVO vo = new IconLibraryVO();
            vo.setId(item.getId());//id
            vo.setName(item.getName());//图标名称
            vo.setUrl(item.getUrl());//图片地址
            vo.setTypeName(item.getTypeName());//类型
            list.add(vo);
        }
        Map<String, List<IconLibraryVO>> collect = list.stream().filter(c -> c.getTypeName() != null && !c.getTypeName().isEmpty())
                .collect(Collectors.groupingBy(IconLibraryVO::getTypeName));
        return Result.data(collect);
    }


}
