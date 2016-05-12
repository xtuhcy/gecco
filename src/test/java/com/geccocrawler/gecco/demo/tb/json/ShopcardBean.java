package com.geccocrawler.gecco.demo.tb.json;

import java.util.List;

public class ShopcardBean {

	private boolean isTmall;
	private int sellerCredit;
	private int totalRate;
	private String encryptedUserId;
	private List<Integer> delivery;
	private List<Integer> service;
	private List<Integer> description;
	private List<LevelClassesBean> levelClasses;

	public boolean isTmall() {
		return isTmall;
	}

	public void setTmall(boolean isTmall) {
		this.isTmall = isTmall;
	}

	public int getSellerCredit() {
		return sellerCredit;
	}

	public void setSellerCredit(int sellerCredit) {
		this.sellerCredit = sellerCredit;
	}

	public int getTotalRate() {
		return totalRate;
	}

	public void setTotalRate(int totalRate) {
		this.totalRate = totalRate;
	}

	public String getEncryptedUserId() {
		return encryptedUserId;
	}

	public void setEncryptedUserId(String encryptedUserId) {
		this.encryptedUserId = encryptedUserId;
	}

	public List<Integer> getDelivery() {
		return delivery;
	}

	public void setDelivery(List<Integer> delivery) {
		this.delivery = delivery;
	}

	public List<Integer> getService() {
		return service;
	}

	public void setService(List<Integer> service) {
		this.service = service;
	}

	public List<Integer> getDescription() {
		return description;
	}

	public void setDescription(List<Integer> description) {
		this.description = description;
	}

	public List<LevelClassesBean> getLevelClasses() {
		return levelClasses;
	}

	public void setLevelClasses(List<LevelClassesBean> levelClasses) {
		this.levelClasses = levelClasses;
	}

}
