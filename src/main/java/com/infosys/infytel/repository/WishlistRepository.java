package com.infosys.infytel.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.infytel.entity.Cart;
import com.infosys.infytel.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, String> {
	


}
