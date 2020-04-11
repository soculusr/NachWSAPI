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
public class Request1 {
	
	@Id
	@Column(name="unique_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id1;
	@Column(name="service_name")
	private String servicename1;
	@Column(name="request_timestamp")
	private String rqsttimestamp1;
	@Column(name="request_id")
	private String rqstid1;
	@Column(name="npciref_id")
	private String npcirefid1;
	@Column(name="request_content")
	private String rqstcontent1;
	
	
	
	public Request1() {

	}
	
	
	
	public int getId() {
		return id1;
	}
	public void setId(int id) {
		this.id1 = id;
	}
	public String getServicename() {
		return servicename1;
	}
	public void setServicename(String servicename) {
		this.servicename1 = servicename;
	}
	public String getRqsttimestamp() {
		return rqsttimestamp1;
	}
	public void setRqsttimestamp(String rqsttimestamp) {
		this.rqsttimestamp1 = rqsttimestamp;
	}
	public String getRqstid() {
		return rqstid1;
	}
	public void setRqstid(String rqstid) {
		this.rqstid1 = rqstid;
	}
	public String getNpcirefid() {
		return npcirefid1;
	}
	public void setNpcirefid(String npcirefid) {
		this.npcirefid1 = npcirefid;
	}
	public String getRqstcontent() {
		return rqstcontent1;
	}
	public void setRqstcontent(String rqstcontent) {
		this.rqstcontent1 = rqstcontent;
	}

}
