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
@Table(name="acct_req_data_in")
public class AcctHolderReqIn {
	
	@Id
	@Column(name="UNIQUE_ID")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCT_REQ_SEQ")
    @SequenceGenerator(sequenceName = "ACCT_REQ_IN_SEQ", allocationSize = 1, name = "ACCT_REQ_SEQ")
	private int uniqueAcctHolderReqId;
	@Column(name="service_name")
	private String acctHoldrServiceName;
	@Column(name="REQ_TIMESTAMP")
	private String acctHoldrReqTimestamp;
	@Column(name="REQ_ID")
	private String acctHoldrReqId;
	@Column(name="NPCIREF_VALUE")
	private String acttHoldrNpciRefId;
	@Column(name="REQ_CONTENT")
	private String acctHoldrReqContent;
	
	
	
	public AcctHolderReqIn() {

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
