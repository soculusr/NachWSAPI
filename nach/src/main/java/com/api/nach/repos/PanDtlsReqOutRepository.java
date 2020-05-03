package com.api.nach.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.PanDtlsReqOut;

public interface PanDtlsReqOutRepository extends JpaRepository<PanDtlsReqOut, Integer>{
	
	@Query(value = "SELECT ACCT_UNIQUE_REQ_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
	int getUniqueReqId();

}
