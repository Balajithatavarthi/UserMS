package com.infosys.infytel.dto;

import com.infosys.infytel.entity.Cart;

public class CartDTO {
	private String buyerId;
	private String prodId;
	private Integer quantity;
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public static CartDTO valueOf(Cart ct) {
		CartDTO cd=new CartDTO();
		cd.setBuyerId(ct.getBuyerId());
		cd.setProdId(ct.getProdId());
		cd.setQuantity(ct.getQuantity());
		return cd;
	}
}
