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
public class PanDtlsReqOutAckIn {
	
	@Id
	@Column(name="UNIQUE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCT_REQ_OUT_ACK")
    @SequenceGenerator(sequenceName = "ACCT_REQ_OUT_ACK_SEQ", allocationSize = 1, name = "ACCT_REQ_OUT_ACK")
	private int uniquePanDtlsReqAckId;
	@Column(name="RES_TIMESTAMP")
	private String panDtlsReqOutAckRespTimestamp;
	@Column(name="REQ_ID")
	private int panDtlsReqOutAckReqId;
	@Column(name="NPCIREF_VALUE")
	private String panDtlsReqOutAckNpciRefId;
	@Column(name="ACK_CONTENT")
	private String panDtlsReqOutAckResData;
	@Column(name="ACK_RESULT")
	private String panDtlsReqOutAckRespResult;
	@Column(name="ERROR_CODE")
	private String panDtlsReqOutAckRespErrorCode;
	@Column(name="REJECTED_BY")
	private String panDtlsReqOutAckRespRejectedBy;
	
	
	
	
	public PanDtlsReqOutAckIn() {
		
	}
	public int getUniquePanDtlsReqAckId() {
		return uniquePanDtlsReqAckId;
	}
	public void setUniquePanDtlsReqAckId(int uniquePanDtlsReqAckId) {
		this.uniquePanDtlsReqAckId = uniquePanDtlsReqAckId;
	}
	public String getPanDtlsReqOutAckRespTimestamp() {
		return panDtlsReqOutAckRespTimestamp;
	}
	public void setPanDtlsReqOutAckRespTimestamp(String panDtlsReqOutAckRespTimestamp) {
		this.panDtlsReqOutAckRespTimestamp = panDtlsReqOutAckRespTimestamp;
	}
	public int getPanDtlsReqOutAckReqId() {
		return panDtlsReqOutAckReqId;
	}
	public void setPanDtlsReqOutAckReqId(int panDtlsReqOutAckReqId) {
		this.panDtlsReqOutAckReqId = panDtlsReqOutAckReqId;
	}
	public String getPanDtlsReqOutAckNpciRefId() {
		return panDtlsReqOutAckNpciRefId;
	}
	public void setPanDtlsReqOutAckNpciRefId(String panDtlsReqOutAckNpciRefId) {
		this.panDtlsReqOutAckNpciRefId = panDtlsReqOutAckNpciRefId;
	}
	public String getPanDtlsReqOutAckResData() {
		return panDtlsReqOutAckResData;
	}
	public void setPanDtlsReqOutAckResData(String panDtlsReqOutAckResData) {
		this.panDtlsReqOutAckResData = panDtlsReqOutAckResData;
	}
	public String getPanDtlsReqOutAckRespResult() {
		return panDtlsReqOutAckRespResult;
	}
	public void setPanDtlsReqOutAckRespResult(String panDtlsReqOutAckRespResult) {
		this.panDtlsReqOutAckRespResult = panDtlsReqOutAckRespResult;
	}
	public String getPanDtlsReqOutAckRespErrorCode() {
		return panDtlsReqOutAckRespErrorCode;
	}
	public void setPanDtlsReqOutAckRespErrorCode(String panDtlsReqOutAckRespErrorCode) {
		this.panDtlsReqOutAckRespErrorCode = panDtlsReqOutAckRespErrorCode;
	}
	public String getPanDtlsReqOutAckRespRejectedBy() {
		return panDtlsReqOutAckRespRejectedBy;
	}
	public void setPanDtlsReqOutAckRespRejectedBy(String panDtlsReqOutAckRespRejectedBy) {
		this.panDtlsReqOutAckRespRejectedBy = panDtlsReqOutAckRespRejectedBy;
	}
	

}
