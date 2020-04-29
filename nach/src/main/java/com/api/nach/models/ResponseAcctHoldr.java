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
@Table(name="acct_RES_data_OUT")
public class ResponseAcctHoldr {
	
	@Id
	@Column(name="UNIQUE_ID")
	private int acctHoldrUniqueRespId;
	@Column(name="SERVICE_NAME")
	private String acctHoldrRespServiceName;
	@Column(name="RES_TIMESTAMP")
	private String acctHoldrRespTimestamp;
	@Column(name="REQ_ID")
	private String acctHoldrResReqId;
	@Column(name="NPCIREF_VALUE")
	private String acctHoldrRespNpciRefId;
	@Column(name="RES_CONTENT")
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
