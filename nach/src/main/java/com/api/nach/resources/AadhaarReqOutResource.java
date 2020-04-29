package com.api.nach.resources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.nach.exceptions.AcctNotFoundException;
import com.api.nach.services.AadhaarReqOutService;

@RestController
public class AadhaarReqOutResource {
	
	AadhaarReqOutService aadhaarService = new AadhaarReqOutService();
	
	@PostMapping("/AadhaarSeedingResp")
	@ResponseStatus(HttpStatus.CREATED)
	public String aadhaarSeeding(@RequestBody String request) {
		
		
		String respData = aadhaarService.aadharSeedingResponse(request);
		
		
		return respData;
	}
	
	@PostMapping("/AadhaarSeedingReq")
	public String aadhaarSeedingTest(@RequestBody String request) throws Exception {
		
		
		//kafkaTemplate.send(TOPIC, accountService.getPanDtls(acctinfo));
		//String respData = accountService.getPanDtlsAck();
		String respData = aadhaarService.aadharSeedingRequest(request);
		
		
		return respData;
	}
	
	

}
