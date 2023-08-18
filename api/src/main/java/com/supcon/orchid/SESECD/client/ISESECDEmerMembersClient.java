package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDEmerMembersDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_addrBook_EmerMembers,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDEmerMembersClient")
@FeignClient(name = "SESECD", contextId = "SESECDEmerMembersClient")
public interface ISESECDEmerMembersClient {

	String API_PREFIX = "/v1/SESECD/addrBook/emerMembers";

	@GetMapping(API_PREFIX + "/get")
	SESECDEmerMembersDTO getSESECDEmerMembers(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDEmerMembersDTO> getByPage(@RequestBody Page<SESECDEmerMembersDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_addrBook_EmerMembers,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}