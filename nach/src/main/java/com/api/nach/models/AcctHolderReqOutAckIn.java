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
@Table(name="ACCT_REQ_OUT_ACK_IN")
public class AcctHolderReqOutAckIn {
	
	@Id
	@Column(name="UNIQUE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCT_HOLDER_REQ_OUT_ACK")
    @SequenceGenerator(sequenceName = "ACCT_REQ_OUT_ACK_SEQ", allocationSize = 1, name = "ACCT_HOLDER_REQ_OUT_ACK")
	private int uniqueAcctHolderReqAckId;
	@Column(name="RES_TIMESTAMP")
	private String acctHolderReqOutAckRespTimestamp;
	@Column(name="REQ_ID")
	private int acctHolderReqOutAckReqId;
	@Column(name="NPCIREF_VALUE")
	private String acctHolderReqOutAckNpciRefId;
	@Column(name="ACK_CONTENT")
	private String acctHolderReqOutAckResData;
	@Column(name="ACK_RESULT")
	private String acctHolderReqOutAckRespResult;
	@Column(name="ERROR_CODE")
	private String acctHolderReqOutAckRespErrorCode;
	@Column(name="REJECTED_BY")
	private String acctHolderReqOutAckRespRejectedBy;
	
	
public AcctHolderReqOutAckIn() {
		
	}
	
	
	public String getAcctHolderReqOutAckRespErrorCode() {
		return acctHolderReqOutAckRespErrorCode;
	}
	public void setAcctHolderReqOutAckRespErrorCode(String acctHolderReqOutAckRespErrorCode) {
		this.acctHolderReqOutAckRespErrorCode = acctHolderReqOutAckRespErrorCode;
	}
	public String getAcctHolderReqOutAckRespRejectedBy() {
		return acctHolderReqOutAckRespRejectedBy;
	}
	public void setAcctHolderReqOutAckRespRejectedBy(String acctHolderReqOutAckRespRejectedBy) {
		this.acctHolderReqOutAckRespRejectedBy = acctHolderReqOutAckRespRejectedBy;
	}
	
	public int getUniqueAcctHolderReqAckId() {
		return uniqueAcctHolderReqAckId;
	}
	public void setUniqueAcctHolderReqAckId(int uniqueAcctHolderReqAckId) {
		this.uniqueAcctHolderReqAckId = uniqueAcctHolderReqAckId;
	}
	public String getAcctHolderReqOutAckRespTimestamp() {
		return acctHolderReqOutAckRespTimestamp;
	}
	public void setAcctHolderReqOutAckRespTimestamp(String acctHolderReqOutAckRespTimestamp) {
		this.acctHolderReqOutAckRespTimestamp = acctHolderReqOutAckRespTimestamp;
	}
	public int getAcctHolderReqOutAckReqId() {
		return acctHolderReqOutAckReqId;
	}
	public void setAcctHolderReqOutAckReqId(int acctHolderReqOutAckReqId) {
		this.acctHolderReqOutAckReqId = acctHolderReqOutAckReqId;
	}
	public String getAcctHolderReqOutAckNpciRefId() {
		return acctHolderReqOutAckNpciRefId;
	}
	public void setAcctHolderReqOutAckNpciRefId(String acctHolderReqOutAckNpciRefId) {
		this.acctHolderReqOutAckNpciRefId = acctHolderReqOutAckNpciRefId;
	}
	public String getAcctHolderReqOutAckResData() {
		return acctHolderReqOutAckResData;
	}
	public void setAcctHolderReqOutAckResData(String acctHolderReqOutAckResData) {
		this.acctHolderReqOutAckResData = acctHolderReqOutAckResData;
	}
	public String getAcctHolderReqOutAckRespResult() {
		return acctHolderReqOutAckRespResult;
	}
	public void setAcctHolderReqOutAckRespResult(String acctHolderReqOutAckRespResult) {
		this.acctHolderReqOutAckRespResult = acctHolderReqOutAckRespResult;
	}

}
