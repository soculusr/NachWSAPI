package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;

import com.api.nach.models.PanDtlsReqIn;



public interface PanDtlsReqInRepository extends JpaRepository<PanDtlsReqIn, Integer>{
	
	@Query(value = "SELECT * from acct_req_data_in where service_name='GetPanDtls'", nativeQuery = true)
	  List<PanDtlsReqIn> findByServiceName();
	
	@Query(value = "SELECT unique_id from acct_req_data_in where REQ_ID=?1", nativeQuery = true)
	int findByReqId(String rqstId);

}
