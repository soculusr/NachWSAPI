package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.RequestPanDtls;
import com.api.nach.models.ResponseAcctStatus;



public interface ResponseRepositoryAcctStatus extends JpaRepository<ResponseAcctStatus, Integer>{
	
	@Query(value = "SELECT * from acct_RES_data_OUT where service_name='GetAccStatus'", nativeQuery = true)
	  List<ResponseAcctStatus> findByServiceName();

}
