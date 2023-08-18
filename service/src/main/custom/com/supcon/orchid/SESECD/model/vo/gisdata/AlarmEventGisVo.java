package com.supcon.orchid.SESECD.model.vo.gisdata;

import com.supcon.orchid.SESECD.model.vo.emergencyplan.EmergencyPlanVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludunyue on 2020/4/16.
 */
public class AlarmEventGisVo extends AlarmEventVo {

    public AlarmEventGisVo(){
        emergencyPlanVoList = new ArrayList<>();
    }

    List<EmergencyPlanVo> emergencyPlanVoList;

    public List<EmergencyPlanVo> getEmergencyPlanVoList() {
        return emergencyPlanVoList;
    }

    public void setEmergencyPlanVoList(List<EmergencyPlanVo> emergencyPlanVoList) {
        this.emergencyPlanVoList = emergencyPlanVoList;
    }
}
