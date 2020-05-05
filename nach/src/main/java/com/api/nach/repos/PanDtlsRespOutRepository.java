package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.PanDtlsReqIn;
import com.api.nach.models.PanDtlsRespOut;

public interface PanDtlsRespOutRepository extends JpaRepository<PanDtlsRespOut, Integer>{
	
	@Query(value = "SELECT * from acct_RES_data_OUT where service_name='GetPanDtls'", nativeQuery = true)
	  List<PanDtlsRespOut> findByServiceName();
	
	@Query(value = "SELECT ACCT_RES_OUT_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
	int getUniqueReqId();

}
