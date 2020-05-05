package com.api.nach.resources;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.nach.exceptions.AcctNotFoundException;
import com.api.nach.models.PanDtlsReqIn;
import com.api.nach.models.AcctHolderReqIn;
import com.api.nach.models.AcctStatusReqIn;
import com.api.nach.models.PanDtlsRespOut;
import com.api.nach.models.AcctHolderRespOut;
import com.api.nach.models.AcctStatusRespOut;
import com.api.nach.repos.PanDtlsReqInRepository;
import com.api.nach.repos.AcctHolderReqInRepository;
import com.api.nach.repos.AcctStatusReqInRepository;
import com.api.nach.repos.PanDtlsRespOutRepository;
import com.api.nach.repos.AcctHolderRespOutRepository;
import com.api.nach.repos.AcctStatusRespOutRepository;
import com.api.nach.services.AccountRespOutService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/nachapi")
public class AccountRespOutResource {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final String TOPIC = "kafka_demo";
	
	@Autowired
	private PanDtlsReqInRepository requestRepositoryPanDtls;
	
	@Autowired
	private AcctHolderReqInRepository requestRepositoryAcctHoldr;
	
	@Autowired
	private AcctStatusReqInRepository requestRepositoryAcctStatus;
	
	@Autowired
	private PanDtlsRespOutRepository responseRepositoryPanDtls;
	
	@Autowired
	private AcctHolderRespOutRepository responseRepositoryAcctHoldr;
	
	@Autowired
	private AcctStatusRespOutRepository responseRepositoryAcctStatus;
	
	@Autowired
	AccountRespOutService accountService;
	
	@Operation(summary = "Pan Details Request", description = "It will give acct no and PAN details", tags = { "GetPanDtls" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@PostMapping("/GetPanDtls")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity getPanDtlsRqst(@RequestBody String acctinfo) throws Exception {
		
		
		//kafkaTemplate.send(TOPIC, accountService.getPanDtls(acctinfo));
		//String respData = accountService.getPanDtlsAck();
		ResponseEntity respData = accountService.getPanDtls(acctinfo);
		
		return respData;
	}
	
	@Operation(summary = "Account Holder Request", description = "It will give acct holder name", tags = { "GetAccHolder" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@PostMapping("/GetAccHolder")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity getAcctHolderRqst(@RequestBody String acctinfo) throws Exception {
		
		//kafkaTemplate.send(TOPIC, accountService.getAcctHolderName(acctinfo));
		//String respData = accountService.getAcctHolderAck();
		ResponseEntity respData = accountService.getAcctHolderName(acctinfo);
		
		return respData;
	}
	
	@Operation(summary = "Account Status Request", description = "It will give status of the acct", tags = { "GetAccStatus" })
	@ApiResponse(responseCode = "200", description = "successful operation")
	@PostMapping("/GetAccStatus")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity getAcctStatusRqst(@RequestBody String acctinfo) throws Exception {
		//kafkaTemplate.send(TOPIC, accountService.getAcctStatus(acctinfo));
		//String respData = accountService.getAcctHolderAck();
		ResponseEntity respData = accountService.getAcctStatus(acctinfo);
		
		return respData;
	}
		

}
