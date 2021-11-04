package com.infosys.infytel.entity;


import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="buyer")
public class Buyer {

	@Id
	private String buyerId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false,unique=true)
	private String email;
	@Column(nullable = false,unique=true)
	private long phoneNo;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private boolean isPriviliged;
	@Column(nullable = false)
	private Integer rewardPoints;
	@Column(nullable = false)
	private boolean isActive;
	
	
	public String getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public long getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean getIsPriviliged() {
		return isPriviliged;
	}


	public void setIsPriviliged(boolean isPriviliged) {
		this.isPriviliged = isPriviliged;
	}


	public Integer getRewardPoints() {
		return rewardPoints;
	}


	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}


	public boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}


	public Buyer() {
		super();
	}

	
}
