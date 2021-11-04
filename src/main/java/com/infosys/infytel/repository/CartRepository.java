package com.infosys.infytel.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.infytel.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, String> {

}
