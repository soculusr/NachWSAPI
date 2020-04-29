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
@Table(name="AADHAAR_REQ_ACK_IN")
public class AadhaarReqDtlsAckIn {
	
	@Id
	@Column(name="UNIQUE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AADHAAR_REQ_ACK")
    @SequenceGenerator(sequenceName = "AADHAAR_REQ_ACK_SEQ", allocationSize = 1, name = "AADHAAR_REQ_ACK")
	private int uniqueAadhaarReqAckId;
	@Column(name="RES_TIMESTAMP")
	private String aadhaarAckRespTimestamp;
	@Column(name="REQ_ID")
	private int aadhaarAckReqId;
	@Column(name="NPCIREF_VALUE")
	private String aadhaarAckRespNpciRefId;
	@Column(name="ACK_CONTENT")
	private String aadhaarAckResData;
	@Column(name="ACK_RESULT")
	private String aadhaarAckRespResult;
	@Column(name="ERROR_CODE")
	private String aadhaarAckRespErrorCode;
	@Column(name="REJECTED_BY")
	private String aadhaarAckRespRejectedBy;
	
	
	
	public AadhaarReqDtlsAckIn() {
		
	}
	
	public int getUniqueAadhaarReqAckId() {
		return uniqueAadhaarReqAckId;
	}
	public void setUniqueAadhaarReqAckId(int uniqueAadhaarReqAckId) {
		this.uniqueAadhaarReqAckId = uniqueAadhaarReqAckId;
	}
	public String getAadhaarAckRespTimestamp() {
		return aadhaarAckRespTimestamp;
	}
	public void setAadhaarAckRespTimestamp(String aadhaarAckRespTimestamp) {
		this.aadhaarAckRespTimestamp = aadhaarAckRespTimestamp;
	}
	public int getAadhaarAckReqId() {
		return aadhaarAckReqId;
	}
	public void setAadhaarAckReqId(int aadhaarAckReqId) {
		this.aadhaarAckReqId = aadhaarAckReqId;
	}
	public String getAadhaarAckRespNpciRefId() {
		return aadhaarAckRespNpciRefId;
	}
	public void setAadhaarAckRespNpciRefId(String aadhaarAckRespNpciRefId) {
		this.aadhaarAckRespNpciRefId = aadhaarAckRespNpciRefId;
	}
	public String getAadhaarAckResData() {
		return aadhaarAckResData;
	}
	public void setAadhaarAckResData(String aadhaarAckResData) {
		this.aadhaarAckResData = aadhaarAckResData;
	}
	public String getAadhaarAckRespResult() {
		return aadhaarAckRespResult;
	}
	public void setAadhaarAckRespResult(String aadhaarAckRespResult) {
		this.aadhaarAckRespResult = aadhaarAckRespResult;
	}
	public String getAadhaarAckRespErrorCode() {
		return aadhaarAckRespErrorCode;
	}
	public void setAadhaarAckRespErrorCode(String aadhaarAckRespErrorCode) {
		this.aadhaarAckRespErrorCode = aadhaarAckRespErrorCode;
	}
	public String getAadhaarAckRespRejectedBy() {
		return aadhaarAckRespRejectedBy;
	}
	public void setAadhaarAckRespRejectedBy(String aadhaarAckRespRejectedBy) {
		this.aadhaarAckRespRejectedBy = aadhaarAckRespRejectedBy;
	}
	
	

}
