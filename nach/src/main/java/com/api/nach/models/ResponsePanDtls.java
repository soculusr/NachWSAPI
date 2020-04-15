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
	private int id4;
	@Column(name="service_name")
	private String servicename4;
	@Column(name="response_timestamp")
	private String resptimestamp4;
	@Column(name="request_id")
	private String rqstid4;
	@Column(name="npciref_id")
	private String npcirefid4;
	@Column(name="response_content")
	private String respcontent4;
	
	
	
	public ResponsePanDtls() {
	
	}
	
	public int getId() {
		return id4;
	}
	public void setId(int id) {
		this.id4 = id;
	}
	public String getServicename() {
		return servicename4;
	}
	public void setServicename(String servicename) {
		this.servicename4 = servicename;
	}
	public String getResptimestamp() {
		return resptimestamp4;
	}
	public void setResptimestamp(String resptimestamp) {
		this.resptimestamp4 = resptimestamp;
	}
	public String getRqstid() {
		return rqstid4;
	}
	public void setRqstid(String rqstid) {
		this.rqstid4 = rqstid;
	}
	public String getNpcirefid() {
		return npcirefid4;
	}
	public void setNpcirefid(String npcirefid) {
		this.npcirefid4 = npcirefid;
	}
	public String getRespcontent() {
		return respcontent4;
	}
	public void setRespcontent(String respcontent) {
		this.respcontent4 = respcontent;
	}
	
	

}
