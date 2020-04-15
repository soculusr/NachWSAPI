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
public class ResponseAcctStatus {
	
	
	@Id
	@Column(name="unique_id")
	private int acctStatusRespUniqueId;
	@Column(name="service_name")
	private String acctStatusRespServiceName;
	@Column(name="response_timestamp")
	private String acctStatusRespTimestamp;
	@Column(name="request_id")
	private String acctStatusRespReqId;
	@Column(name="npciref_id")
	private String acctStatusRespNpciRefId;
	@Column(name="response_content")
	private String respcontent6;
	
	
	public ResponseAcctStatus() {
		
	}
	public int getId() {
		return acctStatusRespUniqueId;
	}
	public void setId(int id) {
		this.acctStatusRespUniqueId = id;
	}
	public String getServicename() {
		return acctStatusRespServiceName;
	}
	public void setServicename(String servicename) {
		this.acctStatusRespServiceName = servicename;
	}
	public String getResptimestamp() {
		return acctStatusRespTimestamp;
	}
	public void setResptimestamp(String resptimestamp) {
		this.acctStatusRespTimestamp = resptimestamp;
	}
	public String getRqstid() {
		return acctStatusRespReqId;
	}
	public void setRqstid(String rqstid) {
		this.acctStatusRespReqId = rqstid;
	}
	public String getNpcirefid() {
		return acctStatusRespNpciRefId;
	}
	public void setNpcirefid(String npcirefid) {
		this.acctStatusRespNpciRefId = npcirefid;
	}
	public String getRespcontent() {
		return respcontent6;
	}
	public void setRespcontent(String respcontent) {
		this.respcontent6 = respcontent;
	}

}
