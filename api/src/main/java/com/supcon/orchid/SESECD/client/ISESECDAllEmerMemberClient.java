package com.supcon.orchid.SESECD.client;

import com.supcon.orchid.SESECD.DTO.SESECDAllEmerMemberDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import com.supcon.orchid.workflow.engine.entities.WorkFlowVar;
import org.springframework.web.bind.annotation.*;
import org.hibernate.criterion.DetachedCriteria;
import com.supcon.orchid.services.Page;
import java.util.Map;
/* CUSTOM CODE START(interface,import,SESECD_1.0.0_addrBook_AllEmerMember,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */

@ConditionalOnMissingClass("com.supcon.orchid.SESECD.provider.SESECDAllEmerMemberClient")
@FeignClient(name = "SESECD", contextId = "SESECDAllEmerMemberClient")
public interface ISESECDAllEmerMemberClient {

	String API_PREFIX = "/v1/SESECD/addrBook/allEmerMember";

	@GetMapping(API_PREFIX + "/get")
	SESECDAllEmerMemberDTO getSESECDAllEmerMember(@RequestParam("id") long id);

	@PostMapping(API_PREFIX + "/getByPage")
	Page<SESECDAllEmerMemberDTO> getByPage(@RequestBody Page<SESECDAllEmerMemberDTO> page);

	/* CUSTOM CODE START(interface,functions,SESECD_1.0.0_addrBook_AllEmerMember,SESECD_1.0.0) */
// 自定义代码

/* CUSTOM CODE END */
}