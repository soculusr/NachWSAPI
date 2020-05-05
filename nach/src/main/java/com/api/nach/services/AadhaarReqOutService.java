package com.api.nach.services;


import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.security.PublicKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.api.nach.models.AadhaarReqDtlsAckIn;
import com.api.nach.models.AadhaarReqDtlsOut;
import com.api.nach.repos.AadhaarReqDtlsAckInRepository;
import com.api.nach.repos.AadhaarReqDtlsOutRepository;




@Service
public class AadhaarReqOutService {
	
	
	@Autowired
	private AadhaarReqDtlsOutRepository aadhaarRepository;
	
	@Autowired
	private AadhaarReqDtlsAckInRepository aadhaarAckRepository;

	@Value("${npci.url}")
	private String npciUri;
	
	@Value("${public.cert}")
	String publicCertificate="";
	
	@Value("${keystore.path}")
	String KeyStoreFilePath = "";
	
	@Value("${keystore.pass}")
	String KeyStorePass = "";
	
	@Value("${keystore.alias}")
	String KeyStoreAlias = "";
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountRespOutService.class);
	
	private XmlSigning signData = new XmlSigning();
	public void aadharSeedingRequest(String request){
		
		//String publicKeyFile = "keys" + File.separator + publicCertificate;
		
		DataEncryption encryptData = new DataEncryption();
		
		DataEncryptDecrypt encryptDecrypt = new DataEncryptDecrypt();
		
		//	PublicKey publicKey = encryptData.readPublicKey(publicCertificate);
		
		
		String [] dataList = request.split(",");
		ArrayList<String> aadhaarDtlsFinal = new ArrayList<String>();
		String sourceValue = "IBKL";
		String aadhaarDetail = "";
		String aadhaarDetailFinal = "";
		String aadhaarReq = "";
		String serviceName = "AadhaarSeeding";
		String serviceType = "Request";
		String encryptedAadhaarNo="";
		String ackNpciRefId = "";
		String ackRespTimestamp="";
		String ackRespResult="";
		String ackRespErrorCode="";
		String ackRespRejectedBy="";
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
		
		
		AadhaarReqDtlsOut aadhaarReqDtls = new AadhaarReqDtlsOut();
		
		AadhaarReqDtlsAckIn aadhaarReqDtlsAck = new AadhaarReqDtlsAckIn();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		recRefNo = aadhaarRepository.getRecRefNo();
		uniqueReqId = aadhaarRepository.getUniqueReqId();
		
		logger.info("RecREF" +recRefNo);
		
		for(int i=0 ; i< dataList.length;i++) {
			
			listContent = dataList[i].split("~");
			
			fixedLenghtList = Arrays.asList(listContent);
			  
			listOfString = new ArrayList<String>(fixedLenghtList);
			byte[] encryptedAadhaarNoData = null;
			try {
				encryptedAadhaarNoData = encryptDecrypt.encryptData(publicCertificate, listOfString.get(0).getBytes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Exception while encryption",e);;
			}
			encryptedAadhaarNo= Base64.getEncoder().encodeToString(encryptedAadhaarNoData);
			aadhaarDetail = "<Detail recRefNo=\""+recRefNo+"\" aadhaar=\""+encryptedAadhaarNo+"\" mapStatus=\""+listOfString.get(1)+"\" mdFlag=\""+listOfString.get(2)+"\"  mdCustDate=\""+listOfString.get(3)+"\" odFlag=\""+listOfString.get(4)+"\" odDate=\""+listOfString.get(5)+"\" previousIIN=\""+listOfString.get(6)+"\" />\r\n";
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
		
		
		try {
			xmlDataSigned = signData.getSignedData(xmlDataUnsigned,KeyStoreFilePath, KeyStorePass, KeyStoreAlias);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Exception while signing data",e);
		}
		
		//logger.info("Signed aadhaar data"+xmlDataSigned);
		
		aadhaarReq="{'Source':'"+sourceValue+"','Service':'"+serviceName+"','Type':'"+serviceType+"','Message':'"+xmlDataSigned+"'}";
		
		aadhaarReqDtls.setAadhaarReqId(uniqueReqId);
		aadhaarReqDtls.setAadhaarReqTimestamp(reqTimestamp);
		aadhaarReqDtls.setAadhaarServiceName(serviceName);
		aadhaarReqDtls.setAadhaarReqData(aadhaarReq);
		aadhaarRepository.save(aadhaarReqDtls);
		
		xmlDataSigned=Base64.getEncoder().encodeToString(xmlDataSigned.getBytes());
		serviceName = Base64.getEncoder().encodeToString(serviceName.getBytes());
		serviceType = Base64.getEncoder().encodeToString(serviceType.getBytes());
		sourceValue = Base64.getEncoder().encodeToString(sourceValue.getBytes());
		aadhaarReq="{'Source':'"+sourceValue+"','Service':'"+serviceName+"','Type':'"+serviceType+"','Message':'"+xmlDataSigned+"'}";
	
		//logger.info(aadhaarReq);
		
		RestTemplate restTemplate = new RestTemplate();
		
		logger.info("url is "+npciUri);
		//SSLAuth.doTrustToCertificates();
		SSLHandshake.startHandshake();
		String npciAckResponse = restTemplate.postForObject( npciUri, aadhaarReq, String.class);
		
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			logger.error("Exception in building document",e);
		}
		Document document = null;
		try {
			document = builder.parse(new InputSource(new StringReader(npciAckResponse)));
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			logger.error("Exception while parsing document",e);
		}
		
		NodeList nListRefId = document.getElementsByTagName("NpciRefId");
		NodeList nListResp = document.getElementsByTagName("Resp");
		
		for(int i=0;i<nListRefId.getLength();i++) {
			
			Node refIdNode = nListRefId.item(i);
			Node respNode = nListResp.item(i);
			
			Element refIdElement = (Element) refIdNode;
			Element respElement = (Element) respNode;
			
			ackNpciRefId = refIdElement.getAttribute("value");
			ackRespTimestamp = respElement.getAttribute("ts");
			ackRespResult = respElement.getAttribute("result");
			ackRespErrorCode = respElement.getAttribute("errCode");
			ackRespRejectedBy = respElement.getAttribute("rejectedBy");
			
			aadhaarReqDtlsAck.setAadhaarAckRespNpciRefId(ackNpciRefId);
			aadhaarReqDtlsAck.setAadhaarAckReqId(uniqueReqId);
			aadhaarReqDtlsAck.setAadhaarAckRespTimestamp(ackRespTimestamp);
			aadhaarReqDtlsAck.setAadhaarAckRespResult(ackRespResult);
			aadhaarReqDtlsAck.setAadhaarAckRespErrorCode(ackRespErrorCode);
			aadhaarReqDtlsAck.setAadhaarAckRespRejectedBy(ackRespRejectedBy);
			aadhaarReqDtlsAck.setAadhaarAckResData(npciAckResponse);
			aadhaarAckRepository.save(aadhaarReqDtlsAck);
			
			
		}
		//logger.info(npciAckResponse);
		
		
	
		
		
		 
	}
	
	public static String aadharSeedingResponse(String request) {
		
		logger.info("Inside response"+request);
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String resTimestamp = dateFormat.format(date);
		
		
		
		String ackData = "<ach:GatewayAck xmlns:ach=\"http://npci.org/ach/schema/\" >\r\n" + 
				"<NpciRefId value=\"6afd4578-f021-4321-a908-04b355a758fa\"/>\r\n" + 
				"<Resp ts=\""+resTimestamp+"\" result=\"ACCEPTED\" errCode=\"\" rejectedBy=\"\" />\r\n" + 
				"</ach:GatewayAck>";
		
		return ackData;
		 
	}
	
	
	

}
