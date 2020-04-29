package com.api.nach.repos;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.AadhaarReqDtlsOut;



public interface AadhaarReqDtlsOutRepository extends JpaRepository<AadhaarReqDtlsOut, Integer>{
	
	@Query(value = "SELECT HR.AADHAAR_REC_REF_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
	int getRecRefNo();
	
	@Query(value = "SELECT HR.AADHAAR_UNIQUE_REQ_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
	int getUniqueReqId();

}
