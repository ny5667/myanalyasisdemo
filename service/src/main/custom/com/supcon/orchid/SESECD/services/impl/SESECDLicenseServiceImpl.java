/**package com.supcon.orchid.SESECD.services.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import com.supcon.greendill.common.entity.License;
import com.supcon.orchid.SESECD.services.SESECDLicenseService;
import com.supcon.greendill.common.license.service.Register;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 中控生产安全应急指挥演练软件业务模块授权注册
 *
 
@Service("SESECDLicenseService")
public class SESECDLicenseServiceImpl implements SESECDLicenseService,InitializingBean, DisposableBean{
	
private static final Logger logger = LoggerFactory.getLogger("com.supcon.bap.license");

	@Autowired
	Register register;
	@Override
	public void destroy() throws Exception {
	
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// 注册实体配置授权
		License ecdLicense = new License("SESECD", "supSES-ECD",
				"EdrvXM2VSorwfKb4iDrzMEQWfxcyFaBq3HOkrltncTMw+O9kdH2PD2rOxmKnKmFhxbFR/g==",
				"EdrvXM2VSorwfKb4iDrzMEQWfxcyFaBq3HOkrltncTMw+O9kdH2PD2rOxmKnKmFhxbFR/g==",
				"{'zh_CN':'中控生产安全应急指挥演练软件','en_US':'EmergencyCommondDrill','zh_TW':'中控生產安全應急指揮演練軟件'}");
		register.register(ecdLicense);
	}
}*/
