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
public class RequestPanDtls {
	
	@Id
	@Column(name="UNIQUE_ID")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCT_REQ_SEQ")
    @SequenceGenerator(sequenceName = "ACCT_REQ_IN_SEQ", allocationSize = 1, name = "ACCT_REQ_SEQ")
	private int panDtlsUniqueReqId;
	@Column(name="service_name")
	private String panDtlsServiceName;
	@Column(name="REQ_TIMESTAMP")
	private String panDtlsReqTimestamp;
	@Column(name="REQ_ID")
	private String panDtlsReqId;
	@Column(name="NPCIREF_VALUE")
	private String panDtlsNpciRefId;
	@Column(name="REQ_CONTENT")
	private String panDtlsReqContent;
	
	
	
	public RequestPanDtls() {

	}
	
	
	
	public int getId() {
		return panDtlsUniqueReqId;
	}
	public void setId(int id) {
		this.panDtlsUniqueReqId = id;
	}
	public String getServicename() {
		return panDtlsServiceName;
	}
	public void setServicename(String servicename) {
		this.panDtlsServiceName = servicename;
	}
	public String getRqsttimestamp() {
		return panDtlsReqTimestamp;
	}
	public void setRqsttimestamp(String rqsttimestamp) {
		this.panDtlsReqTimestamp = rqsttimestamp;
	}
	public String getRqstid() {
		return panDtlsReqId;
	}
	public void setRqstid(String rqstid) {
		this.panDtlsReqId = rqstid;
	}
	public String getNpcirefid() {
		return panDtlsNpciRefId;
	}
	public void setNpcirefid(String npcirefid) {
		this.panDtlsNpciRefId = npcirefid;
	}
	public String getRqstcontent() {
		return panDtlsReqContent;
	}
	public void setRqstcontent(String rqstcontent) {
		this.panDtlsReqContent = rqstcontent;
	}

}
