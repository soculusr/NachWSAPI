package com.api.nach.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="acct_RES_data_OUT")
public class PanDtlsRespOut {
	
	@Id
	@Column(name="UNIQUE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCT_PAN_RES_SEQ")
    @SequenceGenerator(sequenceName = "ACCT_RES_OUT_SEQ", allocationSize = 1, name = "ACCT_PAN_RES_SEQ")
	private int panDtlsUniqueRespId;
	@Column(name="SERVICE_NAME")
	private String panDtlsRespServiceName;
	@Column(name="RES_TIMESTAMP")
	private String panDtlsRespTimestamp;
	@Column(name="REQ_ID")
	private String panDtlsRespReqId;
	@Column(name="NPCIREF_VALUE")
	private String panDtlsRespNpciRefId;
	@Column(name="RES_CONTENT")
	private String panDtlsRespContent;
	
	
	
	public PanDtlsRespOut() {
	
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
