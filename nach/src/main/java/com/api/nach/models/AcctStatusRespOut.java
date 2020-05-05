package com.api.nach.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="acct_RES_data_OUT")
public class AcctStatusRespOut {
	
	
	@Id
	@Column(name="UNIQUE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCT_STATUS_RES_SEQ")
    @SequenceGenerator(sequenceName = "ACCT_RES_OUT_SEQ", allocationSize = 1, name = "ACCT_STATUS_RES_SEQ")
	private int acctStatusRespUniqueId;
	@Column(name="SERVICE_NAME")
	private String acctStatusRespServiceName;
	@Column(name="RES_TIMESTAMP")
	private String acctStatusRespTimestamp;
	@Column(name="REQ_ID")
	private String acctStatusRespReqId;
	@Column(name="NPCIREF_VALUE")
	private String acctStatusRespNpciRefId;
	@Column(name="RES_CONTENT")
	private String respcontent6;
	
	
	public AcctStatusRespOut() {
		
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
