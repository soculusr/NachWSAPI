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
public class AcctStatusReqOutController {
	
	@Autowired
	private PrevIinListRepository prevIinRepo;
	
	@GetMapping(value="/home3")
	public String acctStatusDtls() {
		
		return "acctStatus";
	}
	
	@PostMapping("/acctStatusDtls")
	  public void getData(@RequestBody String data) {
		String acctStatusData = data.replace("=", "").replace("%2C", ",").replace("%2F", "/");
		System.out.println("Status data "+acctStatusData);
	}
	
	 @ModelAttribute("previinlist")
	   public List<String> getWebFrameworkList() {
	      List<String> prevIinList = new ArrayList<String>();
	      prevIinList = prevIinRepo.findAllIin();
	      return prevIinList;
	   }

}
