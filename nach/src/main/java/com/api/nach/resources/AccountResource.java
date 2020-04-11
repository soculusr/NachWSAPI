package com.api.nach.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.nach.exceptions.AcctNotFoundException;
import com.api.nach.models.Request1;
import com.api.nach.repos.RequestRepository1;
import com.api.nach.repos.RequestRepository2;
import com.api.nach.repos.RequestRepository3;
import com.api.nach.repos.ResponseRepository1;
import com.api.nach.repos.ResponseRepository2;
import com.api.nach.repos.ResponseRepository3;
import com.api.nach.services.AccountService;


@RestController
@RequestMapping("/nachapi")
public class AccountResource {
	
	@Autowired
	private RequestRepository1 requestRepository1;
	
	@Autowired
	private RequestRepository2 requestRepository2;
	
	@Autowired
	private RequestRepository3 requestRepository3;
	
	@Autowired
	private ResponseRepository1 responseRepository1;
	
	@Autowired
	private ResponseRepository2 responseRepository2;
	
	@Autowired
	private ResponseRepository3 responseRepository3;
	
	@Autowired
	AccountService accountService;
	
	@PostMapping("/GetPanDtls")
	public String getPanDtlsRqst(@RequestBody String acctinfo) {
		
		String respData = accountService.getPanDtls(acctinfo);
		
		if(respData.equals("not found"))
			throw new AcctNotFoundException("Account Not Found");
		
		return accountService.getPanDtls(acctinfo);
	}
	
	@PostMapping("/GetAccHolder")
	public String getAcctHolderRqst(@RequestBody String acctinfo) {
		
		return accountService.getAcctHolderName(acctinfo);
	}
	
	@PostMapping("/GetAccStatus")
	public String getAcctStatusRqst(@RequestBody String acctinfo) {
		
		return accountService.getAcctStatus(acctinfo);
	}
	
	
	@GetMapping("/GetAllRequest1")
	public List<Request1> getAllRequest1(){
	
		return requestRepository1.findByServiceName();
	}
	
	@GetMapping("/GetAllRequest2")
	public List<Request1> getAllRequest2(){
	
		return requestRepository2.findByServiceName();
	}
	
	@GetMapping("/GetAllRequest3")
	public List<Request1> getAllRequest3(){
	
		return requestRepository3.findByServiceName();
	}
	
	@GetMapping("/GetAllResponse1")
	public List<Request1> getAllResponse1(){
	
		return responseRepository1.findByServiceName();
	}
	
	@GetMapping("/GetAllResponse2")
	public List<Request1> getAllResponse2(){
	
		return responseRepository2.findByServiceName();
	}
	
	@GetMapping("/GetAllResponse3")
	public List<Request1> getAllResponse3(){
	
		return responseRepository3.findByServiceName();
	}
	
	
	
	

}
