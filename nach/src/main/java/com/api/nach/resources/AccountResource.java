package com.api.nach.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.nach.models.Request1;
import com.api.nach.repos.AccountRepository1;
import com.api.nach.services.AccountService;


@RestController
@RequestMapping("/nachapi")
public class AccountResource {
	
	@Autowired
	private AccountRepository1 accountRepository1;
	
	@Autowired
	AccountService accountService;
	
	@PostMapping("/GetPanDtls")
	public String getPanDtlsRqst(@RequestBody String acctinfo) {
		
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
	
		return accountRepository1.findByServiceName();
	}
	
	@GetMapping("/add")
	
	public String addData(String request) {
		
		return accountService.getPanDtls(request);
	}

}
