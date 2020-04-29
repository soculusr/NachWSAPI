package com.api.nach.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="AADHAAR_RES_DATA_IN")
public class AadhaarRespDtls {
	
	@Id
	@Column(name="UNIQUE_ID")
	private int aadhaarRespUniqueId;
	@Column(name="SERVICE_NAME")
	private String aadhaarRespServiceName;
	@Column(name="RES_TIMESTAMP")
	private String aadhaarRespTimestamp;
	@Column(name="REQ_ID")
	private String aadhaarRespReqId;
	@Column(name="NPCIREF_VALUE")
	private String aadhaarRespNpciRefId;
	@Column(name="RES_CONTENT")
	private String aadhaarRespContent;

}
