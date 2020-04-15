package com.api.nach.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="request")
public class RequestPanDtls {
	
	@Id
	@Column(name="unique_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int panDtlsUniqueReqId;
	@Column(name="service_name")
	private String panDtlsServiceName;
	@Column(name="request_timestamp")
	private String panDtlsReqTimestamp;
	@Column(name="request_id")
	private String panDtlsReqId;
	@Column(name="npciref_id")
	private String panDtlsNpciRefId;
	@Column(name="request_content")
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
