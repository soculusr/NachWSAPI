package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.RequestPanDtls;
import com.api.nach.models.RequestAcctHoldr;



public interface RequestRepositoryAcctHoldr extends JpaRepository<RequestAcctHoldr, Integer>{
	
	@Query(value = "SELECT * from request where service_name='GetAccHolder'", nativeQuery = true)
	  List<RequestAcctHoldr> findByServiceName();
	
	@Query(value = "SELECT unique_id from request where request_id=?1", nativeQuery = true)
	int findByReqId(String rqstId);

}
