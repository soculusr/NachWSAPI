package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.PrevIinDtls;



public interface PrevIinRepository extends JpaRepository<PrevIinDtls, Integer>{
	
	@Query(value = "select PREV_IIN_NAMES from PREV_IIN_LIST", nativeQuery = true)
	  List<String> findAllIin();

}
