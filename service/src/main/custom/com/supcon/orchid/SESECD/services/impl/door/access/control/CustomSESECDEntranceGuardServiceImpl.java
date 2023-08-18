package com.supcon.orchid.SESECD.services.impl.door.access.control;


import com.supcon.orchid.SESECD.daos.SESECDEntranceInfoDao;
import com.supcon.orchid.SESECD.entities.SESECDEntranceInfo;
import com.supcon.orchid.SESECD.model.vo.doorAccessControl.BaseShellVo;
import com.supcon.orchid.SESECD.model.vo.doorAccessControl.EntranceGuardVO;
import com.supcon.orchid.SESECD.model.vo.doorAccessControl.HeadVo;
import com.supcon.orchid.SESECD.services.door.access.control.CustomSESECDEntranceGuardService;
import com.supcon.orchid.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 门禁展示相关接口
 */
@Service
@Transactional
public class CustomSESECDEntranceGuardServiceImpl extends BaseServiceImpl implements CustomSESECDEntranceGuardService {

    private final String PREFIX = SESECDEntranceInfo.MODEL_CODE + "_";

    private final String FIELD_TYPE = "string";

    @Autowired
    private SESECDEntranceInfoDao entranceGuardDao;

    /**
     * 获取门禁信息
      */
    @Override
    public BaseShellVo<EntranceGuardVO> getEntranceGuardInfo() {
        StringBuffer entranceGuardSQL = new StringBuffer();
        entranceGuardSQL.append(" FROM ").append(SESECDEntranceInfo.JPA_NAME).append(" WHERE VALID = 1");
        List<SESECDEntranceInfo> vxECDEntranceGuardList = entranceGuardDao.findByHql(entranceGuardSQL.toString());
        if (CollectionUtils.isEmpty(vxECDEntranceGuardList)){
            log.error("门禁信息集合为空...");
            return null;
        }
        List<EntranceGuardVO> entranceGuardVOS = new ArrayList<>(8);
        vxECDEntranceGuardList.forEach(entranceGuard->{
            EntranceGuardVO entranceGuardVO = new EntranceGuardVO();
            entranceGuardVO.setId(entranceGuard.getId().toString());//门禁id
            entranceGuardVO.setEntranceGuardStatus(entranceGuard.getOpenedState() ? "on" : "off");//开关状态
            entranceGuardVO.setName(entranceGuard.getName());//门禁名称
            entranceGuardVO.setSpatialId(PREFIX+entranceGuard.getId().toString());//空间数据库id
            entranceGuardVO.setRemark(entranceGuard.getRemark());//备注
            entranceGuardVOS.add(entranceGuardVO);
        });

        BaseShellVo<EntranceGuardVO> baseShellVo = new BaseShellVo(getHeadData(),entranceGuardVOS);
        return baseShellVo;
    }

    /**
     *返回并告知前端个字段含义和类型
     * @return
     */
    private List<HeadVo> getHeadData(){
        List<HeadVo> list = new ArrayList<>(8);
        list.add(new HeadVo("id", FIELD_TYPE,"ID"));
        list.add(new HeadVo("spatialId",FIELD_TYPE,"spatialId"));
        list.add(new HeadVo("entranceGuardStatus",FIELD_TYPE,"门禁状态"));
        list.add(new HeadVo("name",FIELD_TYPE, "名称"));
        list.add(new HeadVo("remark",FIELD_TYPE, "备注"));
        return list;
    }
}
