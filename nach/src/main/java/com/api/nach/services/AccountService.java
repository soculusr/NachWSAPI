package com.api.nach.services;

import java.io.StringReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

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

@Component
public class AccountService extends Thread{
	

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
	
	String panDtlsService = "GetPanDtls";
	String acctHoldrService = "GetAccHolder";
	String acctStatusService = "GetAccStatus";
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
	
	private static Map<String, String> fiCustNamePan = new LinkedHashMap<String, String>();
	private static Map<String, String> fiCustNameIfsc = new LinkedHashMap<String, String>();
	private Map<String, String> acctTypesFi = new LinkedHashMap<String, String>();
	
	public AccountService() {
		
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

	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountService.class);
	
	
	public String getPanDtls(String request){
		
		
		RequestPanDtls requestPanDtls = new RequestPanDtls();
		ResponsePanDtls responsePanDtls = new ResponsePanDtls();
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
				
				panDtlsAcctHolder = "<AccHolder pan=\""+panDtlsCustPanNos.get(i)+"\"name=\""+panDtlsCustNames.get(i)+"\"/>\r\n";
				panDtlsFinal.add(panDtlsAcctHolder);
				panDtlsAcctHolders = panDtlsAcctHolders + panDtlsFinal.get(i);
				
			}
		}
		else {
			
			panDtlsAcctHolders = "<AccHolder pan=\""+panDtlsCustPanNos.get(0)+"\"name=\""+panDtlsCustNames.get(0)+"\"/>\r\n";
		}
		
		panDtlsResp = "{'Source':'"+destValue+"','Service':'"+panDtlsService+"','Type':'Response','Message':'<ach:GetPanDtlsResp xmlns:ach=\"http://npci.org/ach/schema/\">\r\n" + 
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
				"</ach:GetPanDtlsResp>'}";
		
		responsePanDtls.setId(requestRepositoryPanDtls.findByReqId(reqId));
		responsePanDtls.setServicename(panDtlsService);
		responsePanDtls.setResptimestamp(respTimestamp);
		responsePanDtls.setRqstid(reqId);
		responsePanDtls.setNpcirefid(npciRefId);
		responsePanDtls.setRespcontent(panDtlsResp);
		responseRepositoryPanDtls.save(responsePanDtls);
		
		kafkaTemplate.send(TOPIC, panDtlsResp);
		return getPanDtlsAck();
		}
		else {
			
			return getPanDtlsNack();
		}
		
		
		
	}
	
	public String getAcctHolderName(String request) {
		RequestAcctHoldr request2 = new RequestAcctHoldr();
		
		
		ResponseAcctHoldr response2 = new ResponseAcctHoldr(); 
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
		//System.out.println(xmlContent);
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
				
				acctHolderName = "<AccHolder name=\""+acctHoldrCustName.get(i)+"\" />\r\n";
				acctHoldrCustNames.add(acctHolderName);
				acctHolderNames = acctHolderNames + acctHoldrCustNames.get(i);
				
			}
		}
		else {
			
			acctHolderNames = "<AccHolder name=\""+acctHoldrCustName.get(0)+"\" />\r\n";
		}
		
		acctHolderResp = "{'Source':'"+destValue+"','Service':'"+acctHoldrService+"','Type':'Response','Message':'<ach:GetAccHolderResp xmlns:ach=\"http://npci.org/ach/schema/\">\r\n" + 
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
				"</ach:GetAccHolderResp>'}";
		
		
		response2.setId(requestRepositoryAcctHoldr.findByReqId(reqId));
		response2.setServicename(panDtlsService);
		response2.setResptimestamp(respTimestamp);
		response2.setRqstid(reqId);
		response2.setNpcirefid(npciRefId);
		response2.setRespcontent(acctHolderResp);
		responseRepositoryAcctHoldr.save(response2);
		
		kafkaTemplate.send(TOPIC, acctHolderResp);
		return getAcctHolderAck();
		}
		else {
			
			return getAcctHolderNack();
		}
		
	}
	
	public String getAcctStatus(String request) {
		RequestAcctStatus request3 = new RequestAcctStatus();
		ResponseAcctStatus response3 = new ResponseAcctStatus();
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
		//System.out.println("request data is" +request);
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
		acctStatusType = "<Account type=\""+acctTypesFi.get(acctTypeFi)+"\" status=\"S601\" />\r\n";
		
		acctStatusResp = "{'Source':'"+destValue+"','Service':'"+acctStatusService+"','Type':'Response','Message':'<ach:GetAccStatusResp xmlns:ach=\"http://npci.org/ach/schema/\">\r\n" + 
				"  <Head ts=\""+respTimestamp+"\" ver=\"1.0\"/>\r\n" + 
				"  <Source name=\""+sourceName+"\" type=\"CODE\" value=\""+sourceValue+"\"/>\r\n" + 
				"  <Destination name=\""+destName+"\" type=\"CODE\" value=\""+destValue+"\"/>\r\n" + 
				"  <Request id=\""+reqId+"\" refUrl=\"\" type=\"DETAILS_ENQ\"/>\r\n" + 
				"  <NpciRefId value=\""+npciRefId+"\"/>\r\n" + 
				"  <Resp ts=\""+respTimestamp+"\" result=\"SUCCESS\" errCode=\"\" rejectedBy=\"\"/>\r\n" + 
				"<RespData>\r\n" + 
				acctStatusType+ 
				"</RespData>\r\n" + 
				"</ach:GetAccStatusResp>'}";
		
		response3.setId(requestRepositoryAcctStatus.findByReqId(reqId));
		response3.setServicename(panDtlsService);
		response3.setResptimestamp(respTimestamp);
		response3.setRqstid(reqId);
		response3.setNpcirefid(npciRefId);
		response3.setRespcontent(acctStatusResp);
		responseRepositoryAcctStatus.save(response3);
		
		
		
		kafkaTemplate.send(TOPIC, acctStatusResp);
		return getAcctStatusAck();
		}
		else {
			return getAcctStatusNack();
		}
		
		
		
	}
	
	synchronized static void getDataFi(String acctNo) throws InterruptedException{
		
		String custPan = "";
		String custName = "";
		
		String fiUrl = "";
		
		String ifscCodeFi = "";
		fiCustNamePan.clear();
		fiCustNameIfsc.clear();
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new URL(fiUrl).openStream());
			document.getDocumentElement().normalize();
			
			fiMsg = document.getElementsByTagName("msg").item(0).getTextContent();
			
			NodeList nList = document.getElementsByTagName("acctHolderDtls");
			System.out.println("Nodelist length is "+nList.getLength());
			
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
					
				}
			}
			
			else {
				
				fiMsgList.add(fiMsg);
			}
			
			
		} catch (Exception e) {
			logger.error("Exception",e);
		}
		
	}
	
	public String getPanDtlsAck() {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String ackTimestamp = dateFormat.format(date);
		String ackData = "{'Source':'Demo','Service':'GetPanDtls','Type':'Acknowledgement','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
				"<NpciRefId value=\"\"/>\r\n"+
				"<Resp ts=\""+ackTimestamp+"\" result=\"ACCEPTED\" errCode=\"\" rejectedBy=\"\" />\r\n"+
				"</nachapi:GatewayAck>'}";
		
		return ackData;
			
		
	}
	
	public String getPanDtlsNack() {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String ackTimestamp = dateFormat.format(date);
		String nackData = "{'Source':'Demo','Service':'GetPanDtls','Type':'Account Not Found Error','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
				"<NpciRefId value=\"\"/>\r\n"+
				"<Resp ts=\""+ackTimestamp+"\" result=\"ERROR\" errCode=\"404\" rejectedBy=\"\" />\r\n"+
				"</nachapi:GatewayAck>'}";
		
		return nackData;
			
		
	}
	
	public String getAcctHolderAck() {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String ackTimestamp = dateFormat.format(date);
		String ackData = "{'Source':'Demo','Service':'GetAccHolder','Type':'Acknowledgement','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
				"<NpciRefId value=\"\"/>\r\n"+
				"<Resp ts=\""+ackTimestamp+"\" result=\"ACCEPTED\" errCode=\"\" rejectedBy=\"\" />\r\n"+
				"</nachapi:GatewayAck>'}";
		
		return ackData;
			
		
	}
	
	public String getAcctHolderNack() {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String ackTimestamp = dateFormat.format(date);
		String nackData = "{'Source':'Demo','Service':'GetAccHolder','Type':'Account Not Found Error','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
				"<NpciRefId value=\"\"/>\r\n"+
				"<Resp ts=\""+ackTimestamp+"\" result=\"ERROR\" errCode=\"404\" rejectedBy=\"\" />\r\n"+
				"</nachapi:GatewayAck>'}";
		
		return nackData;
			
		
	}

	public String getAcctStatusAck() {
	
	Date date = Calendar.getInstance().getTime();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	String ackTimestamp = dateFormat.format(date);
	String ackData = "{'Source':'Demo','Service':'GetAccStatus','Type':'Acknowledgement','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
			"<NpciRefId value=\"\"/>\r\n"+
			"<Resp ts=\""+ackTimestamp+"\" result=\"ACCEPTED\" errCode=\"\" rejectedBy=\"\" />\r\n"+
			"</nachapi:GatewayAck>'}";
	
	return ackData;
		
	
}
	
	public String getAcctStatusNack() {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String ackTimestamp = dateFormat.format(date);
		String nackData = "{'Source':'Demo','Service':'GetAccStatus','Type':'Account Not Found Error','Message':'<nachapi:GatewayAck xmlns:nachapi=\"http://demo.nachapi.com/\">\r\n"+
				"<NpciRefId value=\"\"/>\r\n"+
				"<Resp ts=\""+ackTimestamp+"\" result=\"ERROR\" errCode=\"404\" rejectedBy=\"\" />\r\n"+
				"</nachapi:GatewayAck>'}";
		
		return nackData;
			
		
	}
	
	
	
}



