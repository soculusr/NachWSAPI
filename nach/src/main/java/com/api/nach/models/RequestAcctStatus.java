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
public class RequestAcctStatus {
	
	@Id
	@Column(name="UNIQUE_ID")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCT_REQ_SEQ")
    @SequenceGenerator(sequenceName = "ACCT_REQ_IN_SEQ", allocationSize = 1, name = "ACCT_REQ_SEQ")
	private int acctStatusUniqueReqId;
	@Column(name="service_name")
	private String acctStatusServiceName;
	@Column(name="REQ_TIMESTAMP")
	private String acctStatusReqTimestamp;
	@Column(name="REQ_ID")
	private String acctStatusReqId;
	@Column(name="NPCIREF_VALUE")
	private String acctStatusNpciRefId;
	@Column(name="REQ_CONTENT")
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
