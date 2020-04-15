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
@Table(name="response")
public class ResponsePanDtls {
	
	@Id
	@Column(name="unique_id")
	private int panDtlsUniqueRespId;
	@Column(name="service_name")
	private String panDtlsRespServiceName;
	@Column(name="response_timestamp")
	private String panDtlsRespTimestamp;
	@Column(name="request_id")
	private String panDtlsRespReqId;
	@Column(name="npciref_id")
	private String panDtlsRespNpciRefId;
	@Column(name="response_content")
	private String panDtlsRespContent;
	
	
	
	public ResponsePanDtls() {
	
	}
	
	public int getId() {
		return panDtlsUniqueRespId;
	}
	public void setId(int id) {
		this.panDtlsUniqueRespId = id;
	}
	public String getServicename() {
		return panDtlsRespServiceName;
	}
	public void setServicename(String servicename) {
		this.panDtlsRespServiceName = servicename;
	}
	public String getResptimestamp() {
		return panDtlsRespTimestamp;
	}
	public void setResptimestamp(String resptimestamp) {
		this.panDtlsRespTimestamp = resptimestamp;
	}
	public String getRqstid() {
		return panDtlsRespReqId;
	}
	public void setRqstid(String rqstid) {
		this.panDtlsRespReqId = rqstid;
	}
	public String getNpcirefid() {
		return panDtlsRespNpciRefId;
	}
	public void setNpcirefid(String npcirefid) {
		this.panDtlsRespNpciRefId = npcirefid;
	}
	public String getRespcontent() {
		return panDtlsRespContent;
	}
	public void setRespcontent(String respcontent) {
		this.panDtlsRespContent = respcontent;
	}
	
	

}
