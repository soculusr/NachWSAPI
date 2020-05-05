package com.api.nach.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.security.PublicKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
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

@Component
public class AccountRespOutService extends Thread{
	
	private XmlSigning signData = new XmlSigning();
	
	/*@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final String TOPIC = "kafka_demo";*/
	
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
	
	@Value("${fi.url}")
	String fiUrl = "";
	
	@Value("${npci.url}")
	String npciUrl = "";
	
	@Value("${public.cert}")
	String publicCertificate="";
	
	@Value("${keystore.path}")
	String KeyStoreFilePath = "";
	
	@Value("${keystore.pass}")
	String KeyStorePass = "";
	
	@Value("${keystore.alias}")
	String KeyStoreAlias = "";
	
	
	
	
	
	String panDtlsAcctHolder ="";
	String panDtlsAcctHolders="";
	String panDtlsResp="";
	String acctHolderName ="";
	String acctHolderNames="";
	String acctHolderResp="";
	String acctStatusType ="";
	String acctStatusResp="";
	static String acctTypeFi = "";
	static String fiMsg= "";
	RestTemplate restTemplate = new RestTemplate();
	private static Map<String, String> fiCustNamePan = new LinkedHashMap<String, String>();
	private static Map<String, String> fiCustNameIfsc = new LinkedHashMap<String, String>();
	private Map<String, String> acctTypesFi = new LinkedHashMap<String, String>();
	
	//String publicKeyFile = "keys" + File.separator + "public.pem";
	
	DataEncryption encryptData = new DataEncryption();
	
	DataEncryptDecrypt encryptDecrypt = new DataEncryptDecrypt();
	
	
	
	
	public AccountRespOutService() {
		
		acctTypesFi.put("SBA", "T651");
		acctTypesFi.put("CAA", "T652");
		acctTypesFi.put("CCA", "T653");
		acctTypesFi.put("ODA", "T654");
		
	}

