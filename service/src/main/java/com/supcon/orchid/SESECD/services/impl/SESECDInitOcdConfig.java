package com.supcon.orchid.SESECD.services.impl;

import com.supcon.supfusion.framework.cloud.common.context.RpcContext;
import com.supcon.supfusion.systemconfig.api.SystemApiService;
import com.supcon.supfusion.systemconfig.api.dto.XmlContentDTO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;

import java.io.File;
import java.nio.file.*;
import java.util.*;

/**
* 初始化ocd文件
**/
@Service
public class SESECDInitOcdConfig implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String moduleCode = "SESECD";

    private static String OCD_PATH = "service" + File.separator + "src" + File.separator + "main" + File.separator +
            "resources" + File.separator + "META-INF" + File.separator + "ocd" + File.separator + "ocd.xml";

    @Value("${entityconf.generatePath:''}")
    private String generatePath;

    @Autowired
    private SystemApiService systemApiService;

    @Override
    public void afterPropertiesSet() throws Exception {
        String ocdpath = generatePath + File.separator + moduleCode + File.separator + OCD_PATH;
        File ocdfile = new File(ocdpath);
        logger.info("ocdpath:{}", ocdpath);
        logger.info("generatePath:{}", generatePath);
        logger.info("System.getProperty:{}", System.getProperty("user.dir"));
        String tenantId = System.getenv("SUPOS_SUPOS_APP_TENANT_ID");
        logger.info("从系统变量中获取tenantId:{}", tenantId);
        RpcContext.getContext().setTenantId(tenantId);
        if (ocdfile.exists()) {
            try {
                byte[] b = Files.readAllBytes(Paths.get(ocdfile.getPath()));
                String ocd = Base64.getEncoder().encodeToString(b);
                List<String> list = new ArrayList<>();
                list.add(ocd);
                XmlContentDTO xmlContentDTO = new XmlContentDTO();
                xmlContentDTO.setXmlList(list);
                systemApiService.saveOcdContent(xmlContentDTO);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new Exception(e);
            }
        }
    }
}
