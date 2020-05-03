package com.api.nach.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.nach.models.DestBankListDtls;

public interface DestBankListRepository extends JpaRepository<DestBankListDtls, Integer>{
	
	@Query(value = "select DEST_BANK_NAMES from DEST_BANKS_LIST", nativeQuery = true)
	  List<String> findAllIin();
	
	

}
