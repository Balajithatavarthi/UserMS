package com.infosys.infytel.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient; 
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.infosys.infytel.dto.OrderDTO;


@FeignClient("ProductMS")
@CrossOrigin
public interface OrderFeign {
	@GetMapping(value = "api/order/{buyerId}")
	List<OrderDTO> findproductbyBuyerId(@PathVariable String buyerId);
}
