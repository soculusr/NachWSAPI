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
public class Request3 {
	
	@Id
	@Column(name="unique_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id3;
	@Column(name="service_name")
	private String servicename3;
	@Column(name="request_timestamp")
	private String rqsttimestamp3;
	@Column(name="request_id")
	private String rqstid3;
	@Column(name="npciref_id")
	private String npcirefid3;
	@Column(name="request_content")
	private String rqstcontent3;
	
	
	
	public Request3() {

	}
	
	
	
	public int getId() {
		return id3;
	}
	public void setId(int id) {
		this.id3 = id;
	}
	public String getServicename() {
		return servicename3;
	}
	public void setServicename(String servicename) {
		this.servicename3 = servicename;
	}
	public String getRqsttimestamp() {
		return rqsttimestamp3;
	}
	public void setRqsttimestamp(String rqsttimestamp) {
		this.rqsttimestamp3 = rqsttimestamp;
	}
	public String getRqstid() {
		return rqstid3;
	}
	public void setRqstid(String rqstid) {
		this.rqstid3 = rqstid;
	}
	public String getNpcirefid() {
		return npcirefid3;
	}
	public void setNpcirefid(String npcirefid) {
		this.npcirefid3 = npcirefid;
	}
	public String getRqstcontent() {
		return rqstcontent3;
	}
	public void setRqstcontent(String rqstcontent) {
		this.rqstcontent3 = rqstcontent;
	}

}
