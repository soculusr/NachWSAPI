package com.api.nach.models;

import java.util.Date;

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
@Table(name="AADHAAR_REQ_DATA_OUT")
public class AadhaarReqDtls {
	
	@Id
	@Column(name="UNIQUE_ID")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AADHAAR_REQ")
    @SequenceGenerator(sequenceName = "AADHAAR_REQ_OUT_SEQ", allocationSize = 1, name = "AADHAAR_REQ")
	private int uniqueAadhaarReqId;
	@Column(name="SERVICE_NAME")
	private String aadhaarServiceName;
	@Column(name="REQ_TIMESTAMP")
	private String aadhaarReqTimestamp;
	@Column(name="REQ_ID")
	private int aadhaarReqId;
	@Column(name="REQ_CONTENT")
	private String aadhaarReqData;
	
	
	
	
	public AadhaarReqDtls() {
		
	}
	public int getUniqueAadhaarReqId() {
		return uniqueAadhaarReqId;
	}
	public void setUniqueAadhaarReqId(int uniqueAadhaarReqId) {
		this.uniqueAadhaarReqId = uniqueAadhaarReqId;
	}
	public String getAadhaarServiceName() {
		return aadhaarServiceName;
	}
	public void setAadhaarServiceName(String aadhaarServiceName) {
		this.aadhaarServiceName = aadhaarServiceName;
	}
	public String getAadhaarReqTimestamp() {
		return aadhaarReqTimestamp;
	}
	public void setAadhaarReqTimestamp(String aadhaarReqTimestamp) {
		this.aadhaarReqTimestamp = aadhaarReqTimestamp;
	}
	public int getAadhaarReqId() {
		return aadhaarReqId;
	}
	public void setAadhaarReqId(int aadhaarReqId) {
		this.aadhaarReqId = aadhaarReqId;
	}
	public String getAadhaarReqData() {
		return aadhaarReqData;
	}
	public void setAadhaarReqData(String aadhaarReqData) {
		this.aadhaarReqData = aadhaarReqData;
	}
	
	

}
