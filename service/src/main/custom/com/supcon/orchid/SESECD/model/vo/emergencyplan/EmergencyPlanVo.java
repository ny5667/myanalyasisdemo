package com.supcon.orchid.SESECD.model.vo.emergencyplan;

/**
 * Created by ludunyue on 2020/4/16.
 */
public class EmergencyPlanVo {
    private String id;
    private String planName;
    private String planType;
    private String planVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanVersion() {
        return planVersion;
    }

    public void setPlanVersion(String planVersion) {
        this.planVersion = planVersion;
    }
}
