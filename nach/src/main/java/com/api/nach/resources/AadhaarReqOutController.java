package com.api.nach.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
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

import com.api.nach.models.AadhaarReqDtlsOut;

import com.api.nach.models.PrevIinListDtls;
import com.api.nach.repos.PrevIinListRepository;
import com.api.nach.services.AadhaarReqOutService;
import com.api.nach.services.AccountRespOutService;

@Controller
public class AadhaarReqOutController {
	
	@Autowired
	private PrevIinListRepository prevIinRepo;

	
	@Autowired
	AadhaarReqOutService aadhaarService;
	
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AadhaarReqOutController.class);
	

	@GetMapping(value="/aadhaarService")
	public String aadhaarDtls() {
		
		return "aadhaarSeeding";
	}
	  
	  @PostMapping("/aadhaarData")
	  public void getData(@RequestBody String data){
		  
		  String aadhhaarData = data.replace("=", "").replace("%7E", "~").replace("%2C", ",").replace("%2F", "/").replace("+", "");
		  
		  aadhaarService.aadharSeedingRequest(aadhhaarData);
		  logger.info("Received data for aadhaar is "+aadhhaarData);
		  
		  
	  }
	  
	  
	  @ModelAttribute("previinlist")
	   public List<String> getWebFrameworkList() {
	      List<String> prevIinList = new ArrayList<String>();
	      prevIinList = prevIinRepo.findAllIin();
	      return prevIinList;
	   }
	
	

}
