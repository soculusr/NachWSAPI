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

@Component
public class AccountService extends Thread{
	

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final String TOPIC = "kafka_demo";
	
	@Autowired
	private RequestRepository1 accountRepository1;
	
	@Autowired
	private RequestRepository2 accountRepository2;
	
	@Autowired
	private RequestRepository3 accountRepository3;
	
	@Autowired
	private ResponseRepository1 responseRepository1;
	
	@Autowired
	private ResponseRepository2 responseRepository2;
	
	@Autowired
	private ResponseRepository3 responseRepository3;
	
	String serviceName1 = "GetPanDtls";
	String serviceName2 = "GetAccHolder";
	String serviceName3 = "GetAccStatus";
	String respContent1 ="";
	String respContent2="";
	String respContent3="";
	String respContent4 ="";
	String respContent5="";
	String respContent6="";
	String respContent7 ="";
	String respContent8="";
	String respContent9="";
	static String acctTypeFi = "";
	static String fiMsg= "";
	
	private static Map<String, String> fiMap1 = new LinkedHashMap<String, String>();
	private static Map<String, String> fiMap2 = new LinkedHashMap<String, String>();
	private Map<String, String> fiMap3 = new LinkedHashMap<String, String>();
	private Map<String, String> acctTypesFi = new LinkedHashMap<String, String>();
	
	public AccountService() {
		
		acctTypesFi.put("SBA", "T651");
		acctTypesFi.put("CAA", "T652");
		acctTypesFi.put("CCA", "T653");
		acctTypesFi.put("ODA", "T654");
		
	}

