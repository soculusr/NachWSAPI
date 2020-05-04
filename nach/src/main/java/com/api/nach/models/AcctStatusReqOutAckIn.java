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
public class AcctStatusReqOutAckIn {
	
	@Id
	@Column(name="UNIQUE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCT_STATUS_REQ_OUT_ACK")
    @SequenceGenerator(sequenceName = "ACCT_REQ_OUT_ACK_SEQ", allocationSize = 1, name = "ACCT_STATUS_REQ_OUT_ACK")
	private int uniqueAcctStatusReqAckId;
	@Column(name="RES_TIMESTAMP")
	private String acctStatusReqOutAckRespTimestamp;
	@Column(name="REQ_ID")
	private int acctStatusReqOutAckReqId;
	@Column(name="NPCIREF_VALUE")
	private String acctStatusReqOutAckNpciRefId;
	@Column(name="ACK_CONTENT")
	private String acctStatusReqOutAckResData;
	@Column(name="ACK_RESULT")
	private String acctStatusReqOutAckRespResult;
	@Column(name="ERROR_CODE")
	private String acctStatusReqOutAckRespErrorCode;
	@Column(name="REJECTED_BY")
	private String acctStatusReqOutAckRespRejectedBy;
	
	
	
	
	public AcctStatusReqOutAckIn() {
		
	}
	public int getUniqueAcctStatusReqAckId() {
		return uniqueAcctStatusReqAckId;
	}
	public void setUniqueAcctStatusReqAckId(int uniqueAcctStatusReqAckId) {
		this.uniqueAcctStatusReqAckId = uniqueAcctStatusReqAckId;
	}
	public String getAcctStatusReqOutAckRespTimestamp() {
		return acctStatusReqOutAckRespTimestamp;
	}
	public void setAcctStatusReqOutAckRespTimestamp(String acctStatusReqOutAckRespTimestamp) {
		this.acctStatusReqOutAckRespTimestamp = acctStatusReqOutAckRespTimestamp;
	}
	public int getAcctStatusReqOutAckReqId() {
		return acctStatusReqOutAckReqId;
	}
	public void setAcctStatusReqOutAckReqId(int acctStatusReqOutAckReqId) {
		this.acctStatusReqOutAckReqId = acctStatusReqOutAckReqId;
	}
	public String getAcctStatusReqOutAckNpciRefId() {
		return acctStatusReqOutAckNpciRefId;
	}
	public void setAcctStatusReqOutAckNpciRefId(String acctStatusReqOutAckNpciRefId) {
		this.acctStatusReqOutAckNpciRefId = acctStatusReqOutAckNpciRefId;
	}
	public String getAcctStatusReqOutAckResData() {
		return acctStatusReqOutAckResData;
	}
	public void setAcctStatusReqOutAckResData(String acctStatusReqOutAckResData) {
		this.acctStatusReqOutAckResData = acctStatusReqOutAckResData;
	}
	public String getAcctStatusReqOutAckRespResult() {
		return acctStatusReqOutAckRespResult;
	}
	public void setAcctStatusReqOutAckRespResult(String acctStatusReqOutAckRespResult) {
		this.acctStatusReqOutAckRespResult = acctStatusReqOutAckRespResult;
	}
	public String getAcctStatusReqOutAckRespErrorCode() {
		return acctStatusReqOutAckRespErrorCode;
	}
	public void setAcctStatusReqOutAckRespErrorCode(String acctStatusReqOutAckRespErrorCode) {
		this.acctStatusReqOutAckRespErrorCode = acctStatusReqOutAckRespErrorCode;
	}
	public String getAcctStatusReqOutAckRespRejectedBy() {
		return acctStatusReqOutAckRespRejectedBy;
	}
	public void setAcctStatusReqOutAckRespRejectedBy(String acctStatusReqOutAckRespRejectedBy) {
		this.acctStatusReqOutAckRespRejectedBy = acctStatusReqOutAckRespRejectedBy;
	}

}
