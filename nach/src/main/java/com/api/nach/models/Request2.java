package com.api.nach.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="request")
public class Request2 {
	
	@Id
	@Column(name="unique_id")
	private int id;
	@Column(name="service_name")
	private String servicename;
	@Column(name="request_timestamp")
	private String rqsttimestamp;
	@Column(name="request_id")
	private String rqstid;
	@Column(name="npciref_id")
	private String npcirefid;
	@Column(name="request_content")
	private String rqstcontent;
	
	
	
	public Request2() {

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
	public String getRqsttimestamp() {
		return rqsttimestamp;
	}
	public void setRqsttimestamp(String rqsttimestamp) {
		this.rqsttimestamp = rqsttimestamp;
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
	public String getRqstcontent() {
		return rqstcontent;
	}
	public void setRqstcontent(String rqstcontent) {
		this.rqstcontent = rqstcontent;
	}

}