	private static ArrayList<String> fiMsgList = new ArrayList<String>();
	private ArrayList<String> list1 = new ArrayList<String>();
	private ArrayList<String> list2 = new ArrayList<String>();
	private ArrayList<String> list3 = new ArrayList<String>();
	private ArrayList<String> list4 = new ArrayList<String>();
	private ArrayList<String> list5 = new ArrayList<String>();
	private ArrayList<String> list6 = new ArrayList<String>();
	private ArrayList<String> list7 = new ArrayList<String>();
	private ArrayList<String> list8 = new ArrayList<String>();
	private ArrayList<String> list9 = new ArrayList<String>();
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountService.class);
	
	
	public String getPanDtls(String request){
		
		
		Request1 request1 = new Request1();
		Response1 response1 = new Response1();
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
		respContent1 ="";
		respContent2="";
		respContent3="";
		fiMap1.clear();
		list1.clear();
		list2.clear();
		list3.clear();
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
			
			NodeList nList1 = document.getElementsByTagName("Detail");
			NodeList nList2 = document.getElementsByTagName("NpciRefId");
			NodeList nList3 = document.getElementsByTagName("Source");
			NodeList nList4 = document.getElementsByTagName("Destination");
			NodeList nList5 = document.getElementsByTagName("Request");
			NodeList nList6 = document.getElementsByTagName("Head");
			
		
			logger.info("Node list length "+nList1.getLength());
			
			for(int i=0;i<nList1.getLength();i++) {
				
				Node node1 = nList1.item(i);
				Node node2 = nList2.item(i);
				Node node3 = nList3.item(i);
				Node node4 = nList4.item(i);
				Node node5 = nList5.item(i);
				Node node6 = nList6.item(i);
				
				Element element1 = (Element) node1;
				Element element2 = (Element) node2;
				Element element3 = (Element) node3;
				Element element4 = (Element) node4;
				Element element5 = (Element) node5;
				Element element6 = (Element) node6;
				
				acctNo = element1.getAttribute("accNo");
				npciRefId = element2.getAttribute("value");
				sourceValue = element3.getAttribute("value");
				sourceName = element3.getAttribute("name");
				destValue = element4.getAttribute("value");
				destName = element4.getAttribute("name");
				reqId = element5.getAttribute("id");
				reqTimestamp = element6.getAttribute("ts");
				
				request1.setServicename(serviceName1);
				request1.setRqsttimestamp(reqTimestamp);
				request1.setRqstid(reqId);
				request1.setNpcirefid(npciRefId);
				request1.setRqstcontent(requestData);
				accountRepository1.save(request1);
				
				
				
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
		
		fiMap1.put("Nayan", "asdjkahskdjh");
		fiMap1.put("Vaibhav", "agjshdgjasgdjhgj");
		fiMap1.put("Aniket", "agjshdgjasasdgdjhgj");
		fiMsgList.add("Account Not Found");
		
		
		if(fiMsgList.get(0).equals("Account Found")) {
		Set s1 = fiMap1.keySet();
		Set s2 = fiMap1.entrySet();
		
		for(Map.Entry mE:fiMap1.entrySet()) {
			
			list1.add((String)mE.getKey());
			list2.add((String)mE.getValue());
		}
		
		if(fiMap1.size()>1) {
			
			for(int i=0;i<list1.size();i++) {
				
				respContent1 = "<AccHolder pan=\""+list2.get(i)+"\"name=\""+list1.get(i)+"\"/>\r\n";
				list3.add(respContent1);
				respContent2 = respContent2 + list3.get(i);
				
			}
		}
		else {
			
			respContent2 = "<AccHolder pan=\""+list2.get(0)+"\"name=\""+list1.get(0)+"\"/>\r\n";
		}
		
		respContent3 = "{'Source':'"+destValue+"','Service':'"+serviceName1+"','Type':'Response','Message':'<ach:GetPanDtlsResp xmlns:ach=\"http://npci.org/ach/schema/\">\r\n" + 
				"  <Head ts=\""+respTimestamp+"\" ver=\"1.0\"/>\r\n" + 
				"  <Source name=\""+sourceName+"\" type=\"CODE\" value=\""+sourceValue+"\"/>\r\n" + 
				"  <Destination name=\""+destName+"\" type=\"CODE\" value=\""+destValue+"\"/>\r\n" + 
				"  <Request id=\""+reqId+"\" refUrl=\"\" type=\"DETAILS_ENQ\"/>\r\n" + 
				"  <NpciRefId value=\""+npciRefId+"\"/>\r\n" + 
				"  <Resp ts=\""+respTimestamp+"\" result=\"SUCCESS\" errCode=\"\" rejectedBy=\"\" />\r\n" + 
				"  <RespData>\r\n" + 
				"<AccHolderList>\r\n" + 
				respContent2+
				"	</AccHolderList>\r\n" + 
				"</RespData>\r\n" + 
				"</ach:GetPanDtlsResp>'}";
		
		response1.setId(accountRepository1.findByReqId(reqId));
		response1.setServicename(serviceName1);
		response1.setResptimestamp(respTimestamp);
		response1.setRqstid(reqId);
		response1.setNpcirefid(npciRefId);
		response1.setRespcontent(respContent3);
		responseRepository1.save(response1);
		
		kafkaTemplate.send(TOPIC, respContent3);
		return getPanDtlsAck();
		}
		else {
			
			return getPanDtlsNack();
		}
		
		
		
	}
	
	public String getAcctHolderName(String request) {
		Request2 request2 = new Request2();
		
		
		Response2 response2 = new Response2(); 
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
		fiMap2.clear();
		list4.clear();
		list5.clear();
		respContent4="";
		respContent5="";
		respContent6="";
		
		xmlContent = requestData.substring(requestData.indexOf("<ach:"),requestData.indexOf("'}"));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		//System.out.println(xmlContent);
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlContent)));
			Element root = document.getDocumentElement();
			System.out.println("Root node is "+root.getNodeName());
			
			NodeList nList1 = document.getElementsByTagName("Detail");
			NodeList nList2 = document.getElementsByTagName("NpciRefId");
			NodeList nList3 = document.getElementsByTagName("Source");
			NodeList nList4 = document.getElementsByTagName("Destination");
			NodeList nList5 = document.getElementsByTagName("Request");
			NodeList nList6 = document.getElementsByTagName("Head");
			
			System.out.println("Node list length "+nList1.getLength());
			
			for(int i=0;i<nList1.getLength();i++) {
				
				Node node1 = nList1.item(i);
				Node node2 = nList2.item(i);
				Node node3 = nList3.item(i);
				Node node4 = nList4.item(i);
				Node node5 = nList5.item(i);
				Node node6 = nList6.item(i);
				
				Element element1 = (Element) node1;
				Element element2 = (Element) node2;
				Element element3 = (Element) node3;
				Element element4 = (Element) node4;
				Element element5 = (Element) node5;
				Element element6 = (Element) node6;
				
				acctNo = element1.getAttribute("accNo");
				if(xmlContent.contains("GetAccStatus"))
					ifscCode = element1.getAttribute("ifsc");
				npciRefId = element2.getAttribute("value");
				sourceValue = element3.getAttribute("value");
				sourceName = element3.getAttribute("name");
				destValue = element4.getAttribute("value");
				destName = element4.getAttribute("name");
				reqId = element5.getAttribute("id");
				reqTimestamp = element6.getAttribute("ts");
				
				request2.setServicename(serviceName2);
				request2.setRqsttimestamp(reqTimestamp);
				request2.setRqstid(reqId);
				request2.setNpcirefid(npciRefId);
				request2.setRqstcontent(requestData);
				accountRepository2.save(request2);
				
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
		fiMap2.put("Nayan", "asdjkahskdjh");
		fiMap2.put("Vaibhav", "agjshdgjasgdjhgj");
		fiMap2.put("Aniket", "agjshdgjasasdgdjhgj");
		fiMsgList.add("Account Not Found");
		logger.info(list4.toString());
		
		if(fiMsgList.get(0).equals("Account Found")) {
		for(Map.Entry mE:fiMap2.entrySet()) {
			
			list4.add((String)mE.getKey());
			
		}
		
		if(fiMap2.size()>1) {
			
			for(int i=0;i<list4.size();i++) {
				
				logger.info("Account holder "+ list4.get(i));
				
				respContent4 = "<AccHolder name=\""+list4.get(i)+"\" />\r\n";
				list5.add(respContent4);
				respContent5 = respContent5 + list5.get(i);
				
			}
		}
		else {
			
			respContent5 = "<AccHolder name=\""+list4.get(0)+"\" />\r\n";
		}
		
		respContent6 = "{'Source':'"+destValue+"','Service':'"+serviceName2+"','Type':'Response','Message':'<ach:GetAccHolderResp xmlns:ach=\"http://npci.org/ach/schema/\">\r\n" + 
				"  <Head ts=\""+respTimestamp+"\" ver=\"1.0\"/>\r\n" + 
				"  <Source name=\""+sourceName+"\" type=\"CODE\" value=\""+sourceValue+"\"/>\r\n" + 
				"  <Destination name=\""+destName+"\" type=\"CODE\" value=\""+destValue+"\"/>\r\n" + 
				"  <Request id=\""+reqId+"\" refUrl=\"\" type=\"DETAILS_ENQ\"/>\r\n" + 
				"  <Resp ts=\""+respTimestamp+"\" result=\"SUCCESS\" errCode=\"\" rejectedBy=\"\" />\r\n" + 
				"  <RespData>\r\n" + 
				"  <AccHolderList>\r\n" + 
				respContent5+
				"	</AccHolderList>\r\n" + 
				"  </RespData>\r\n" + 
				"  <NpciRefId value=\""+npciRefId+"\"/>\r\n" + 
				"</ach:GetAccHolderResp>'}";
		
		
		response2.setId(accountRepository2.findByReqId(reqId));
		response2.setServicename(serviceName1);
		response2.setResptimestamp(respTimestamp);
		response2.setRqstid(reqId);
		response2.setNpcirefid(npciRefId);
		response2.setRespcontent(respContent6);
		responseRepository2.save(response2);
		
		kafkaTemplate.send(TOPIC, respContent6);
		return getAcctHolderAck();
		}
		else {
			
			return getAcctHolderNack();
		}
		
	}
	
	public String getAcctStatus(String request) {
		Request3 request3 = new Request3();
		Response3 response3 = new Response3();
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
		respContent7="";
		respContent8="";
		
		xmlContent = requestData.substring(requestData.indexOf("<ach:"),requestData.indexOf("'}"));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		//System.out.println(xmlContent);
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlContent)));
			Element root = document.getDocumentElement();
			System.out.println("Root node is "+root.getNodeName());
			
			NodeList nList1 = document.getElementsByTagName("Detail");
			NodeList nList2 = document.getElementsByTagName("NpciRefId");
			NodeList nList3 = document.getElementsByTagName("Source");
			NodeList nList4 = document.getElementsByTagName("Destination");
			NodeList nList5 = document.getElementsByTagName("Request");
			NodeList nList6 = document.getElementsByTagName("Head");
			
			System.out.println("Node list length "+nList1.getLength());
			
			for(int i=0;i<nList1.getLength();i++) {
				
				Node node1 = nList1.item(i);
				Node node2 = nList2.item(i);
				Node node3 = nList3.item(i);
				Node node4 = nList4.item(i);
				Node node5 = nList5.item(i);
				Node node6 = nList6.item(i);
				
				Element element1 = (Element) node1;
				Element element2 = (Element) node2;
				Element element3 = (Element) node3;
				Element element4 = (Element) node4;
				Element element5 = (Element) node5;
				Element element6 = (Element) node6;
				
				acctNo = element1.getAttribute("accNo");
				npciRefId = element2.getAttribute("value");
				sourceValue = element3.getAttribute("value");
				sourceName = element3.getAttribute("name");
				destValue = element4.getAttribute("value");
				destName = element4.getAttribute("name");
				reqId = element5.getAttribute("id");
				reqTimestamp = element6.getAttribute("ts");
				
				request3.setServicename(serviceName3);
				request3.setRqsttimestamp(reqTimestamp);
				request3.setRqstid(reqId);
				request3.setNpcirefid(npciRefId);
				request3.setRqstcontent(requestData);
				accountRepository3.save(request3);
				
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
		respContent7 = "<Account type=\""+acctTypesFi.get(acctTypeFi)+"\" status=\"S601\" />\r\n";
		
		respContent8 = "{'Source':'"+destValue+"','Service':'"+serviceName3+"','Type':'Response','Message':'<ach:GetAccStatusResp xmlns:ach=\"http://npci.org/ach/schema/\">\r\n" + 
				"  <Head ts=\""+respTimestamp+"\" ver=\"1.0\"/>\r\n" + 
				"  <Source name=\""+sourceName+"\" type=\"CODE\" value=\""+sourceValue+"\"/>\r\n" + 
				"  <Destination name=\""+destName+"\" type=\"CODE\" value=\""+destValue+"\"/>\r\n" + 
				"  <Request id=\""+reqId+"\" refUrl=\"\" type=\"DETAILS_ENQ\"/>\r\n" + 
				"  <NpciRefId value=\""+npciRefId+"\"/>\r\n" + 
				"  <Resp ts=\""+respTimestamp+"\" result=\"SUCCESS\" errCode=\"\" rejectedBy=\"\"/>\r\n" + 
				"<RespData>\r\n" + 
				respContent7+ 
				"</RespData>\r\n" + 
				"</ach:GetAccStatusResp>'}";
		
		response3.setId(accountRepository3.findByReqId(reqId));
		response3.setServicename(serviceName1);
		response3.setResptimestamp(respTimestamp);
		response3.setRqstid(reqId);
		response3.setNpcirefid(npciRefId);
		response3.setRespcontent(respContent8);
		responseRepository3.save(response3);
		
		
		
		kafkaTemplate.send(TOPIC, respContent8);
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
		fiMap1.clear();
		fiMap2.clear();
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
					
					fiMap1.put(custName, custPan);
					fiMap2.put(custName, ifscCodeFi);
					
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



