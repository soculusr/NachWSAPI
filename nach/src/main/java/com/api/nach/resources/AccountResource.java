package com.api.nach.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.nach.exceptions.AcctNotFoundException;
import com.api.nach.models.Request1;
import com.api.nach.models.Request2;
import com.api.nach.models.Request3;
import com.api.nach.models.Response1;
import com.api.nach.models.Response2;
import com.api.nach.models.Response3;
import com.api.nach.repos.RequestRepository1;
import com.api.nach.repos.RequestRepository2;
import com.api.nach.repos.RequestRepository3;
import com.api.nach.repos.ResponseRepository1;
import com.api.nach.repos.ResponseRepository2;
import com.api.nach.repos.ResponseRepository3;
import com.api.nach.services.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/nachapi")
public class AccountResource {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final String TOPIC = "kafka_demo";
	
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
	
	@Operation(summary = "Pan Details Request", description = "It will give acct no and PAN details", tags = { "GetPanDtls" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@PostMapping("/GetPanDtls")
	@ResponseStatus(HttpStatus.OK)
	public String getPanDtlsRqst(@RequestBody String acctinfo) {
		
		
		//kafkaTemplate.send(TOPIC, accountService.getPanDtls(acctinfo));
		//String respData = accountService.getPanDtlsAck();
		String respData = accountService.getPanDtls(acctinfo);
		
		
		if(respData.equals("not found"))
			throw new AcctNotFoundException("Account Not Found");
		
		return respData;
	}
	
	@Operation(summary = "Account Holder Request", description = "It will give acct holder name", tags = { "GetAccHolder" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@PostMapping("/GetAccHolder")
	@ResponseStatus(HttpStatus.OK)
	public String getAcctHolderRqst(@RequestBody String acctinfo) {
		
		//kafkaTemplate.send(TOPIC, accountService.getAcctHolderName(acctinfo));
		//String respData = accountService.getAcctHolderAck();
		String respData = accountService.getAcctHolderName(acctinfo);
		if(respData.equals("not found"))
			throw new AcctNotFoundException("Account Not Found");
		
		return respData;
	}
	
	@Operation(summary = "Account Status Request", description = "It will give status of the acct", tags = { "GetAccStatus" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@PostMapping("/GetAccStatus")
	@ResponseStatus(HttpStatus.OK)
	public String getAcctStatusRqst(@RequestBody String acctinfo) {
		//kafkaTemplate.send(TOPIC, accountService.getAcctStatus(acctinfo));
		//String respData = accountService.getAcctHolderAck();
		String respData = accountService.getAcctStatus(acctinfo);
		if(respData.equals("not found"))
			throw new AcctNotFoundException("Account Not Found");
		
		return respData;
	}
	
	
	@Operation(summary = "List All Request1", description = "It will give all request1 data from db", tags = { "GetAllRequest1" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@GetMapping("/GetAllRequest1")
	public List<Request1> getAllRequest1(){
	
		return requestRepository1.findByServiceName();
	}
	
	@Operation(summary = "List All Request2", description = "It will give all request2 data from db", tags = { "GetAllRequest2" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@GetMapping("/GetAllRequest2")
	public List<Request2> getAllRequest2(){
	
		return requestRepository2.findByServiceName();
	}
	
	@Operation(summary = "List All Request3", description = "It will give all request3 data from db", tags = { "GetAllRequest3" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@GetMapping("/GetAllRequest3")
	public List<Request3> getAllRequest3(){
	
		return requestRepository3.findByServiceName();
	}
	
	@Operation(summary = "List All Response1", description = "It will give all resposne1 data from db", tags = { "GetAllResponse1" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@GetMapping("/GetAllResponse1")
	public List<Response1> getAllResponse1(){
	
		return responseRepository1.findByServiceName();
	}
	
	@Operation(summary = "List All Response2", description = "It will give all resposne2 data from db", tags = { "GetAllResponse2" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@GetMapping("/GetAllResponse2")
	public List<Response2> getAllResponse2(){
	
		return responseRepository2.findByServiceName();
	}
	
	@Operation(summary = "List All Response3", description = "It will give all resposne3 data from db", tags = { "GetAllResponse3" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@GetMapping("/GetAllResponse3")
	public List<Response3> getAllResponse3(){
	
		return responseRepository3.findByServiceName();
	}
	
	
	
	

}
