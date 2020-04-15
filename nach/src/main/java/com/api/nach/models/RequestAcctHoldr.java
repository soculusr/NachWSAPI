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
public class RequestAcctHoldr {
	
	@Id
	@Column(name="unique_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id2;
	@Column(name="service_name")
	private String servicename2;
	@Column(name="request_timestamp")
	private String rqsttimestamp2;
	@Column(name="request_id")
	private String rqstid2;
	@Column(name="npciref_id")
	private String npcirefid2;
	@Column(name="request_content")
	private String rqstcontent2;
	
	
	
	public RequestAcctHoldr() {

	}
	
	
	
	public int getId() {
		return id2;
	}
	public void setId(int id) {
		this.id2 = id;
	}
	public String getServicename() {
		return servicename2;
	}
	public void setServicename(String servicename) {
		this.servicename2 = servicename;
	}
	public String getRqsttimestamp() {
		return rqsttimestamp2;
	}
	public void setRqsttimestamp(String rqsttimestamp) {
		this.rqsttimestamp2 = rqsttimestamp;
	}
	public String getRqstid() {
		return rqstid2;
	}
	public void setRqstid(String rqstid) {
		this.rqstid2 = rqstid;
	}
	public String getNpcirefid() {
		return npcirefid2;
	}
	public void setNpcirefid(String npcirefid) {
		this.npcirefid2 = npcirefid;
	}
	public String getRqstcontent() {
		return rqstcontent2;
	}
	public void setRqstcontent(String rqstcontent) {
		this.rqstcontent2 = rqstcontent;
	}

}
