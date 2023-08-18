package com.supcon.orchid.SESECD.services.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.supcon.supfusion.license.api.LicenseApiService;
import com.supcon.supfusion.license.api.dto.LicenseInfoDTO;

/**
* nacos注册模块授权信息
**/
@Service
public class SESECDRegisterNacosLicense implements InitializingBean {

    private String artifact = "SESECD";
    /**
     * 软件狗key
     */
    private String licenseKey = "";
    /**
     * 应用模块名称（授权中文）
     */
    private String applicationName = "";
    /**
     * 应用模块型号（授权英文）
     */
    private String applicationType = "";

    @Autowired
    private LicenseApiService licenseApiService;

    @Override
    public void afterPropertiesSet() {
        //自定义代码区，对licenseKey，applicationName，applicationType进行赋值
     /* CUSTOM CODE START(serviceimpl,registerNacosLicense,SESECD_1.0.0,SESECD_1.0.0) */
// 自定义代码
      /*
   licenseKey = "EdrvXM2VSorwfKb4iDrzMEQWfxcyFaBq3HOkrltncTMw+O9kdH2PD2rOxmKnKmFhxbFR/g==";   
   
   applicationName = "中控生产安全应急指挥演练软件";   
   
   applicationType = "supSES-ECD";
      
        LicenseInfoDTO licenseInfoDTO4 = new LicenseInfoDTO("EdrvXM2VSorwfKb4iDrzMPdteaeVMxmA3HOkrltncTPE/bATT1kwUddvpd9O35/0NbbPoA==");
        licenseInfoDTO4.setModuleCode("VxSES-SIP");

        if (licenseApiService.getLicenseInfoByLicenseKey(licenseInfoDTO4) >= 0) {
            licenseKey = "EdrvXM2VSorwfKb4iDrzMPdteaeVMxmA3HOkrltncTPE/bATT1kwUddvpd9O35/0NbbPoA==";
            applicationType = "VxSES-SIP";//VxSES-SIP
        }
        */
/* CUSTOM CODE END */

        //向nacos注册模块信息
        //软件狗key不允许为空
        if (!ObjectUtils.isEmpty(licenseKey)) {
            LicenseInfoDTO licenseInfoDTO = new LicenseInfoDTO();
            licenseInfoDTO.setModuleCode(artifact);
            licenseInfoDTO.setLicenseKey(licenseKey);
            licenseInfoDTO.setApplicationName(applicationName);
            licenseInfoDTO.setApplicationType(applicationType);
            licenseApiService.registerLicenseInfo(licenseInfoDTO);
        }
    }
}