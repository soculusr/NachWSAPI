package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.RequestPanDtls;
import com.api.nach.models.ResponseAcctHoldr;



public interface ResponseRepositoryAcctHoldr extends JpaRepository<ResponseAcctHoldr, Integer>{
	
	@Query(value = "SELECT * from acct_RES_data_OUT where service_name='GetAccHolder'", nativeQuery = true)
	  List<ResponseAcctHoldr> findByServiceName();

}
