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
public class AcctHolderReqOut {
	
	@Id
	@Column(name="UNIQUE_ID")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCT_HOLDR_REQ_OUT_SEQ")
    @SequenceGenerator(sequenceName = "ACCT_REQ_OUT_SEQ", allocationSize = 1, name = "ACCT_HOLDR_REQ_OUT_SEQ")
	private int acctHolderUniqueReqOutId;
	@Column(name="service_name")
	private String acctHolderReqOutServiceName;
	@Column(name="REQ_TIMESTAMP")
	private String acctHolderReqOutTimestamp;
	@Column(name="REQ_ID")
	private int acctHolderReqOutId;
	@Column(name="NPCIREF_VALUE")
	private String acctHolderReqOutNpciRefId;
	@Column(name="REQ_CONTENT")
	private String acctHolderReqOutContent;
	
	
	
	
	public AcctHolderReqOut() {
		
	}
	public int getAcctHolderUniqueReqOutId() {
		return acctHolderUniqueReqOutId;
	}
	public void setAcctHolderUniqueReqOutId(int acctHolderUniqueReqOutId) {
		this.acctHolderUniqueReqOutId = acctHolderUniqueReqOutId;
	}
	public String getAcctHolderReqOutServiceName() {
		return acctHolderReqOutServiceName;
	}
	public void setAcctHolderReqOutServiceName(String acctHolderReqOutServiceName) {
		this.acctHolderReqOutServiceName = acctHolderReqOutServiceName;
	}
	public String getAcctHolderReqOutTimestamp() {
		return acctHolderReqOutTimestamp;
	}
	public void setAcctHolderReqOutTimestamp(String acctHolderReqOutTimestamp) {
		this.acctHolderReqOutTimestamp = acctHolderReqOutTimestamp;
	}
	public int getAcctHolderReqOutId() {
		return acctHolderReqOutId;
	}
	public void setAcctHolderReqOutId(int acctHolderReqOutId) {
		this.acctHolderReqOutId = acctHolderReqOutId;
	}
	public String getAcctHolderReqOutNpciRefId() {
		return acctHolderReqOutNpciRefId;
	}
	public void setAcctHolderReqOutNpciRefId(String acctHolderReqOutNpciRefId) {
		this.acctHolderReqOutNpciRefId = acctHolderReqOutNpciRefId;
	}
	public String getAcctHolderReqOutContent() {
		return acctHolderReqOutContent;
	}
	public void setAcctHolderReqOutContent(String acctHolderReqOutContent) {
		this.acctHolderReqOutContent = acctHolderReqOutContent;
	}
	
	
	

}
