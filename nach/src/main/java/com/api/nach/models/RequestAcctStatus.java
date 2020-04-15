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
public class RequestAcctStatus {
	
	@Id
	@Column(name="unique_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int acctStatusUniqueReqId;
	@Column(name="service_name")
	private String acctStatusServiceName;
	@Column(name="request_timestamp")
	private String acctStatusReqTimestamp;
	@Column(name="request_id")
	private String acctStatusReqId;
	@Column(name="npciref_id")
	private String acctStatusNpciRefId;
	@Column(name="request_content")
	private String acctStatusReqContent;
	
	
	
	public RequestAcctStatus() {

	}
	
	
	
	public int getId() {
		return acctStatusUniqueReqId;
	}
	public void setId(int id) {
		this.acctStatusUniqueReqId = id;
	}
	public String getServicename() {
		return acctStatusServiceName;
	}
	public void setServicename(String servicename) {
		this.acctStatusServiceName = servicename;
	}
	public String getRqsttimestamp() {
		return acctStatusReqTimestamp;
	}
	public void setRqsttimestamp(String rqsttimestamp) {
		this.acctStatusReqTimestamp = rqsttimestamp;
	}
	public String getRqstid() {
		return acctStatusReqId;
	}
	public void setRqstid(String rqstid) {
		this.acctStatusReqId = rqstid;
	}
	public String getNpcirefid() {
		return acctStatusNpciRefId;
	}
	public void setNpcirefid(String npcirefid) {
		this.acctStatusNpciRefId = npcirefid;
	}
	public String getRqstcontent() {
		return acctStatusReqContent;
	}
	public void setRqstcontent(String rqstcontent) {
		this.acctStatusReqContent = rqstcontent;
	}

}
