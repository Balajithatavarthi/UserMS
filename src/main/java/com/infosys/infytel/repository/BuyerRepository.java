package com.infosys.infytel.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.infytel.entity.Buyer;
import com.infosys.infytel.entity.Seller;

public interface BuyerRepository extends JpaRepository<Buyer, String> {
	Buyer findTopByOrderByBuyerIdDesc();
	Buyer findByEmail(String Email);
}
