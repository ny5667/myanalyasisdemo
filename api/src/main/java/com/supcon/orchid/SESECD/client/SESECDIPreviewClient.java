package com.supcon.orchid.SESECD.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.PreviewSESECDClient")
@FeignClient(name = "baseService", contextId = "SESECDIPreviewClient")
public interface SESECDIPreviewClient {

	String API_PREFIX = "/baseService/workbench";

	@GetMapping(API_PREFIX + "/moduleViewUrl")
	public String fileViewUrl(@RequestParam(value = "id") String id, @RequestParam(value = "type") String type) throws Exception;

	@GetMapping(API_PREFIX + "/modulePicViewUrl")
	public String picViewUrl(@RequestParam(value = "id") String id) throws Exception;

}

