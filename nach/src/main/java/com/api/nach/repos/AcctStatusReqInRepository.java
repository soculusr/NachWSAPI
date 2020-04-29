package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.PanDtlsReqIn;
import com.api.nach.models.AcctStatusReqIn;



public interface AcctStatusReqInRepository extends JpaRepository<AcctStatusReqIn, Integer>{
	
	@Query(value = "SELECT * from acct_req_data_in where service_name='GetAccStatus'", nativeQuery = true)
	  List<AcctStatusReqIn> findByServiceName();
	
	@Query(value = "SELECT unique_id from acct_req_data_in where REQ_ID=?1", nativeQuery = true)
	int findByReqId(String rqstId);

}
