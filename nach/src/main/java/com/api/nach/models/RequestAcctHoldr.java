package com.api.nach.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="request")
public class RequestAcctHoldr {
	
	@Id
	@Column(name="unique_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uniqueAcctHolderReqId;
	@Column(name="service_name")
	private String acctHoldrServiceName;
	@Column(name="request_timestamp")
	private String acctHoldrReqTimestamp;
	@Column(name="request_id")
	private String acctHoldrReqId;
	@Column(name="npciref_id")
	private String acttHoldrNpciRefId;
	@Column(name="request_content")
	private String acctHoldrReqContent;
	
	
	
	public RequestAcctHoldr() {

	}
	
	
	
	public int getId() {
		return uniqueAcctHolderReqId;
	}
	public void setId(int id) {
		this.uniqueAcctHolderReqId = id;
	}
	public String getServicename() {
		return acctHoldrServiceName;
	}
	public void setServicename(String servicename) {
		this.acctHoldrServiceName = servicename;
	}
	public String getRqsttimestamp() {
		return acctHoldrReqTimestamp;
	}
	public void setRqsttimestamp(String rqsttimestamp) {
		this.acctHoldrReqTimestamp = rqsttimestamp;
	}
	public String getRqstid() {
		return acctHoldrReqId;
	}
	public void setRqstid(String rqstid) {
		this.acctHoldrReqId = rqstid;
	}
	public String getNpcirefid() {
		return acttHoldrNpciRefId;
	}
	public void setNpcirefid(String npcirefid) {
		this.acttHoldrNpciRefId = npcirefid;
	}
	public String getRqstcontent() {
		return acctHoldrReqContent;
	}
	public void setRqstcontent(String rqstcontent) {
		this.acctHoldrReqContent = rqstcontent;
	}

}
