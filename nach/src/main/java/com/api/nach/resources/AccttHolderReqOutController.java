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
public class AccttHolderReqOutController {
	
	/*@Autowired
	private PrevIinListRepository prevIinRepo;*/
	
	@Autowired
	private DestBankListRepository destBankRepo;
	
	@Autowired
	AccountReqOutService acctReqOutService;
	
	@GetMapping(value="/acctHolderService")
	public String aadhaarDtls() {
		
		return "acctHolder";
	}
	
	@PostMapping("/acctHolderDtls")
	  public void getData(@RequestBody String data) {
		String acctHolderData = data.replace("=", "").replace("+", " ").replace("%2C", ",").replace("%2F", "/");
		acctReqOutService.acctHolderReqOut(acctHolderData);
		System.out.println("Holder data "+acctHolderData);
	}
	
	@ModelAttribute("destBankList")
	   public List<String> getWebFrameworkList() {
	      List<String> destBankList = new ArrayList<String>();
	      destBankList = destBankRepo.findAllIin();
	      return destBankList;
	   }

}
