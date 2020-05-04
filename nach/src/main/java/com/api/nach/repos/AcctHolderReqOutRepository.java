package com.api.nach.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.AcctHolderReqOut;

public interface AcctHolderReqOutRepository extends JpaRepository<AcctHolderReqOut, Integer>{
	
	@Query(value = "SELECT ACCT_UNIQUE_REQ_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
	int getUniqueReqId();

}
