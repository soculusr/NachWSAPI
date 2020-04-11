package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.Request1;
import com.api.nach.models.Response3;



public interface ResponseRepository3 extends JpaRepository<Response3, Integer>{
	
	@Query(value = "SELECT * from response where service_name='GetAccStatus'", nativeQuery = true)
	  List<Request1> findByServiceName();

}
