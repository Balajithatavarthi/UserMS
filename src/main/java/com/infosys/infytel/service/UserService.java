package com.infosys.infytel.service;

import java.util.ArrayList; 

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.infytel.controller.OrderFeign;
import com.infosys.infytel.controller.ProductFeign;
import com.infosys.infytel.dto.BuyerDTO;
import com.infosys.infytel.dto.CartDTO;
import com.infosys.infytel.dto.OrderDTO;
import com.infosys.infytel.dto.SellerDTO;
import com.infosys.infytel.dto.WishlistDTO;
import com.infosys.infytel.dto.productDTO;
import com.infosys.infytel.entity.Buyer;
import com.infosys.infytel.entity.Cart;
import com.infosys.infytel.entity.Seller;
import com.infosys.infytel.entity.Wishlist;
import com.infosys.infytel.repository.BuyerRepository;
import com.infosys.infytel.repository.CartRepository;
import com.infosys.infytel.repository.SellerRepository;
import com.infosys.infytel.repository.WishlistRepository;
@Service
public class UserService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	SellerRepository sellRepo;
	@Autowired 
	BuyerRepository bRepo;
	@Autowired
	WishlistRepository wRepo;
	@Autowired
	CartRepository cRepo;
	@Autowired
	ProductFeign pf;
	@Autowired
	OrderFeign of;

	public String addseller(SellerDTO sdto) throws Exception{
		Optional<Seller> opt=sellRepo.findById(sdto.getEmail());
		Seller bd=sellRepo.findTopByOrderBySellerIdDesc();
		Integer st=Integer.parseInt(bd.getsellerId().substring(1));
		if(opt.isPresent()) {
			throw new Exception("Service.SELLER_ALREADY_EXISTS");
		}
		Seller s=new Seller();
		st++;
		s.setsellerId("S"+String.valueOf(st));
		s.setName(sdto.getName());
		s.setEmail(sdto.getEmail());
		s.setPhoneNo(sdto.getPhoneNo());
		s.setActive(sdto.getIsActive());
		s.setPassword(sdto.getPassword());
		sellRepo.save(s);
		return s.getsellerId();
	}
	public String addbuyer(BuyerDTO bdto) throws Exception{
		Optional<Buyer> opt=bRepo.findById(bdto.getEmail());
		Buyer bd=bRepo.findTopByOrderByBuyerIdDesc();
		Integer st=Integer.parseInt(bd.getBuyerId().substring(1));
		if(opt.isPresent()) {
			throw new Exception("Service.BUYER_ALREADY_EXISTS");
		}
		Buyer s=new Buyer();
		st++;
		s.setBuyerId("B"+String.valueOf(st));
		s.setName(bdto.getName());
		s.setEmail(bdto.getEmail());
		s.setPhoneNo(bdto.getPhoneNo());
		s.setIsActive(bdto.getIsActive());
		s.setPassword(bdto.getPassword());
		s.setIsPriviliged(bdto.getIsPriviliged());
		s.setRewardPoints(bdto.getRewardPoints());
		bRepo.save(s);
		return s.getBuyerId();
	}
	
	public String buyerLogin(String email, String password) throws Exception {
		
		Buyer buy = bRepo.findByEmail(email);
		if(buy==null) throw new Exception("SERVICE.NO_BUYER_WITH_THIS_EMAIL");
		
		if(!buy.getPassword().equals(password))
			throw new Exception("Wrong credentials");
		
		buy.setIsActive(true);
			
		bRepo.saveAndFlush(buy);
		
		return "Login Success";
	}
	public String sellerLogin(String email, String password) throws Exception {
		
		Seller sell=sellRepo.findByEmail(email);
		if(sell==null) throw new Exception("SERVICE.NO_SELLER_WITH_THIS_EMAIL");
		System.out.println("1");
		System.out.println(sell.getEmail());
		
		
		if(!sell.getPassword().equals(password))
			throw new Exception("Wrong credentials");
		
		sell.setActive(true);
			
		sellRepo.saveAndFlush(sell);
		
		return "Login Success";
	}
	
	public void deleteBuyer(String buyerId) throws Exception {
		Optional<Buyer> s = bRepo.findById(buyerId);
		Buyer bu=s.orElseThrow(() -> new Exception("Service.BUYER_NOT_FOUND"));
		bRepo.delete(bu);
	}
	public void deleteSeller(String sellerId) throws Exception {
		Optional<Seller> s = sellRepo.findById(sellerId);
		Seller sl=s.orElseThrow(() -> new Exception("Service.SELLER_NOT_FOUND"));
		sellRepo.delete(sl);
	}
	
	public SellerDTO findSeller(String sellerId) throws Exception {
		Optional<Seller> sl=sellRepo.findById(sellerId);
		Seller s=sl.orElseThrow(()->new Exception("Service.SELLER_NOT_FOUND"));
		SellerDTO sd=SellerDTO.valueOf(s);
		return sd;
	}
	public BuyerDTO findBuyer(String buyerId) throws Exception {
		Optional<Buyer> sl=bRepo.findById(buyerId);
		Buyer s=sl.orElseThrow(()->new Exception("Service.BUYER_NOT_FOUND"));
		BuyerDTO sd=BuyerDTO.valueOf(s);
		return sd;
	}
	public String addtowishlist(WishlistDTO wishlist) throws Exception {
		Optional<Buyer> b=bRepo.findById(wishlist.getBuyerId());
		if(b.isEmpty()) throw new Exception("Service.NO_BUYER_FOUND");
		productDTO pd=pf.getProduct(wishlist.getProdId());
		if(pd==null) throw new Exception("Service.NO_PRODUCT_FOUND");
		Wishlist w = new Wishlist();
		w.setBuyerId(wishlist.getBuyerId());
		w.setProdId(wishlist.getProdId());
		wRepo.save(w);
		return wishlist.getProdId();
	}
	public void removefromwishlist(WishlistDTO wl) throws Exception {
		List<Wishlist> wd=wRepo.findAll();
		for(Wishlist wm:wd) {
			if(wm.getBuyerId().equals(wl.getBuyerId()) && wm.getProdId().equals(wl.getProdId())) {
				wRepo.delete(wm);
			}else {
				throw new Exception("Service.BUYERID_OR_PRODUCTID_DOES_NOT_EXIST");
			}
		}
	}
	public List<CartDTO> allcartdetails(String buyerId) throws Exception{
		List<Cart> ct=cRepo.findAll();
		List<CartDTO> cd=new ArrayList<>();
		for(Cart cm:ct) {
			if(cm.getBuyerId().equals(buyerId)) {
				CartDTO cdt=CartDTO.valueOf(cm);
				cd.add(cdt);
			}
		}
		if(cd==null) throw new Exception("Service.CART_IS_EMPTY_FOR_BUYERID");
		return cd;
	}
	public String deleteCartitems(String buyerId) throws Exception{
		List<Cart> ct=cRepo.findAll();
		for(Cart cm:ct) {
			if(cm.getBuyerId().equals(buyerId)) {
				cRepo.delete(cm);
			}
		}
		return buyerId;
	}
	public void deleteCartitem(String buyerId,String prodId) throws Exception{
		List<Cart> ct=cRepo.findAll();
		for(Cart cm:ct) {
			if(cm.getBuyerId().equals(buyerId) && cm.getProdId().equals(prodId)) {
				cRepo.delete(cm);
			}
		}
	}
	public String additemstocart(String buyerId) throws Exception {
		List<Wishlist> wt=wRepo.findAll();
		for(Wishlist wl:wt) {
			if(wl.getBuyerId().equals(buyerId)) {
				Cart cm=new Cart();
				cm.setBuyerId(wl.getBuyerId());
				cm.setProdId(wl.getProdId());
				cm.setQuantity(1);
				cRepo.save(cm);
				wRepo.delete(wl);
			}
		}
		return buyerId;
	}
	public String addtocartfromproducst(WishlistDTO wl) throws Exception {
		Optional<Buyer> b=bRepo.findById(wl.getBuyerId());
		if(b.isEmpty()) throw new Exception("Service.NO_BUYER_FOUND");
		productDTO pd=pf.getProduct(wl.getProdId());
		if(pd==null) throw new Exception("Service.NO_PRODUCT_FOUND");
		List<Cart> wp=cRepo.findAll();
		Integer count=0;
		for(Cart wm: wp) {
			if(wm.getBuyerId().equals(wl.getBuyerId()) && wm.getProdId().equals(wl.getProdId())) {
				wm.setQuantity(wm.getQuantity()+1);
				cRepo.saveAndFlush(wm);
				count++;
			}
		}
		if(count==0) {
			Cart w = new Cart();
			w.setBuyerId(wl.getBuyerId());
			w.setProdId(wl.getProdId());
			w.setQuantity(1);
			cRepo.save(w);
		}
		return wl.getProdId();
	}
	public void updateProductQuantity(String prodId,String buyerId,Integer quantity) throws Exception {
		Optional<Buyer> b=bRepo.findById(buyerId);
		if(b.isEmpty()) throw new Exception("Service.NO_BUYER_FOUND");
		productDTO pd=pf.getProduct(prodId);
		if(pd==null) throw new Exception("Service.NO_PRODUCT_FOUND");
		if(pd.getStock()<quantity) throw new Exception("Service.STOCK_IS_LESS_THAN_QUANTITY");
		List<Cart> ct=cRepo.findAll();
		for(Cart c:ct) {
			if(c.getBuyerId().equals(buyerId) && c.getProdId().equals(prodId)) {
				c.setQuantity(quantity);
				cRepo.saveAndFlush(c);
			}
		}
	}
	public List<OrderDTO> getpastorders(String buyerId) throws Exception {
		List<OrderDTO> od=of.findproductbyBuyerId(buyerId);
		if(od==null) throw new Exception("Service.NO_ORDERS_FOUND_FOR_BUYERID");
		return od;
	}
	public List<productDTO> getallproducts(){
		List<productDTO> pr=pf.getAllProduct();
		return pr;
	}
	public List<productDTO> getproductsbyname(String name) throws Exception{
		List<productDTO> pr=pf.findproductbyname(name);
		if(pr==null) throw new Exception("Service.NO_PRODUCT_FOUND_WITH_THIS_NAME");
		return pr;
	}
	public List<productDTO> getproductsbycategory(String category) throws Exception{
		List<productDTO> pr=pf.findproductbycategory(category);
		if(pr==null) throw new Exception("Service.NO_PRODUCT_FOUND_WITH_THIS_CATEGORY");
		return pr;
	}
	public void updaterewardpoints(Integer points,String buyerId) {
		Optional<Buyer> bt=bRepo.findById(buyerId);
		bt.get().setRewardPoints(bt.get().getRewardPoints()+points);
		bRepo.saveAndFlush(bt.get());
	}
}
