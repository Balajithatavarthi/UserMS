package com.infosys.infytel.controller;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infosys.infytel.dto.BuyerDTO;
import com.infosys.infytel.dto.CartDTO;
import com.infosys.infytel.dto.LoginDetails;
import com.infosys.infytel.dto.SellerDTO;
import com.infosys.infytel.dto.WishlistDTO;
import com.infosys.infytel.dto.productDTO;
import com.infosys.infytel.service.UserService;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class UserController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserService userService;

	@Autowired
	private Environment environment;
	
	
	
	
	@GetMapping(value = "/seller/{sellerId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SellerDTO> findSeller(@PathVariable String sellerId) throws Exception{
		try {
			SellerDTO sl=userService.findSeller(sellerId);
			return new ResponseEntity<>(sl, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@PostMapping(value = "/seller/login",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sellerlogin(@Valid @RequestBody LoginDetails logindto) throws Exception{
		try {
			String sl=userService.sellerLogin(logindto.getEmail(),logindto.getPassword());
			return new ResponseEntity<>(sl, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@PostMapping(value = "/seller",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addSeller(@RequestBody SellerDTO sdto) throws Exception{
		try {
			String sd=userService.addseller(sdto);
			String successMessage = environment.getProperty("API.SELLER_INSERT_SUCCESS") + sd ;
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}	
	}
	@DeleteMapping(value = "/seller/{sellerId}")
	public ResponseEntity<String> deleteSeller(@PathVariable String sellerId) throws Exception {
		try {
			userService.deleteSeller(sellerId);
			String successMessage = environment.getProperty("API.SELLER_DELETE_SUCCESS");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@GetMapping(value = "/visitor/products",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<productDTO>> getallProducts() throws Exception{
		try {
			List<productDTO> sl=userService.getallproducts();
			return new ResponseEntity<>(sl, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@GetMapping(value = "/visitor/products/name={name}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<productDTO>> getProductByName(@PathVariable String name) throws Exception{
		try {
			List<productDTO> sl=userService.getproductsbyname(name);
			return new ResponseEntity<>(sl, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@GetMapping(value = "/visitor/products/category={category}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<productDTO>> getProductByCategory(@PathVariable String category) throws Exception{
		try {
			List<productDTO> sl=userService.getproductsbycategory(category);
			return new ResponseEntity<>(sl, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	
	@GetMapping(value = "/buyer/{buyerId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BuyerDTO> findBuyer(@PathVariable String buyerId) throws Exception{
		try {
			BuyerDTO sl=userService.findBuyer(buyerId);
			return new ResponseEntity<>(sl, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@PostMapping(value = "/buyer",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addBuyer(@RequestBody BuyerDTO sdto) throws Exception{
		try {
			String sd=userService.addbuyer(sdto);
			String successMessage = environment.getProperty("API.BUYER_INSERT_SUCCESS") + sd ;
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}	
	}
	@DeleteMapping(value = "/buyer/{buyerId}")
	public ResponseEntity<String> deleteBuyer(@PathVariable String buyerId) throws Exception {
		try {
			userService.deleteBuyer(buyerId);
			String successMessage = environment.getProperty("API.BUYER_DELETE_SUCCESS");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@PostMapping(value = "/buyer/wishlist")
	public ResponseEntity<String> addProducttoWishlist(@RequestBody WishlistDTO wdto) throws Exception {
		try {
			String md=userService.addtowishlist(wdto);
			String successMessage = environment.getProperty("API.PRODUCT_ADDED_SUCCESSFULLY")+md;
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@DeleteMapping(value = "/buyer/wishlist")
	public ResponseEntity<String> deleteProductfromWishlist(@RequestBody WishlistDTO wl) throws Exception {
		try {
			userService.removefromwishlist(wl);
			String successMessage = environment.getProperty("API.PRODUCT_DELETED_SUCCESSFULLY");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@GetMapping(value = "/buyer/{buyerId}/cart")
	public ResponseEntity<List<CartDTO>> getCartDetails(@PathVariable String buyerId){
		try {
			List<CartDTO> by=userService.allcartdetails(buyerId);
			return new ResponseEntity<>(by, HttpStatus.OK);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@PostMapping(value = "/buyer/{buyerId}/cart")
	public ResponseEntity<String> addtocart(@PathVariable String buyerId){
		try {
			String by=userService.additemstocart(buyerId);
			String successMessage = environment.getProperty("API.PRODUCT_ADDED_TO_CART_SUCCESSFULLY")+by;
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@DeleteMapping(value="/buyer/{buyerId}/cart")
	public ResponseEntity<String> deleteAllCartItem(@PathVariable String buyerId){
		try {
			String st=userService.deleteCartitems(buyerId);
			String successMessage = environment.getProperty("API.ITEMS_DELETED_FROM_CART_SUCCESSFULLY")+st;
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@PostMapping(value = "/buyer/cart")
	public ResponseEntity<String> addtocart(@RequestBody WishlistDTO wl){
		try {
			String by=userService.addtocartfromproducst(wl);
			String successMessage = environment.getProperty("API.PRODUCT_ADDED_TO_BUYERCART_SUCCESSFULLY")+by;
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@DeleteMapping(value="/buyer/{buyerId}/cart/{prodId}")
	public ResponseEntity<String> deleteAllCartItem(@PathVariable String buyerId,@PathVariable String prodId){
		try {
			userService.deleteCartitem(buyerId, prodId);
			String successMessage = environment.getProperty("API.PRODUCT_DELETED_FROM_BUYERCART_SUCCESSFULLY")+prodId;
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@PutMapping(value = "/buyer/{buyerId}/cart/{prodId}")
	public ResponseEntity<String> updateproductquantity(@PathVariable String buyerId,@PathVariable String prodId,@RequestBody Integer quantity){
		try {
			userService.updateProductQuantity(prodId, buyerId, quantity);
			String successMessage = environment.getProperty("API.PRODUCT_QUANTITY_UPDATED_SUCCESSFULLY");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
}
