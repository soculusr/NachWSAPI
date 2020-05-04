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
@Table(name="acct_req_data_out")
public class AcctStatusReqOut {
	
	@Id
	@Column(name="UNIQUE_ID")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCT_STATUS_REQ_OUT_SEQ")
    @SequenceGenerator(sequenceName = "ACCT_REQ_OUT_SEQ", allocationSize = 1, name = "ACCT_STATUS_REQ_OUT_SEQ")
	private int acctStatusUniqueReqOutId;
	@Column(name="service_name")
	private String acctStatusReqOutServiceName;
	@Column(name="REQ_TIMESTAMP")
	private String acctStatusReqOutTimestamp;
	@Column(name="REQ_ID")
	private int acctStatusReqOutId;
	@Column(name="NPCIREF_VALUE")
	private String acctStatusReqOutNpciRefId;
	@Column(name="REQ_CONTENT")
	private String acctStatusReqOutContent;
	
	public AcctStatusReqOut() {
		
	}
	public int getAcctStatusUniqueReqOutId() {
		return acctStatusUniqueReqOutId;
	}
	public void setAcctStatusUniqueReqOutId(int acctStatusUniqueReqOutId) {
		this.acctStatusUniqueReqOutId = acctStatusUniqueReqOutId;
	}
	public String getAcctStatusReqOutServiceName() {
		return acctStatusReqOutServiceName;
	}
	public void setAcctStatusReqOutServiceName(String acctStatusReqOutServiceName) {
		this.acctStatusReqOutServiceName = acctStatusReqOutServiceName;
	}
	public String getAcctStatusReqOutTimestamp() {
		return acctStatusReqOutTimestamp;
	}
	public void setAcctStatusReqOutTimestamp(String acctStatusReqOutTimestamp) {
		this.acctStatusReqOutTimestamp = acctStatusReqOutTimestamp;
	}
	public int getAcctStatusReqOutId() {
		return acctStatusReqOutId;
	}
	public void setAcctStatusReqOutId(int acctStatusReqOutId) {
		this.acctStatusReqOutId = acctStatusReqOutId;
	}
	public String getAcctStatusReqOutNpciRefId() {
		return acctStatusReqOutNpciRefId;
	}
	public void setAcctStatusReqOutNpciRefId(String acctStatusReqOutNpciRefId) {
		this.acctStatusReqOutNpciRefId = acctStatusReqOutNpciRefId;
	}
	public String getAcctStatusReqOutContent() {
		return acctStatusReqOutContent;
	}
	public void setAcctStatusReqOutContent(String acctStatusReqOutContent) {
		this.acctStatusReqOutContent = acctStatusReqOutContent;
	}
	

}