	private static ArrayList<String> fiMsgList = new ArrayList<String>();
	private ArrayList<String> panDtlsCustNames = new ArrayList<String>();
	private ArrayList<String> panDtlsCustPanNos = new ArrayList<String>();
	private ArrayList<String> panDtlsFinal = new ArrayList<String>();
	private ArrayList<String> acctHoldrCustName = new ArrayList<String>();
	private ArrayList<String> acctHoldrCustNames = new ArrayList<String>();

	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountRespOutService.class);
	
	
	public ResponseEntity getPanDtls(String request){
		
		String currentWorkingDir = System.getProperty("user.dir");
        logger.info("Current Working Directory"+currentWorkingDir);
		logger.info("Finacle url is" +fiUrl);
		logger.info("Nach url is" +npciUrl);
		logger.info("Keystore is "+KeyStoreFilePath);
		PanDtlsReqIn requestPanDtls = new PanDtlsReqIn();
		PanDtlsRespOut responsePanDtls = new PanDtlsRespOut();
		String panDtlsService = "GetPanDtls";
		String resType="Response";
		String xmlContent = "";
		String acctNo = "";
		String sourceValue = "";
		String sourceName = "";
		String destValue = "";
		String destName = "";
		String npciRefId = "";
		String reqId="";
		
		String reqTimestamp = "";
		String xmlDataUnsigned = "";
		String xmlDataSigned = "";
		panDtlsAcctHolder ="";
		panDtlsAcctHolders="";
		panDtlsResp="";
		fiCustNamePan.clear();
		panDtlsCustNames.clear();
		panDtlsCustPanNos.clear();
		panDtlsFinal.clear();
		//System.out.println("request data is" +request);
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String respTimestamp = dateFormat.format(date);
		String requestData = request;
		
		//passing public key filepath
		//PublicKey publicKey = encryptDecrypt.readPublicKey(publicCertificate);
		xmlContent = requestData.substring(requestData.indexOf("<ach:"),requestData.indexOf("'}"));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		
		try {
			
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlContent)));
			Element root = document.getDocumentElement();
			logger.info("Root node is "+root.getNodeName());
			
			NodeList nListDtl = document.getElementsByTagName("Detail");
			NodeList nListRefId = document.getElementsByTagName("NpciRefId");
			NodeList nListSrc = document.getElementsByTagName("Source");
			NodeList nListDest = document.getElementsByTagName("Destination");
			NodeList nListReq = document.getElementsByTagName("Request");
			NodeList nListHead = document.getElementsByTagName("Head");
			
		
			logger.info("Node list length "+nListDtl.getLength());
			
			for(int i=0;i<nListDtl.getLength();i++) {
				
				Node dtlNode = nListDtl.item(i);
				Node refIdNode = nListRefId.item(i);
				Node srcNode = nListSrc.item(i);
				Node destNode = nListDest.item(i);
				Node reqNode = nListReq.item(i);
				Node headNode = nListHead.item(i);
				
				Element dtlElement = (Element) dtlNode;
				Element refIdElement = (Element) refIdNode;
				Element srcElement = (Element) srcNode;
				Element destElement = (Element) destNode;
				Element reqElement = (Element) reqNode;
				Element headElement = (Element) headNode;
				
				acctNo = dtlElement.getAttribute("accNo");
				npciRefId = refIdElement.getAttribute("value");
				sourceValue = srcElement.getAttribute("value");
				sourceName = srcElement.getAttribute("name");
				destValue = destElement.getAttribute("value");
				destName = destElement.getAttribute("name");
				reqId = reqElement.getAttribute("id");
				reqTimestamp = headElement.getAttribute("ts");
				
				requestPanDtls.setServicename(panDtlsService);
				requestPanDtls.setRqsttimestamp(reqTimestamp);
				requestPanDtls.setRqstid(reqId);
				requestPanDtls.setNpcirefid(npciRefId);
				requestPanDtls.setRqstcontent(requestData);
				requestRepositoryPanDtls.save(requestPanDtls);
				
				
				
			}
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		
		final String acctNoFinal = acctNo;
		
		/*Thread t1 = new Thread(new Runnable() {
			
			public void run() {
				
				
					try {
						getDataFi(acctNoFinal);
					} catch (InterruptedException e) {
						logger.error("Exception",e);
					}
					
				
			}
		});
		t1.start();*/
		
		//getDataFi(acctNo);
		
		fiCustNamePan.put("Nayan", "asdjkahskdjh");
		fiCustNamePan.put("Vaibhav", "agjshdgjasgdjhgj");
		fiCustNamePan.put("Aniket", "agjshdgjasasdgdjhgj");
		fiMsgList.add("Account Found");
		
		
		if(fiMsgList.get(0).equals("Account Found")) {
		Set s1 = fiCustNamePan.keySet();
		Set s2 = fiCustNamePan.entrySet();
		
		for(Map.Entry mE:fiCustNamePan.entrySet()) {
			
			panDtlsCustNames.add((String)mE.getKey());
			panDtlsCustPanNos.add((String)mE.getValue());
		}
		
		if(fiCustNamePan.size()>1) {
			
			for(int i=0;i<panDtlsCustNames.size();i++) {
				
				//Encryption using public key
				byte[] panDtls = panDtlsCustPanNos.get(i).getBytes();
				byte[] panDtlsData = null;
				try {
					panDtlsData = encryptDecrypt.encryptData(publicCertificate, panDtls);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception while data encryption",e);
				}
				String encryptedPanDtls = Base64.getEncoder().encodeToString(panDtlsData);
				//panDtlsAcctHolder = "<AccHolder pan=\""+panDtlsCustPanNos.get(i)+"\" name=\""+panDtlsCustNames.get(i)+"\" />\r\n";
				panDtlsAcctHolder = "<AccHolder pan=\""+encryptedPanDtls+"\" name=\""+panDtlsCustNames.get(i)+"\" />\r\n";
				panDtlsFinal.add(panDtlsAcctHolder);
				panDtlsAcctHolders = panDtlsAcctHolders + panDtlsFinal.get(i);
				
			}
		}
		else {
			
			panDtlsAcctHolders = "<AccHolder pan=\""+panDtlsCustPanNos.get(0)+"\" name=\""+panDtlsCustNames.get(0)+"\" />\r\n";
		}
		
		xmlDataUnsigned="<ach:GetPanDtlsResp xmlns:ach=\"http://npci.org/ach/schema/\">\r\n" + 
				"  <Head ts=\""+respTimestamp+"\" ver=\"1.0\"/>\r\n" + 
				"  <Source name=\""+sourceName+"\" type=\"CODE\" value=\""+sourceValue+"\"/>\r\n" + 
				"  <Destination name=\""+destName+"\" type=\"CODE\" value=\""+destValue+"\"/>\r\n" + 
				"  <Request id=\""+reqId+"\" refUrl=\"\" type=\"DETAILS_ENQ\"/>\r\n" + 
				"  <NpciRefId value=\""+npciRefId+"\"/>\r\n" + 
				"  <Resp ts=\""+respTimestamp+"\" result=\"SUCCESS\" errCode=\"\" rejectedBy=\"\" />\r\n" + 
				"  <RespData>\r\n" + 
				"<AccHolderList>\r\n" + 
				panDtlsAcctHolders+
				"	</AccHolderList>\r\n" + 
				"</RespData>\r\n" + 
				"</ach:GetPanDtlsResp>";
		
		
		
		//xml signing
		try {
			xmlDataSigned = signData.getSignedData(xmlDataUnsigned,KeyStoreFilePath, KeyStorePass, KeyStoreAlias);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Exception while signing data",e);
		}
		
		
		panDtlsResp = "{'Source':'"+destValue+"','Service':'"+panDtlsService+"','Type':'"+resType+"','Message':'"+xmlDataSigned+ "'}";
		
		
		responsePanDtls.setServicename(panDtlsService);
		responsePanDtls.setResptimestamp(respTimestamp);
		responsePanDtls.setRqstid(reqId);
		responsePanDtls.setNpcirefid(npciRefId);
		responsePanDtls.setRespcontent(panDtlsResp);
		responseRepositoryPanDtls.save(responsePanDtls);
		
		//Base64 Conversion
		
		xmlDataSigned = Base64.getEncoder().encodeToString(xmlDataSigned.getBytes());
		destValue = Base64.getEncoder().encodeToString(destValue.getBytes());
		panDtlsService = Base64.getEncoder().encodeToString(panDtlsService.getBytes());
		resType = Base64.getEncoder().encodeToString(resType.getBytes());
		panDtlsResp = "{'Source':'"+destValue+"','Service':'"+panDtlsService+"','Type':'"+resType+"','Message':'"+xmlDataSigned+ "'}";
		
		//kafkaTemplate.send(TOPIC, panDtlsResp);
		
		
		//Sending original response to URL
		try {
			sendPanDtlsResp(panDtlsResp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception while sendng response",e);
		}
		
		
		//Sending ack for request
		//return getPanDtlsAck(reqId);
		
		//return panDtlsResp;
		return new ResponseEntity(getPanDtlsAck(reqId), HttpStatus.ACCEPTED);
		
		
		}
		else {
			return new ResponseEntity(getPanDtlsNack(reqId), HttpStatus.NOT_FOUND);
			//return getPanDtlsNack(reqId);
		}
		
		
	}
	
	public ResponseEntity getAcctHolderName(String request){
		AcctHolderReqIn request2 = new AcctHolderReqIn();
		
		
		AcctHolderRespOut responseAcctHolder = new AcctHolderRespOut(); 
		String acctHoldrService = "GetAccHolder";
		String resType="Response";
		String xmlContent = "";
		String acctNo = "";
		String sourceValue = "";
		String sourceName = "";
		String destValue = "";
		String destName = "";
		String ifscCode = "";
		String npciRefId = "";
		String reqId="";
		String reqType="";
		String reqTimestamp = "";
		String xmlDataUnsigned = "";
		String xmlDataSigned = "";
		//System.out.println("request data is" +request);
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String respTimestamp = dateFormat.format(date);
		String requestData = request;
		fiCustNameIfsc.clear();
		acctHoldrCustName.clear();
		acctHoldrCustNames.clear();
		acctHolderName="";
		acctHolderNames="";
		acctHolderResp="";
		
		xmlContent = requestData.substring(requestData.indexOf("<ach:"),requestData.indexOf("'}"));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlContent)));
			Element root = document.getDocumentElement();
			System.out.println("Root node is "+root.getNodeName());
			
			NodeList nListDtl = document.getElementsByTagName("Detail");
			NodeList nListRefId = document.getElementsByTagName("NpciRefId");
			NodeList nListSrc = document.getElementsByTagName("Source");
			NodeList nListDest = document.getElementsByTagName("Destination");
			NodeList nListReq = document.getElementsByTagName("Request");
			NodeList nListHead = document.getElementsByTagName("Head");
			
			System.out.println("Node list length "+nListDtl.getLength());
			
			for(int i=0;i<nListDtl.getLength();i++) {
				
				Node dtlNode = nListDtl.item(i);
				Node refIdNode = nListRefId.item(i);
				Node srcNode = nListSrc.item(i);
				Node destNode = nListDest.item(i);
				Node reqNode = nListReq.item(i);
				Node headNode = nListHead.item(i);
				
				Element dtlElement = (Element) dtlNode;
				Element refIdElement = (Element) refIdNode;
				Element srcElement = (Element) srcNode;
				Element destElement = (Element) destNode;
				Element reqElement = (Element) reqNode;
				Element headElement = (Element) headNode;
				
				acctNo = dtlElement.getAttribute("accNo");
				if(xmlContent.contains("GetAccStatus"))
					ifscCode = dtlElement.getAttribute("ifsc");
				npciRefId = refIdElement.getAttribute("value");
				sourceValue = srcElement.getAttribute("value");
				sourceName = srcElement.getAttribute("name");
				destValue = destElement.getAttribute("value");
				destName = destElement.getAttribute("name");
				reqId = reqElement.getAttribute("id");
				reqTimestamp = headElement.getAttribute("ts");
				
				request2.setServicename(acctHoldrService);
				request2.setRqsttimestamp(reqTimestamp);
				request2.setRqstid(reqId);
				request2.setNpcirefid(npciRefId);
				request2.setRqstcontent(requestData);
				requestRepositoryAcctHoldr.save(request2);
				
			}
		} catch (Exception e) {
			logger.error("Exception",e);
		}
		
		final String acctNoFinal = acctNo;
		
		/*Thread t2 = new Thread(new Runnable() {
			
			public void run() {
				
				
					try {
						getDataFi(acctNoFinal);
					} catch (InterruptedException e) {
						logger.error("Exception",e);
					}
					
				
			}
		});
		t2.start();*/
		
		//getDataFi(acctNo);
		fiCustNameIfsc.put("Nayan", "asdjkahskdjh");
		fiCustNameIfsc.put("Vaibhav", "agjshdgjasgdjhgj");
		fiCustNameIfsc.put("Aniket", "agjshdgjasasdgdjhgj");
		fiMsgList.add("Account Found");
		logger.info(acctHoldrCustName.toString());
		
		if(fiMsgList.get(0).equals("Account Found")) {
		for(Map.Entry mE:fiCustNameIfsc.entrySet()) {
			
			acctHoldrCustName.add((String)mE.getKey());
			
		}
		
		if(fiCustNameIfsc.size()>1) {
			
			for(int i=0;i<acctHoldrCustName.size();i++) {
				
				logger.info("Account holder "+ acctHoldrCustName.get(i));
				
				acctHolderName = "<AccHolder> name=\""+acctHoldrCustName.get(i)+"\" </AccHolder>\r\n";
				acctHoldrCustNames.add(acctHolderName);
				acctHolderNames = acctHolderNames + acctHoldrCustNames.get(i);
				
			}
		}
		else {
			
			acctHolderNames = "<AccHolder> name=\""+acctHoldrCustName.get(0)+"\" </AccHolder>\r\n";
		}
		
		
		
		
		xmlDataUnsigned = "<ach:GetAccHolderResp xmlns:ach=\"http://npci.org/ach/schema/\">\r\n" + 
				"  <Head ts=\""+respTimestamp+"\" ver=\"1.0\"/>\r\n" + 
				"  <Source name=\""+sourceName+"\" type=\"CODE\" value=\""+sourceValue+"\"/>\r\n" + 
				"  <Destination name=\""+destName+"\" type=\"CODE\" value=\""+destValue+"\"/>\r\n" + 
				"  <Request id=\""+reqId+"\" refUrl=\"\" type=\"DETAILS_ENQ\"/>\r\n" + 
				"  <Resp ts=\""+respTimestamp+"\" result=\"SUCCESS\" errCode=\"\" rejectedBy=\"\" />\r\n" + 
				"  <RespData>\r\n" + 
				"  <AccHolderList>\r\n" + 
				acctHolderNames+
				"	</AccHolderList>\r\n" + 
				"  </RespData>\r\n" + 
				"  <NpciRefId value=\""+npciRefId+"\"/>\r\n" + 
				"</ach:GetAccHolderResp>";
		
		try {
			xmlDataSigned = signData.getSignedData(xmlDataUnsigned,KeyStoreFilePath, KeyStorePass, KeyStoreAlias);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Exception while signing data",e);
		}
		
		acctHolderResp = "{'Source':'"+destValue+"','Service':'"+acctHoldrService+"','Type':'"+resType+"','Message':'"+xmlDataSigned+"'}";
		
		
		responseAcctHolder.setServicename(acctHoldrService);
		responseAcctHolder.setResptimestamp(respTimestamp);
		responseAcctHolder.setRqstid(reqId);
		responseAcctHolder.setNpcirefid(npciRefId);
		responseAcctHolder.setRespcontent(acctHolderResp);
		responseRepositoryAcctHoldr.save(responseAcctHolder);
		
		//Base64 Conversion
		
		xmlDataSigned = Base64.getEncoder().encodeToString(xmlDataSigned.getBytes());
		destValue = Base64.getEncoder().encodeToString(destValue.getBytes());
		acctHoldrService =Base64.getEncoder().encodeToString(acctHoldrService.getBytes());
		resType = Base64.getEncoder().encodeToString(resType.getBytes());
		acctHolderResp = "{'Source':'"+destValue+"','Service':'"+acctHoldrService+"','Type':'"+resType+"','Message':'"+xmlDataSigned+"'}";
		//kafkaTemplate.send(TOPIC, acctHolderResp);
		
		
		try {
			sendAcctHolderResp(acctHolderResp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception while sending response",e);
		}
		
		//return getAcctHolderAck(reqId);
		return new ResponseEntity(getAcctHolderAck(reqId), HttpStatus.ACCEPTED);
		
		
		}
		else {
			
			//return getAcctHolderNack(reqId);
			return new ResponseEntity(getAcctHolderNack(reqId), HttpStatus.OK);
		}
		
	}
	
	public ResponseEntity getAcctStatus(String request){
		AcctStatusReqIn request3 = new AcctStatusReqIn();
		AcctStatusRespOut responseAcctStatus = new AcctStatusRespOut();
		String acctStatusService = "GetAccStatus";
		String resType="Response";
		String xmlContent = "";
		String acctNo = "";
		String sourceValue = "";
		String sourceName = "";
		String destValue = "";
		String destName = "";
		String npciRefId = "";
		String reqId="";
		String reqType="";
		String reqTimestamp = "";
		
		String xmlDataUnsigned = "";
		String xmlDataSigned = "";
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String respTimestamp = dateFormat.format(date);
		String requestData = request;
		acctStatusType="";
		acctStatusResp="";
		
		xmlContent = requestData.substring(requestData.indexOf("<ach:"),requestData.indexOf("'}"));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		//System.out.println(xmlContent);
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlContent)));
			Element root = document.getDocumentElement();
			logger.info("Root node is "+root.getNodeName());
			
			NodeList nListDtl = document.getElementsByTagName("Detail");
			NodeList nListRefId = document.getElementsByTagName("NpciRefId");
			NodeList nListSrc = document.getElementsByTagName("Source");
			NodeList nListDest = document.getElementsByTagName("Destination");
			NodeList nListReq = document.getElementsByTagName("Request");
			NodeList nListHead = document.getElementsByTagName("Head");
			
			logger.info("Node list length "+nListDtl.getLength());
			
			for(int i=0;i<nListDtl.getLength();i++) {
				
				Node dtlNode = nListDtl.item(i);
				Node refIdNode = nListRefId.item(i);
				Node srcNode = nListSrc.item(i);
				Node destNode = nListDest.item(i);
				Node reqNode = nListReq.item(i);
				Node headNode = nListHead.item(i);
				
				Element dtlElement = (Element) dtlNode;
				Element refIdElement = (Element) refIdNode;
				Element srcElement = (Element) srcNode;
				Element destElement = (Element) destNode;
				Element reqElement = (Element) reqNode;
				Element headElement = (Element) headNode;
				
				acctNo = dtlElement.getAttribute("accNo");
				npciRefId = refIdElement.getAttribute("value");
				sourceValue = srcElement.getAttribute("value");
				sourceName = srcElement.getAttribute("name");
				destValue = destElement.getAttribute("value");
				destName = destElement.getAttribute("name");
				reqId = reqElement.getAttribute("id");
				reqTimestamp = headElement.getAttribute("ts");
				
				request3.setServicename(acctStatusService);
				request3.setRqsttimestamp(reqTimestamp);
				request3.setRqstid(reqId);
				request3.setNpcirefid(npciRefId);
				request3.setRqstcontent(requestData);
				requestRepositoryAcctStatus.save(request3);
				
			}
		} catch (Exception e) {
			logger.error("Exception",e);
		}
		
		final String acctNoFinal = acctNo;
		
		/*Thread t3 = new Thread(new Runnable() {
			
			public void run() {
				
				
					try {
						getDataFi(acctNoFinal);
					} catch (InterruptedException e) {
						logger.error("Exception",e);
					}
					
				
			}
		});
		t3.start();*/
		
		//getDataFi(acctNo);
		
		acctTypeFi = "SBA";
		fiMsgList.add("Account Found");
		if(fiMsgList.get(0).equals("Account Found")) {
		acctStatusType = "<Account> type=\""+acctTypesFi.get(acctTypeFi)+"\" status=\"S601\" </Account>\r\n";
		
		xmlDataUnsigned = "<ach:GetAccStatusResp xmlns:ach=\"http://npci.org/ach/schema/\">\r\n" + 
				"  <Head ts=\""+respTimestamp+"\" ver=\"1.0\"/>\r\n" + 
				"  <Source name=\""+sourceName+"\" type=\"CODE\" value=\""+sourceValue+"\"/>\r\n" + 
				"  <Destination name=\""+destName+"\" type=\"CODE\" value=\""+destValue+"\"/>\r\n" + 
				"  <Request id=\""+reqId+"\" refUrl=\"\" type=\"DETAILS_ENQ\"/>\r\n" + 
				"  <NpciRefId value=\""+npciRefId+"\"/>\r\n" + 
				"  <Resp ts=\""+respTimestamp+"\" result=\"SUCCESS\" errCode=\"\" rejectedBy=\"\"/>\r\n" + 
				"<RespData>\r\n" + 
				acctStatusType+ 
				"</RespData>\r\n" + 
				"</ach:GetAccStatusResp>";
		
		try {
			xmlDataSigned = signData.getSignedData(xmlDataUnsigned,KeyStoreFilePath, KeyStorePass, KeyStoreAlias);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Exception while signing data",e);
		}
		
		acctStatusResp = "{'Source':'"+destValue+"','Service':'"+acctStatusService+"','Type':'"+resType+"','Message':'"+xmlDataSigned+"'}";
		
		
		responseAcctStatus.setServicename(acctStatusService);
		responseAcctStatus.setResptimestamp(respTimestamp);
		responseAcctStatus.setRqstid(reqId);
		responseAcctStatus.setNpcirefid(npciRefId);
		responseAcctStatus.setRespcontent(acctStatusResp);
		responseRepositoryAcctStatus.save(responseAcctStatus);
		
		//Base64 Conversion
		
		xmlDataSigned = Base64.getEncoder().encodeToString(xmlDataSigned.getBytes());
		destValue =Base64.getEncoder().encodeToString(destValue.getBytes());
		acctStatusService =Base64.getEncoder().encodeToString(acctStatusService.getBytes());
		resType = Base64.getEncoder().encodeToString(resType.getBytes());
		acctStatusResp = "{'Source':'"+destValue+"','Service':'"+acctStatusService+"','Type':'"+resType+"','Message':'"+xmlDataSigned+"'}";
		
		
		//kafkaTemplate.send(TOPIC, acctStatusResp);
		
		try {
			sendAcctStatusResp(acctStatusResp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception while sending response",e);
		}
		
		//return getAcctStatusAck(reqId);
		return new ResponseEntity(getAcctStatusAck(reqId), HttpStatus.ACCEPTED);
		//return acctStatusResp;
		}
		else {
			//return getAcctStatusNack(reqId);
			return new ResponseEntity(getAcctStatusNack(reqId), HttpStatus.OK);
		}
		
		
		
	}
	
	synchronized void getDataFi(String acctNo) throws InterruptedException{
		
		String custPan = "";
		String custName = "";
		
		
		
		String ifscCodeFi = "";
		fiCustNamePan.clear();
		fiCustNameIfsc.clear();
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			SSLAuth.doTrustToCertificates();
			Document document = db.parse(new URL(fiUrl).openStream());
			document.getDocumentElement().normalize();
			
			fiMsg = document.getElementsByTagName("msg").item(0).getTextContent();
			
			NodeList nList = document.getElementsByTagName("acctHolderDtls");
			logger.info("Nodelist length is "+nList.getLength());
			
			acctTypeFi = document.getElementsByTagName("accttype").item(0).getTextContent();
			ifscCodeFi = document.getElementsByTagName("brIFSCCode").item(0).getTextContent();
			
			if(nList.getLength()>0) {
				
				for(int i=0;i< nList.getLength();i++) {
					
					Node node = nList.item(i);
					
					Element element = (Element) node;
					custName = document.getElementsByTagName("custName").item(i).getTextContent();
					custPan = document.getElementsByTagName("custPAN").item(i).getTextContent();
					
					fiCustNamePan.put(custName, custPan);
					fiCustNameIfsc.put(custName, ifscCodeFi);
					fiMsgList.add(fiMsg);
					
				}
			}
			
			else {
				
				fiMsgList.add(fiMsg);
			}
			
			
		} catch (Exception e) {
			logger.error("Exception",e);
		}
		
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
	
	public String getPanDtlsNack(String reqId) {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String ackTimestamp = dateFormat.format(date);
		String nackData = "{'Source':'Demo','Service':'GetPanDtls','Type':'Account Not Found Error','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
				"<NpciRefId value=\"\"/>\r\n"+
				"<Request id=\""+reqId+"\"/>\r\n"+
				"<Resp ts=\""+ackTimestamp+"\" result=\"ERROR\" errCode=\"404\" rejectedBy=\"\" />\r\n"+
				"</nachapi:GatewayAck>'}";
		
		return nackData;
			
		
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
	
	public String getAcctHolderNack(String reqId) {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String ackTimestamp = dateFormat.format(date);
		String nackData = "{'Source':'Demo','Service':'GetAccHolder','Type':'Account Not Found Error','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
				"<NpciRefId value=\"\"/>\r\n"+
				"<Request id=\""+reqId+"\"/>\r\n"+
				"<Resp ts=\""+ackTimestamp+"\" result=\"ERROR\" errCode=\"404\" rejectedBy=\"\" />\r\n"+
				"</nachapi:GatewayAck>'}";
		
		return nackData;
			
		
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
	
	public String getAcctStatusNack(String reqId) {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String ackTimestamp = dateFormat.format(date);
		String nackData = "{'Source':'Demo','Service':'GetAccStatus','Type':'Account Not Found Error','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
				"<NpciRefId value=\"\"/>\r\n"+
				"<Request id=\""+reqId+"\"/>\r\n"+
				"<Resp ts=\""+ackTimestamp+"\" result=\"ERROR\" errCode=\"404\" rejectedBy=\"\" />\r\n"+
				"</nachapi:GatewayAck>'}";
		
		return nackData;
			
		
	}
	
	public void sendPanDtlsResp(String response) throws Exception{
		
		logger.info("In pan details");
		//SSLAuth.doTrustToCertificates();
		SSLHandshake.startHandshake();
		String npciResponse = restTemplate.postForObject( npciUrl, response, String.class);
		logger.info("Ack response for panDtlsService "+ npciResponse);
	}
	
	public void sendAcctHolderResp(String response) throws Exception {
		
		logger.info("In acct holder");
		//SSLAuth.doTrustToCertificates();
		SSLHandshake.startHandshake();
		String npciResponse = restTemplate.postForObject( npciUrl, response, String.class);
		logger.info("Ack response for acctHolderService "+ npciResponse);
	}

	public void sendAcctStatusResp(String response) throws Exception {
		
		logger.info("In acct status");
		//SSLAuth.doTrustToCertificates();
		SSLHandshake.startHandshake();
		String npciResponse = restTemplate.postForObject( npciUrl, response, String.class);
		logger.info("Ack response for acctStatusService "+ npciResponse);
	}
	
	
	
}



