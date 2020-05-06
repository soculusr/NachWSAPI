package com.api.nach.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.nach.services.AccountRespInService;

@RestController
public class AccountRespInResource {
	
	@Autowired
	AccountRespInService acctRespInService;
	
	@PostMapping("/GetPanDtlsResp")
	public ResponseEntity getPanDtlsResp(@RequestBody String response) {
		
		
		return acctRespInService.getPanDtlsResp(response);
		
		
	}
	
	@PostMapping("/GetAccHolderResp")
	public ResponseEntity getAcctHolderResp(@RequestBody String response) {
		
		
		return acctRespInService.getAcctHolderResp(response);
		
		
	}
	
	@PostMapping("/GetAccStatusResp")
	public ResponseEntity getAcctStatusResp(@RequestBody String response) {
		
		
		return acctRespInService.getAcctStatusResp(response);
		
		
	}

}
