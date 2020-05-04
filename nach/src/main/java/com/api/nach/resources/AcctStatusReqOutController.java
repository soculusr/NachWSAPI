package com.api.nach.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.api.nach.repos.DestBankListRepository;
import com.api.nach.repos.PrevIinListRepository;
import com.api.nach.services.AccountReqOutService;

@Controller
public class AcctStatusReqOutController {
	
	/*@Autowired
	private PrevIinListRepository prevIinRepo;*/
	
	@Autowired
	private DestBankListRepository destBankRepo;
	
	@Autowired
	AccountReqOutService acctReqOutService;
	
	@GetMapping(value="/home3")
	public String acctStatusDtls() {
		
		return "acctStatus";
	}
	
	@PostMapping("/acctStatusDtls")
	  public void getData(@RequestBody String data) {
		String acctStatusData = data.replace("=", "").replace("+", " ").replace("%2C", ",").replace("%2F", "/");
		acctReqOutService.acctStatusReqOut(acctStatusData);
		System.out.println("Status data "+acctStatusData);
	}
	
	@ModelAttribute("destBankList")
	   public List<String> getWebFrameworkList() {
	      List<String> destBankList = new ArrayList<String>();
	      destBankList = destBankRepo.findAllIin();
	      return destBankList;
	   }

}
