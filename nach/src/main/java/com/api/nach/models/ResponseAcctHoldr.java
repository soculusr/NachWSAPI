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
@Table(name="response")
public class ResponseAcctHoldr {
	
	@Id
	@Column(name="unique_id")
	private int acctHoldrUniqueRespId;
	@Column(name="service_name")
	private String acctHoldrRespServiceName;
	@Column(name="response_timestamp")
	private String acctHoldrRespTimestamp;
	@Column(name="request_id")
	private String acctHoldrResReqId;
	@Column(name="npciref_id")
	private String acctHoldrRespNpciRefId;
	@Column(name="response_content")
	private String respcontent5;
	
	
	
	public ResponseAcctHoldr() {
		
	}
	
	public int getId() {
		return acctHoldrUniqueRespId;
	}
	public void setId(int id) {
		this.acctHoldrUniqueRespId = id;
	}
	public String getServicename() {
		return acctHoldrRespServiceName;
	}
	public void setServicename(String servicename) {
		this.acctHoldrRespServiceName = servicename;
	}
	public String getResptimestamp() {
		return acctHoldrRespTimestamp;
	}
	public void setResptimestamp(String resptimestamp) {
		this.acctHoldrRespTimestamp = resptimestamp;
	}
	public String getRqstid() {
		return acctHoldrResReqId;
	}
	public void setRqstid(String rqstid) {
		this.acctHoldrResReqId = rqstid;
	}
	public String getNpcirefid() {
		return acctHoldrRespNpciRefId;
	}
	public void setNpcirefid(String npcirefid) {
		this.acctHoldrRespNpciRefId = npcirefid;
	}
	public String getRespcontent() {
		return respcontent5;
	}
	public void setRespcontent(String respcontent) {
		this.respcontent5 = respcontent;
	}
	
	
	
	

}
