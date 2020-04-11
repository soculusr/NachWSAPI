package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.Request1;
import com.api.nach.models.Response1;

public interface ResponseRepository1 extends JpaRepository<Response1, Integer>{
	
	@Query(value = "SELECT * from response where service_name='GetPanDtls'", nativeQuery = true)
	  List<Request1> findByServiceName();

}
