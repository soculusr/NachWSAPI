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
public class ResponseAcctHoldr {
	
	@Id
	@Column(name="unique_id")
	private int id5;
	@Column(name="service_name")
	private String servicename5;
	@Column(name="response_timestamp")
	private String resptimestamp5;
	@Column(name="request_id")
	private String rqstid5;
	@Column(name="npciref_id")
	private String npcirefid5;
	@Column(name="response_content")
	private String respcontent5;
	
	
	
	public ResponseAcctHoldr() {
		
	}
	
	public int getId() {
		return id5;
	}
	public void setId(int id) {
		this.id5 = id;
	}
	public String getServicename() {
		return servicename5;
	}
	public void setServicename(String servicename) {
		this.servicename5 = servicename;
	}
	public String getResptimestamp() {
		return resptimestamp5;
	}
	public void setResptimestamp(String resptimestamp) {
		this.resptimestamp5 = resptimestamp;
	}
	public String getRqstid() {
		return rqstid5;
	}
	public void setRqstid(String rqstid) {
		this.rqstid5 = rqstid;
	}
	public String getNpcirefid() {
		return npcirefid5;
	}
	public void setNpcirefid(String npcirefid) {
		this.npcirefid5 = npcirefid;
	}
	public String getRespcontent() {
		return respcontent5;
	}
	public void setRespcontent(String respcontent) {
		this.respcontent5 = respcontent;
	}
	
	
	
	

}
