package com.api.nach.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.api.nach.models.AadhaarReqDtls;

import com.api.nach.models.PrevIinDtls;
import com.api.nach.repos.PrevIinRepository;
import com.api.nach.services.AadhaarReqService;
import com.api.nach.services.AccountService;

@Controller
public class AadhaarController {
	
	@Autowired
	private PrevIinRepository prevIinRepo;

	
	@Autowired
	AadhaarReqService aadhaarService;
	
	
	
	

	@GetMapping(value="/home")
	public String aadhaarDtls() {
		
		return "aadhaarSeeding";
	}
	  
	  @PostMapping("/aadhaarData")
	  public String getData(@RequestBody String data) throws Exception {
		  
		  String aadhhaarData = data.replace("=", "").replace("%7E", "~").replace("%2C", ",").replace("%2F", "/").replace("+", "");
		  
		  
		  String [] list = aadhhaarData.split(",");
		  
		  List<String> fixedLenghtList = Arrays.asList(list);
		  
		  ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);
		  System.out.println(listOfString);
		  
		  aadhaarService.aadharSeedingRequest(aadhhaarData);
		  System.out.println("Received data is "+aadhhaarData);
		  
		  return "data received";
		  
	  }
	  
	  
	  @ModelAttribute("previinlist")
	   public List<String> getWebFrameworkList() {
	      List<String> prevIinList = new ArrayList<String>();
	      prevIinList = prevIinRepo.findAllIin();
	      return prevIinList;
	   }
	
	

}
