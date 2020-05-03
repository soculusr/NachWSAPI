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
@Table(name="DEST_BANKS_LIST")
public class DestBankListDtls {
	
	@Id
	@Column(name="SR_NO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEST_BANK")
    @SequenceGenerator(sequenceName = "DEST_BANK_SEQ", allocationSize = 1, name = "DEST_BANK")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uniqueueDestBankId;
	
	
	
	public DestBankListDtls() {
		
	}
	
	public int getUniqueueDestBankId() {
		return uniqueueDestBankId;
	}
	public void setUniqueueDestBankId(int uniqueueDestBankId) {
		this.uniqueueDestBankId = uniqueueDestBankId;
	}
	public String getDestBankName() {
		return destBankName;
	}
	public void setDestBankName(String destBankName) {
		this.destBankName = destBankName;
	}
	@Column(name="DEST_BANK_NAMES")
	private String destBankName;

}
