package com.api.nach.services;

import java.io.IOException;
import java.io.StringReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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

import com.api.nach.models.AcctHolderReqOut;
import com.api.nach.models.AcctHolderReqOutAckIn;
import com.api.nach.models.AcctStatusReqOut;
import com.api.nach.models.AcctStatusReqOutAckIn;
import com.api.nach.models.PanDtlsReqOut;
import com.api.nach.models.PanDtlsReqOutAckIn;
import com.api.nach.repos.AcctHolderReqOutAckInRepository;
import com.api.nach.repos.AcctHolderReqOutRepository;
import com.api.nach.repos.AcctStatusReqOutAckInRepository;
import com.api.nach.repos.AcctStatusReqOutRepository;
import com.api.nach.repos.PanDtlsReqOutAckInRepository;
import com.api.nach.repos.PanDtlsReqOutRepository;



@Service
public class AccountReqOutService {
	
	@Autowired
	private PanDtlsReqOutRepository panDtlsReqOutRepository;
	
	@Autowired
	private PanDtlsReqOutAckInRepository panDtlsReqOutAckInRepo; 
	
	@Autowired
	private AcctHolderReqOutRepository acctHolderReqOutRepo;
	
	@Autowired
	private AcctHolderReqOutAckInRepository acctHolderReqOutAckInRepo;
	
	@Autowired
	private AcctStatusReqOutRepository acctStatusReqOutRepo;
	
	@Autowired
	private AcctStatusReqOutAckInRepository acctStatusReqOutAckInRepo;
	
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
	
	private DataEncryption encryptData = new DataEncryption();
	
	private DataEncryptDecrypt encryptDecrypt = new DataEncryptDecrypt();
	
	
	
