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
public class Response3 {
	
	
	@Id
	@Column(name="unique_id")
	private int id6;
	@Column(name="service_name")
	private String servicename6;
	@Column(name="response_timestamp")
	private String resptimestamp6;
	@Column(name="request_id")
	private String rqstid6;
	@Column(name="npciref_id")
	private String npcirefid6;
	@Column(name="response_content")
	private String respcontent6;
	
	
	public Response3() {
		
	}
	public int getId() {
		return id6;
	}
	public void setId(int id) {
		this.id6 = id;
	}
	public String getServicename() {
		return servicename6;
	}
	public void setServicename(String servicename) {
		this.servicename6 = servicename;
	}
	public String getResptimestamp() {
		return resptimestamp6;
	}
	public void setResptimestamp(String resptimestamp) {
		this.resptimestamp6 = resptimestamp;
	}
	public String getRqstid() {
		return rqstid6;
	}
	public void setRqstid(String rqstid) {
		this.rqstid6 = rqstid;
	}
	public String getNpcirefid() {
		return npcirefid6;
	}
	public void setNpcirefid(String npcirefid) {
		this.npcirefid6 = npcirefid;
	}
	public String getRespcontent() {
		return respcontent6;
	}
	public void setRespcontent(String respcontent) {
		this.respcontent6 = respcontent;
	}

}
