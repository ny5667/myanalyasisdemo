package com.supcon.orchid.SESECD.services.outward.jobctl;

import com.supcon.orchid.SESECD.model.dto.outward.jobctl.JobControlDTO;
import com.supcon.orchid.SesCommonFunc.vo.BaseInfoVo;

public interface CustomSESECDOutwardService {
    /**
     * 作业受控
     *
     * @param jobControlDTO
     * @return
     */
    BaseInfoVo getJobControl(JobControlDTO jobControlDTO);
}