	public void panDtlsReqOut(String request) {
		
		PanDtlsReqOut panDtlsReqOut = new PanDtlsReqOut();
		
		PanDtlsReqOutAckIn panDtlsReqOutAckIn = new PanDtlsReqOutAckIn();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		Document document = null;
		
		String [] dataList = request.split(",");
		String [] listContent = null;
		List<String> fixedLenghtList = null;
		ArrayList<String> listOfString = null;
		String destValue = "";
		String destBankName="";
		String sourceValue = "IBKL";
		String sourceBankName = "IDBI Bank";
		String serviceName = "GetPanDtls";
		String serviceType = "Request";
		String npciRefId="";
		String encryptedAcctNo="";
		String ackNpciRefId = "";
		String ackRespTimestamp="";
		String ackRespResult="";
		String ackRespErrorCode="";
		String ackRespRejectedBy="";
		int uniqueReqId;
		String xmlDataUnsigned = "";
		String xmlDataSigned = "";
		String panDtlsReqOutData="";
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String reqTimestamp = dateFormat.format(date);
		
		listContent=dataList[1].split("/");
		fixedLenghtList = Arrays.asList(listContent);
		  
		listOfString = new ArrayList<String>(fixedLenghtList);
		destBankName = listOfString.get(0);
		destValue = listOfString.get(1);
		uniqueReqId = panDtlsReqOutRepository.getUniqueReqId();
		
			byte[] encryptedAcctNoData = null;
			try {
				encryptedAcctNoData = encryptDecrypt.encryptData(publicCertificate, dataList[0].getBytes());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				logger.error("Exception while encryption",e1);
			}
			encryptedAcctNo = Base64.getEncoder().encodeToString(encryptedAcctNoData);
		
		logger.info("Dest bank is"+destBankName);
		logger.info("Dest value is"+destValue);
		logger.info("Acct no is "+dataList[0]);
		logger.info("Encrypted acct no is"+encryptedAcctNo);
		
		xmlDataUnsigned = "<ach:GetPanDtlsRqst xmlns:ach=\"http://npci.org/ach/schema/\" >\r\n" + 
				"			<Head ver=\"1.0\" ts=\""+reqTimestamp+"\" />	\r\n" + 
				"			<Source type=\"CODE\" value=\""+sourceValue+"\" name=\""+sourceBankName+"\" />\r\n" + 
				"			<Destination type=\"CODE\" value=\""+destValue+"\" name=\""+destBankName+"\" />\r\n" + 
				"			<Request id=\""+uniqueReqId+"\" type=\"DETAILS_ENQ\" refUrl=\"\"/>\r\n" + 
				"			<ReqData>\r\n" + 
				"				<Detail accNo=\""+encryptedAcctNo+"\" />\r\n" + 
				"			</ReqData>	\r\n "+
				"           <NpciRefId value=\"\" />\r\n"+ 
				"		</ach:GetPanDtlsRqst>";
		
		try {
			xmlDataSigned = signData.getSignedData(xmlDataUnsigned,KeyStoreFilePath, KeyStorePass, KeyStoreAlias);
		} catch (IOException e) {
			
			logger.error("Exception while sigining data", e);
		}
		
		panDtlsReqOutData="{'Source':'"+sourceValue+"','Service':'"+serviceName+"','Type':'"+serviceType+"','Message':'"+xmlDataSigned+"'}";
		
		panDtlsReqOut.setPanDtlsReqOutServiceName(serviceName);
		panDtlsReqOut.setPanDtlsReqOutId(uniqueReqId);
		panDtlsReqOut.setPanDtlsReqOutNpciRefId(npciRefId);
		panDtlsReqOut.setPanDtlsReqOutTimestamp(reqTimestamp);
		panDtlsReqOut.setPanDtlsReqOutContent(panDtlsReqOutData);
		panDtlsReqOutRepository.save(panDtlsReqOut);
		//logger.info("xmlsigneddata "+xmlDataSigned);
		
		//xmlDataSigned = DatatypeConverter.printBase64Binary(Base64.getEncoder().encode(xmlDataSigned.getBytes()));
		xmlDataSigned=Base64.getEncoder().encodeToString(xmlDataSigned.getBytes());
		serviceName = Base64.getEncoder().encodeToString(serviceName.getBytes());
		serviceType = Base64.getEncoder().encodeToString(serviceType.getBytes());
		sourceValue = Base64.getEncoder().encodeToString(sourceValue.getBytes());
		panDtlsReqOutData="{'Source':'"+sourceValue+"','Service':'"+serviceName+"','Type':'"+serviceType+"','Message':'"+xmlDataSigned+"'}";
		
		
		RestTemplate restTemplate = new RestTemplate();
		
		logger.info("url is "+npciUri);
		SSLHandshake.startHandshake();
		String npciAckResponse = restTemplate.postForObject( npciUri, panDtlsReqOutData, String.class);
		
		
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(new InputSource(new StringReader(npciAckResponse)));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			
			logger.error("Exception while parsing document", e);
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
			
			panDtlsReqOutAckIn.setPanDtlsReqOutAckNpciRefId(ackNpciRefId);
			panDtlsReqOutAckIn.setPanDtlsReqOutAckRespTimestamp(ackRespTimestamp);
			panDtlsReqOutAckIn.setPanDtlsReqOutAckRespResult(ackRespResult);
			panDtlsReqOutAckIn.setPanDtlsReqOutAckRespErrorCode(ackRespErrorCode);
			panDtlsReqOutAckIn.setPanDtlsReqOutAckRespRejectedBy(ackRespRejectedBy);
			panDtlsReqOutAckIn.setPanDtlsReqOutAckResData(npciAckResponse);
			panDtlsReqOutAckIn.setPanDtlsReqOutAckReqId(uniqueReqId);
			panDtlsReqOutAckInRepo.save(panDtlsReqOutAckIn);
			
			
			
			
		}
		
		
	}
	
	
	public void acctHolderReqOut(String request) {
	
		AcctHolderReqOut acctHolderReqOut =  new AcctHolderReqOut();
		AcctHolderReqOutAckIn acctHolderReqOutAckIn = new AcctHolderReqOutAckIn();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		Document document = null;
		
		String [] dataList = request.split(",");
		String [] listContent = null;
		List<String> fixedLenghtList = null;
		ArrayList<String> listOfString = null;
		String destValue = "";
		String destBankName="";
		String sourceValue = "IBKL";
		String sourceBankName = "IDBI Bank";
		String serviceName = "GetAccHolder";
		String serviceType = "Request";
		String npciRefId="";
		String encryptedAcctNo="";
		String ackNpciRefId = "";
		String ackRespTimestamp="";
		String ackRespResult="";
		String ackRespErrorCode="";
		String ackRespRejectedBy="";
		int uniqueReqId;
		String xmlDataUnsigned = "";
		String xmlDataSigned = "";
		String acctHolderReqOutData="";
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String reqTimestamp = dateFormat.format(date);
		
		listContent=dataList[1].split("/");
		fixedLenghtList = Arrays.asList(listContent);
		  
		listOfString = new ArrayList<String>(fixedLenghtList);
		destBankName = listOfString.get(0);
		destValue = listOfString.get(1);
		uniqueReqId = acctHolderReqOutRepo.getUniqueReqId();
		byte[] encryptedAcctNoData = null;
		try {
			encryptedAcctNoData = encryptDecrypt.encryptData(publicCertificate, dataList[0].getBytes());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error("Exception while encryption",e1);
		}
		encryptedAcctNo = Base64.getEncoder().encodeToString(encryptedAcctNoData);
		logger.info("Dest bank is"+destBankName);
		logger.info("Dest value is"+destValue);
		logger.info("Acct no is "+dataList[0]);
		logger.info("Encrypted acct no is"+encryptedAcctNo);
		
		xmlDataUnsigned = "<ach:GetAccHolderRqst xmlns:ach=\"http://npci.org/ach/schema/\">\r\n" + 
				"			<Head ts=\""+reqTimestamp+"\" ver=\"1.0\"/>	\r\n" + 
				"			<Source name=\""+sourceBankName+"\" type=\"CODE\" value=\""+sourceValue+"\"/>\r\n" + 
				"			<Destination name=\""+destBankName+"\" type=\"CODE\" value=\""+destValue+"\"/>\r\n" + 
				"			<Request id=\""+uniqueReqId+"\" refUrl=\"\" type=\"DETAILS_ENQ\"/>\r\n" + 
				"			<ReqData>\r\n" + 
				"				<Detail accNo=\""+encryptedAcctNo+"\"/>\r\n" + 
				"			</ReqData>	\r\n" + 
				"			<NpciRefId value=\"\"/>\r\n" + 
				"		</ach:GetAccHolderRqst>";
		
		try {
			xmlDataSigned = signData.getSignedData(xmlDataUnsigned,KeyStoreFilePath, KeyStorePass, KeyStoreAlias);
		} catch (IOException e) {
			
			logger.error("Exception while sigining data", e);
		}
		
		acctHolderReqOutData="{'Source':'"+sourceValue+"','Service':'"+serviceName+"','Type':'"+serviceType+"','Message':'"+xmlDataSigned+"'}";
		
		acctHolderReqOut.setAcctHolderReqOutServiceName(serviceName);
		acctHolderReqOut.setAcctHolderReqOutId(uniqueReqId);
		acctHolderReqOut.setAcctHolderReqOutNpciRefId(npciRefId);
		acctHolderReqOut.setAcctHolderReqOutTimestamp(reqTimestamp);
		acctHolderReqOut.setAcctHolderReqOutContent(acctHolderReqOutData);
		acctHolderReqOutRepo.save(acctHolderReqOut);
		
		xmlDataSigned=Base64.getEncoder().encodeToString(xmlDataSigned.getBytes());
		serviceName = Base64.getEncoder().encodeToString(serviceName.getBytes());
		serviceType = Base64.getEncoder().encodeToString(serviceType.getBytes());
		sourceValue = Base64.getEncoder().encodeToString(sourceValue.getBytes());
		acctHolderReqOutData="{'Source':'"+sourceValue+"','Service':'"+serviceName+"','Type':'"+serviceType+"','Message':'"+xmlDataSigned+"'}";
		RestTemplate restTemplate = new RestTemplate();
		
		logger.info("url is "+npciUri);
		SSLHandshake.startHandshake();
		String npciAckResponse = restTemplate.postForObject( npciUri, acctHolderReqOutData, String.class);
		
		
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(new InputSource(new StringReader(npciAckResponse)));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			
			logger.error("Exception while parsing document", e);
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
			
			acctHolderReqOutAckIn.setAcctHolderReqOutAckNpciRefId(ackNpciRefId);
			acctHolderReqOutAckIn.setAcctHolderReqOutAckResData(npciAckResponse);
			acctHolderReqOutAckIn.setAcctHolderReqOutAckRespResult(ackRespResult);
			acctHolderReqOutAckIn.setAcctHolderReqOutAckRespTimestamp(ackRespTimestamp);
			acctHolderReqOutAckIn.setAcctHolderReqOutAckRespErrorCode(ackRespErrorCode);
			acctHolderReqOutAckIn.setAcctHolderReqOutAckRespRejectedBy(ackRespRejectedBy);
			acctHolderReqOutAckIn.setAcctHolderReqOutAckReqId(uniqueReqId);
			acctHolderReqOutAckInRepo.save(acctHolderReqOutAckIn);
			
			
			
		}
		
		
		
		
	}
	
	public void acctStatusReqOut(String request) {
		
		AcctStatusReqOut acctStatusReqOut = new AcctStatusReqOut();
		AcctStatusReqOutAckIn acctStatusReqOutAckIn = new AcctStatusReqOutAckIn();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		Document document = null;
		
		String [] dataList = request.split(",");
		String [] listContent = null;
		List<String> fixedLenghtList = null;
		ArrayList<String> listOfString = null;
		String destValue = "";
		String destBankName="";
		String sourceValue = "IBKL";
		String sourceBankName = "IDBI Bank";
		String serviceName = "GetAccStatus";
		String serviceType = "Request";
		String ifscCode = "";
		String npciRefId="";
		String encryptedAcctNo="";
		String ackNpciRefId = "";
		String ackRespTimestamp="";
		String ackRespResult="";
		String ackRespErrorCode="";
		String ackRespRejectedBy="";
		int uniqueReqId;
		String xmlDataUnsigned = "";
		String xmlDataSigned = "";
		String acctStatusReqOutData="";
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String reqTimestamp = dateFormat.format(date);
		
		listContent=dataList[2].split("/");
		fixedLenghtList = Arrays.asList(listContent);
		  
		listOfString = new ArrayList<String>(fixedLenghtList);
		ifscCode = dataList[1];
		destBankName = listOfString.get(0);
		destValue = listOfString.get(1);
		uniqueReqId = acctStatusReqOutRepo.getUniqueReqId();
		byte[] encryptedAcctNoData = null;
		try {
			encryptedAcctNoData = encryptDecrypt.encryptData(publicCertificate, dataList[0].getBytes());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error("Exception while encryption",e1);
		}
		encryptedAcctNo = Base64.getEncoder().encodeToString(encryptedAcctNoData);
		logger.info("Dest bank is"+destBankName);
		logger.info("Dest value is"+destValue);
		logger.info("Ifsc code is "+dataList[1]);
		logger.info("Acct no is "+dataList[0]);
		logger.info("Encrypted acct no is"+encryptedAcctNo);
		
		xmlDataUnsigned = "<ach:GetAccStatusRqst xmlns:ach=\"http://npci.org/ach/schema/\">\r\n" + 
				"			<Head ts=\""+reqTimestamp+"\" ver=\"1.0\"/>	\r\n" + 
				"			<Source type=\"CODE\" value=\""+sourceValue+"\" name=\""+sourceBankName+"\" />\r\n" + 
				"			<Destination type=\"CODE\" value=\""+destValue+"\" name=\""+destBankName+"\" />\r\n" + 
				"			<Request id=\""+uniqueReqId+"\" type=\"DETAILS_ENQ\" refUrl=\"\"/>\r\n" + 
				"			<ReqData>\r\n" + 
				"				<Detail accNo=\""+encryptedAcctNo+"\" ifsc=\""+ifscCode+"\" />\r\n" + 
				"			</ReqData>	\r\n" + 
				"			<NpciRefId value=\"sss\" />\r\n" + 
				"		</ach:GetAccStatusRqst>";
		
		try {
			xmlDataSigned = signData.getSignedData(xmlDataUnsigned,KeyStoreFilePath, KeyStorePass, KeyStoreAlias);
		} catch (IOException e) {
			
			logger.error("Exception while sigining data", e);
		}
		
		acctStatusReqOutData="{'Source':'"+sourceValue+"','Service':'"+serviceName+"','Type':'"+serviceType+"','Message':'"+xmlDataSigned+"'}";
		
		acctStatusReqOut.setAcctStatusReqOutId(uniqueReqId);
		acctStatusReqOut.setAcctStatusReqOutNpciRefId(npciRefId);
		acctStatusReqOut.setAcctStatusReqOutServiceName(serviceName);
		acctStatusReqOut.setAcctStatusReqOutTimestamp(reqTimestamp);
		acctStatusReqOut.setAcctStatusReqOutContent(acctStatusReqOutData);
		acctStatusReqOutRepo.save(acctStatusReqOut);
		
		xmlDataSigned=Base64.getEncoder().encodeToString(xmlDataSigned.getBytes());
		serviceName = Base64.getEncoder().encodeToString(serviceName.getBytes());
		serviceType = Base64.getEncoder().encodeToString(serviceType.getBytes());
		sourceValue = Base64.getEncoder().encodeToString(sourceValue.getBytes());
		acctStatusReqOutData="{'Source':'"+sourceValue+"','Service':'"+serviceName+"','Type':'"+serviceType+"','Message':'"+xmlDataSigned+"'}";
		RestTemplate restTemplate = new RestTemplate();
		
		logger.info("url is "+npciUri);
		SSLHandshake.startHandshake();
		String npciAckResponse = restTemplate.postForObject( npciUri, acctStatusReqOutData, String.class);
		
		
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(new InputSource(new StringReader(npciAckResponse)));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			
			logger.error("Exception while parsing document", e);
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
			
			acctStatusReqOutAckIn.setAcctStatusReqOutAckNpciRefId(ackNpciRefId);
			acctStatusReqOutAckIn.setAcctStatusReqOutAckReqId(uniqueReqId);
			acctStatusReqOutAckIn.setAcctStatusReqOutAckRespErrorCode(ackRespErrorCode);
			acctStatusReqOutAckIn.setAcctStatusReqOutAckRespRejectedBy(ackRespRejectedBy);
			acctStatusReqOutAckIn.setAcctStatusReqOutAckRespResult(ackRespResult);
			acctStatusReqOutAckIn.setAcctStatusReqOutAckRespTimestamp(ackRespTimestamp);
			acctStatusReqOutAckIn.setAcctStatusReqOutAckResData(npciAckResponse);
			acctStatusReqOutAckInRepo.save(acctStatusReqOutAckIn);
			
			
			
		}
	}

}
