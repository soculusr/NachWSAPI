package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.Request1;
import com.api.nach.models.Response2;



public interface ResponseRepository2 extends JpaRepository<Response2, Integer>{
	
	@Query(value = "SELECT * from response where service_name='GetAccHolder'", nativeQuery = true)
	  List<Request1> findByServiceName();

}
