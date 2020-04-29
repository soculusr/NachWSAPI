package com.api.nach.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.api.nach.repos.PrevIinListRepository;

@Controller
public class AccttHolderReqOutController {
	
	@Autowired
	private PrevIinListRepository prevIinRepo;
	
	@GetMapping(value="/home2")
	public String aadhaarDtls() {
		
		return "acctHolder";
	}
	
	@PostMapping("/acctHolderDtls")
	  public void getData(@RequestBody String data) {
		String acctHolderData = data.replace("=", "").replace("%2C", ",").replace("%2F", "/");
		System.out.println("Holder data "+acctHolderData);
	}
	
	 @ModelAttribute("previinlist")
	   public List<String> getWebFrameworkList() {
	      List<String> prevIinList = new ArrayList<String>();
	      prevIinList = prevIinRepo.findAllIin();
	      return prevIinList;
	   }

}
