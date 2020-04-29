package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.PanDtlsReqIn;
import com.api.nach.models.AcctStatusRespOut;



public interface AcctStatusRespOutRepository extends JpaRepository<AcctStatusRespOut, Integer>{
	
	@Query(value = "SELECT * from acct_RES_data_OUT where service_name='GetAccStatus'", nativeQuery = true)
	  List<AcctStatusRespOut> findByServiceName();

}
