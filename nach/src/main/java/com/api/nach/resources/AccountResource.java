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
import com.api.nach.models.RequestPanDtls;
import com.api.nach.models.RequestAcctHoldr;
import com.api.nach.models.RequestAcctStatus;
import com.api.nach.models.ResponsePanDtls;
import com.api.nach.models.ResponseAcctHoldr;
import com.api.nach.models.ResponseAcctStatus;
import com.api.nach.repos.RequestRepositoryPanDtls;
import com.api.nach.repos.RequestRepositoryAcctHoldr;
import com.api.nach.repos.RequestRepositoryAcctStatus;
import com.api.nach.repos.ResponseRepositoryPanDtls;
import com.api.nach.repos.ResponseRepositoryAcctHoldr;
import com.api.nach.repos.ResponseRepositoryAcctStatus;
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
	private RequestRepositoryPanDtls requestRepositoryPanDtls;
	
	@Autowired
	private RequestRepositoryAcctHoldr requestRepositoryAcctHoldr;
	
	@Autowired
	private RequestRepositoryAcctStatus requestRepositoryAcctStatus;
	
	@Autowired
	private ResponseRepositoryPanDtls responseRepositoryPanDtls;
	
	@Autowired
	private ResponseRepositoryAcctHoldr responseRepositoryAcctHoldr;
	
	@Autowired
	private ResponseRepositoryAcctStatus responseRepositoryAcctStatus;
	
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
	
	
	@Operation(summary = "List All Pan Details Request", description = "It will give all request1 data from db", tags = { "GetAllRequest1" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@GetMapping("/GetAllPanDtlsRequest")
	public List<RequestPanDtls> getAllPanDtlsRequest(){
	
		return requestRepositoryPanDtls.findByServiceName();
	}
	
	@Operation(summary = "List All Acct Holder Request", description = "It will give all request2 data from db", tags = { "GetAllRequest2" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@GetMapping("/GetAllAcctHoldrRequest")
	public List<RequestAcctHoldr> getAllAcctHoldrRequest(){
	
		return requestRepositoryAcctHoldr.findByServiceName();
	}
	
	@Operation(summary = "List All Request3", description = "It will give all request3 data from db", tags = { "GetAllRequest3" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@GetMapping("/GetAllAcctStatusRequest")
	public List<RequestAcctStatus> getAllAcctStatusRequest(){
	
		return requestRepositoryAcctStatus.findByServiceName();
	}
	
	@Operation(summary = "List All Pan Details Response", description = "It will give all resposne1 data from db", tags = { "GetAllResponse1" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@GetMapping("/GetAllPanDtlsResponse")
	public List<ResponsePanDtls> getAllPanDtlsResponse(){
	
		return responseRepositoryPanDtls.findByServiceName();
	}
	
	@Operation(summary = "List All Acct Holder Response", description = "It will give all resposne2 data from db", tags = { "GetAllResponse2" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@GetMapping("/GetAllAcctHoldrResponse")
	public List<ResponseAcctHoldr> getAllAcctHoldrResponse(){
	
		return responseRepositoryAcctHoldr.findByServiceName();
	}
	
	@Operation(summary = "List All Acct Status Response", description = "It will give all resposne3 data from db", tags = { "GetAllResponse3" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@GetMapping("/GetAllResponse3")
	public List<ResponseAcctStatus> getAllAcctStatusResponse(){
	
		return responseRepositoryAcctStatus.findByServiceName();
	}
	
	
	
	

}
