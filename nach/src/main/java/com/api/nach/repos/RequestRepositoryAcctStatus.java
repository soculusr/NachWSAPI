package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.RequestPanDtls;
import com.api.nach.models.RequestAcctStatus;



public interface RequestRepositoryAcctStatus extends JpaRepository<RequestAcctStatus, Integer>{
	
	@Query(value = "SELECT * from acct_req_data_in where service_name='GetAccStatus'", nativeQuery = true)
	  List<RequestAcctStatus> findByServiceName();
	
	@Query(value = "SELECT unique_id from acct_req_data_in where REQ_ID=?1", nativeQuery = true)
	int findByReqId(String rqstId);

}
