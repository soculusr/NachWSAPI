package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.Request1;
import com.api.nach.models.Request3;



public interface RequestRepository3 extends JpaRepository<Request3, Integer>{
	
	@Query(value = "SELECT * from request where service_name='GetAccStatus'", nativeQuery = true)
	  List<Request3> findByServiceName();
	
	@Query(value = "SELECT unique_id from request where request_id=?1", nativeQuery = true)
	int findByReqId(String rqstId);

}
