package com.infosys.infytel.controller;


import java.util.List; 

import org.springframework.cloud.openfeign.FeignClient; 
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.infosys.infytel.dto.productDTO;
@FeignClient("ProductMS")
@CrossOrigin
public interface ProductFeign {
	@GetMapping(value="api/products/{prodId}")
	productDTO getProduct(@PathVariable("prodId") String prodId);
	@GetMapping(value="api/products")
	List<productDTO> getAllProduct();
	@GetMapping(value = "api/products/name={prodName}")
	List<productDTO> findproductbyname(@PathVariable String prodName);
	@GetMapping(value = "api/products/category={category}")
	List<productDTO> findproductbycategory(@PathVariable String category);
}
