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
@Table(name="PREV_IIN_LIST")
public class PrevIinListDtls {
	
	@Id
	@Column(name="SR_NO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PREV_IIN")
    @SequenceGenerator(sequenceName = "PREV_IIN_SEQ", allocationSize = 1, name = "PREV_IIN")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uniquePrevIinId;
	@Column(name="PREV_IIN_NAMES")
	private String prevInnName;
	
	public PrevIinListDtls() {
		
	}
	public int getUniquePrevIinId() {
		return uniquePrevIinId;
	}
	public void setUniquePrevIinId(int uniquePrevIinId) {
		this.uniquePrevIinId = uniquePrevIinId;
	}
	public String getPrevInnName() {
		return prevInnName;
	}
	public void setPrevInnName(String prevInnName) {
		this.prevInnName = prevInnName;
	}
	
	

}
