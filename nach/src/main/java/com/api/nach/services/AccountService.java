package com.api.nach.services;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.api.nach.models.Request1;
import com.api.nach.models.Request2;
import com.api.nach.models.Request3;
import com.api.nach.repos.AccountRepository1;
import com.api.nach.repos.AccountRepository2;
import com.api.nach.repos.AccountRepository3;

@Component
public class AccountService {
	
	
	@Autowired
	private AccountRepository1 accountRepository1;
	
	@Autowired
	private AccountRepository2 accountRepository2;
	
	@Autowired
	private AccountRepository3 accountRepository3;
	
	String serviceName1 = "GetPanDtls";
	String serviceName2 = "GetAccHolder";
	String serviceName3 = "GetAccStatus";
	
	
	public String getPanDtls(String request) {
		Request1 request1 = new Request1();
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
				
				request1.setServicename(serviceName1);
				request1.setRqsttimestamp(reqTimestamp);
				request1.setRqstid(reqId);
				request1.setNpcirefid(npciRefId);
				request1.setRqstcontent(requestData);
				accountRepository1.save(request1);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "data added";
		
		
	}
	
	public String getAcctHolderName(String request) {
		Request2 request2 = new Request2();
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
				
				request2.setServicename(serviceName2);
				request2.setRqsttimestamp(reqTimestamp);
				request2.setRqstid(reqId);
				request2.setNpcirefid(npciRefId);
				request2.setRqstcontent(requestData);
				accountRepository2.save(request2);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "data added";
		
		
	}
	
	public String getAcctStatus(String request) {
		Request3 request3 = new Request3();
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
			e.printStackTrace();
		}
		
		
		return "data added";
		
		
	}
	
	
}
