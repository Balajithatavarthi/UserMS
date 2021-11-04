package com.infosys.infytel.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.infytel.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, String> {

	Seller findTopByOrderBySellerIdDesc();
	Seller findByEmail(String Email);

	
}
