package com.api.nach.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="response")
public class Response2 {
	
	@Id
	@Column(name="unique_id")
	private int id;
	@Column(name="service_name")
	private String servicename;
	@Column(name="response_timestamp")
	private String resptimestamp;
	@Column(name="request_id")
	private String rqstid;
	@Column(name="npciref_id")
	private String npcirefid;
	@Column(name="response_content")
	private String respcontent;
	
	
	
	public Response2() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	public String getResptimestamp() {
		return resptimestamp;
	}
	public void setResptimestamp(String resptimestamp) {
		this.resptimestamp = resptimestamp;
	}
	public String getRqstid() {
		return rqstid;
	}
	public void setRqstid(String rqstid) {
		this.rqstid = rqstid;
	}
	public String getNpcirefid() {
		return npcirefid;
	}
	public void setNpcirefid(String npcirefid) {
		this.npcirefid = npcirefid;
	}
	public String getRespcontent() {
		return respcontent;
	}
	public void setRespcontent(String respcontent) {
		this.respcontent = respcontent;
	}
	
	
	
	

}
