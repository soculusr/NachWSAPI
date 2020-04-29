package com.api.nach.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.nach.models.AadhaarReqDtls;
import com.api.nach.repos.AadhaarRepository;




@Service
public class AadhaarReqService {
	
	
	@Autowired
	private AadhaarRepository aadhaarRepository;

	@Value("${npci.url}")
	private String npciUri;
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountService.class);
	
	private xmlSigning signData = new xmlSigning();
	public String aadharSeedingRequest(String request) throws Exception {
		
		
		String [] dataList = request.split(",");
		ArrayList<String> aadhaarDtlsFinal = new ArrayList<String>();
		String sourceValue = "IDBI";
		String aadhaarDetail = "";
		String aadhaarDetailFinal = "";
		String detailData  = "";
		String aadhaarReq = "";
		String serviceName = "AadhaarSeeding";
		String serviceType = "Request";
		int recRefNo;
		int uniqueReqId;
		String xmlDataUnsigned = "";
		String xmlDataSigned = "";
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String reqTimestamp = dateFormat.format(date);
		String [] listContent = null;
		List<String> fixedLenghtList = null;
		ArrayList<String> listOfString = null;
		ArrayList<String> listDetails = null;
		
		
		AadhaarReqDtls aadhaarReqDtls = new AadhaarReqDtls();
		recRefNo = aadhaarRepository.getRecRefNo();
		uniqueReqId = aadhaarRepository.getUniqueReqId();
		
		logger.info("RecREF" +recRefNo);
		
		for(int i=0 ; i< dataList.length;i++) {
			
			listContent = dataList[i].split("~");
			
			fixedLenghtList = Arrays.asList(listContent);
			  
			listOfString = new ArrayList<String>(fixedLenghtList);
			
			
			aadhaarDetail = "<Detail recRefNo=\""+recRefNo+"\" aadhaar=\""+listOfString.get(0)+"\" mapStatus=\""+listOfString.get(1)+"\" mdFlag=\""+listOfString.get(2)+"\"  mdCustDate=\""+listOfString.get(3)+"\" odFlag=\""+listOfString.get(4)+"\" odDate=\""+listOfString.get(5)+"\" previousIIN=\""+listOfString.get(6)+"\" />\r\n";
			aadhaarDtlsFinal.add(aadhaarDetail);
			aadhaarDetailFinal = aadhaarDetailFinal + aadhaarDtlsFinal.get(i);
			
		}
		
		
		xmlDataUnsigned = "<ach:AadhaarSeedingRqst xmlns:ach=\"http://npci.org/ach/schema/\" >\r\n" + 
				"<Head ver=\"1.0\" ts=\""+reqTimestamp+"\" />\r\n" + 
				"<Source type=\"CODE\" value=\"IDBI\" name=\"\" />	\r\n" + 
				"<Request id=\""+uniqueReqId+"\" type=\"AadhaarSeeding\" refUrl=\"\"/>\r\n" + 
				"<ReqData>\r\n" + 
				aadhaarDetailFinal+
				"</ReqData>	\r\n" + 
				"</ach:AadhaarSeedingRqst>";
		
		
		xmlDataSigned = signData.getSignedData(xmlDataUnsigned);
		
		//logger.info("Signed aadhaar data"+xmlDataSigned);
		
		aadhaarReq="{'Source':'"+sourceValue+"','Service':'"+serviceName+"','Type':'"+serviceType+"','Message':'"+xmlDataSigned+"'}";
		
		aadhaarReqDtls.setAadhaarReqId(uniqueReqId);
		aadhaarReqDtls.setAadhaarReqTimestamp(reqTimestamp);
		aadhaarReqDtls.setAadhaarServiceName(serviceName);
		aadhaarReqDtls.setAadhaarReqData(aadhaarReq);
		aadhaarRepository.save(aadhaarReqDtls);
		
		xmlDataSigned = DatatypeConverter.printBase64Binary(Base64.getEncoder().encode(xmlDataSigned.getBytes()));
		
		serviceName = DatatypeConverter.printBase64Binary(Base64.getEncoder().encode(serviceName.getBytes()));
		serviceType = DatatypeConverter.printBase64Binary(Base64.getEncoder().encode(serviceType.getBytes()));
		sourceValue = DatatypeConverter.printBase64Binary(Base64.getEncoder().encode(sourceValue.getBytes()));
		aadhaarReq="{'Source':'"+sourceValue+"','Service':'"+serviceName+"','Type':'"+serviceType+"','Message':'"+xmlDataSigned+"'}";
	
		//logger.info(aadhaarReq);
		
		RestTemplate restTemplate = new RestTemplate();
		
		logger.info("url is "+npciUri);
		SSLAuth.doTrustToCertificates();
		String npciResponse = restTemplate.postForObject( npciUri, aadhaarReq, String.class);
		
		
		logger.info(npciResponse);
		return npciResponse;
		
	
		
		
		 
	}
	
	public static String aadharSeedingResponse(String request) {
		
		logger.info("Inside response"+request);
		
		
		
		String ackData = "{'Source':'Demo','Service':'Demo','Type':'Acknowledgement','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
				"<NpciRefId value=\"\"/>\r\n"+
				"<Request id=\"234234234\"/>\r\n"+
				"<Resp ts=\"2017-10-16T10:02:00\" result=\"ACCEPTED\" errCode=\"\" rejectedBy=\"\" />\r\n"+
				"</nachapi:GatewayAck>'}";
		
		return ackData;
		 
	}
	
	
	

}
