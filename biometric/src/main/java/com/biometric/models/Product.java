package com.biometric.models;

public class Product {
	private String productId;
	private String productName;
	private String manufacturerName;
	private String description;
	private Double listPrice;
	private String primarImageUrl;
	private ProductAttributes productAttributes;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getListPrice() {
		return listPrice;
	}
	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}
	public String getPrimarImageUrl() {
		return primarImageUrl;
	}
	public void setPrimarImageUrl(String primarImageUrl) {
		this.primarImageUrl = primarImageUrl;
	}
	public ProductAttributes getProductAttributes() {
		return productAttributes;
	}
	public void setProductAttributes(ProductAttributes productAttributes) {
		this.productAttributes = productAttributes;
	}
	
	
	
	
}
