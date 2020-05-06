package com.api.nach.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountRespInService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountRespInService.class);
	public ResponseEntity getPanDtlsResp(String response) {
		
		
		//String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		String responseData = response.substring(response.indexOf("'Message':'")+11, response.indexOf("'}"));
		
		byte[] decodedBytes = Base64.getDecoder().decode(responseData);
		//String requestData2=DatatypeConverter.printBase64Binary(Base64.getDecoder().decode(decodedBytes));
		String responseDataFinal = new String(decodedBytes);
		logger.info("Received PanDtls Response is \n"+responseDataFinal);
		return new ResponseEntity(getPanDtlsAck("demo"), HttpStatus.ACCEPTED);
		
	}
	
	public ResponseEntity getAcctHolderResp(String response) {
		
		
		//String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		String responseData = response.substring(response.indexOf("'Message':'")+11, response.indexOf("'}"));
		
		byte[] decodedBytes = Base64.getDecoder().decode(responseData);
		//String requestData2=DatatypeConverter.printBase64Binary(Base64.getDecoder().decode(decodedBytes));
		String responseDataFinal = new String(decodedBytes);
		logger.info("Received AcctHolder Response is \n"+responseDataFinal);
		return new ResponseEntity(getAcctHolderAck("demo"), HttpStatus.ACCEPTED);
		
	}

	public ResponseEntity getAcctStatusResp(String response) {
	
	
	//String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
	String responseData = response.substring(response.indexOf("'Message':'")+11, response.indexOf("'}"));
	
	byte[] decodedBytes = Base64.getDecoder().decode(responseData);
	//String requestData2=DatatypeConverter.printBase64Binary(Base64.getDecoder().decode(decodedBytes));
	String responseDataFinal = new String(decodedBytes);
	logger.info("Received AcctStatus Response is \n"+responseDataFinal);
	return new ResponseEntity(getAcctStatusAck("demo"), HttpStatus.ACCEPTED);
	
	}
	
	public String getPanDtlsAck(String reqId) {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String ackTimestamp = dateFormat.format(date);
		String ackData = "{'Source':'Demo','Service':'GetPanDtls','Type':'Acknowledgement','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
				"<NpciRefId value=\"\"/>\r\n"+
				"<Request id=\""+reqId+"\"/>\r\n"+
				"<Resp ts=\""+ackTimestamp+"\" result=\"ACCEPTED\" errCode=\"\" rejectedBy=\"\" />\r\n"+
				"</nachapi:GatewayAck>'}";
		
		return ackData;
			
		
	}
	
	public String getAcctHolderAck(String reqId) {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String ackTimestamp = dateFormat.format(date);
		String ackData = "{'Source':'Demo','Service':'GetAccHolder','Type':'Acknowledgement','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
				"<NpciRefId value=\"\"/>\r\n"+
				"<Request id=\""+reqId+"\"/>\r\n"+
				"<Resp ts=\""+ackTimestamp+"\" result=\"ACCEPTED\" errCode=\"\" rejectedBy=\"\" />\r\n"+
				"</nachapi:GatewayAck>'}";
		
		return ackData;
			
		
	}
	
	public String getAcctStatusAck(String reqId) {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String ackTimestamp = dateFormat.format(date);
		String ackData = "{'Source':'Demo','Service':'GetAccStatus','Type':'Acknowledgement','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
				"<NpciRefId value=\"\"/>\r\n"+
				"<Request id=\""+reqId+"\"/>\r\n"+
				"<Resp ts=\""+ackTimestamp+"\" result=\"ACCEPTED\" errCode=\"\" rejectedBy=\"\" />\r\n"+
				"</nachapi:GatewayAck>'}";
		
		return ackData;
	
	
	}
}
