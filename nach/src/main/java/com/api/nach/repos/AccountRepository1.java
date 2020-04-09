package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;

import com.api.nach.models.Request1;



public interface AccountRepository1 extends JpaRepository<Request1, Integer>{
	
	@Query(value = "SELECT * from request where service_name='GetPanDtls'", nativeQuery = true)
	  List<Request1> findByServiceName();

}
