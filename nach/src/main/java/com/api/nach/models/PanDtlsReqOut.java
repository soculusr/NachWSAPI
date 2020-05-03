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
public class PanDtlsReqOut {
	
	@Id
	@Column(name="UNIQUE_ID")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCT_REQ_OUT_SEQ")
    @SequenceGenerator(sequenceName = "ACCT_REQ_OUT_SEQ", allocationSize = 1, name = "ACCT_REQ_OUT_SEQ")
	private int panDtlsUniqueReqOutId;
	@Column(name="service_name")
	private String panDtlsReqOutServiceName;
	@Column(name="REQ_TIMESTAMP")
	private String panDtlsReqOutTimestamp;
	@Column(name="REQ_ID")
	private int panDtlsReqOutId;
	@Column(name="NPCIREF_VALUE")
	private String panDtlsReqOutNpciRefId;
	@Column(name="REQ_CONTENT")
	private String panDtlsReqOutContent;
	
	
	
	public PanDtlsReqOut() {
		
	}
	
	public int getPanDtlsUniqueReqOutId() {
		return panDtlsUniqueReqOutId;
	}
	public void setPanDtlsUniqueReqOutId(int panDtlsUniqueReqOutId) {
		this.panDtlsUniqueReqOutId = panDtlsUniqueReqOutId;
	}
	public String getPanDtlsReqOutServiceName() {
		return panDtlsReqOutServiceName;
	}
	public void setPanDtlsReqOutServiceName(String panDtlsReqOutServiceName) {
		this.panDtlsReqOutServiceName = panDtlsReqOutServiceName;
	}
	public String getPanDtlsReqOutTimestamp() {
		return panDtlsReqOutTimestamp;
	}
	public void setPanDtlsReqOutTimestamp(String panDtlsReqOutTimestamp) {
		this.panDtlsReqOutTimestamp = panDtlsReqOutTimestamp;
	}
	public int getPanDtlsReqOutId() {
		return panDtlsReqOutId;
	}
	public void setPanDtlsReqOutId(int panDtlsReqOutId) {
		this.panDtlsReqOutId = panDtlsReqOutId;
	}
	public String getPanDtlsReqOutNpciRefId() {
		return panDtlsReqOutNpciRefId;
	}
	public void setPanDtlsReqOutNpciRefId(String panDtlsReqOutNpciRefId) {
		this.panDtlsReqOutNpciRefId = panDtlsReqOutNpciRefId;
	}
	public String getPanDtlsReqOutContent() {
		return panDtlsReqOutContent;
	}
	public void setPanDtlsReqOutContent(String panDtlsReqOutContent) {
		this.panDtlsReqOutContent = panDtlsReqOutContent;
	}

}
