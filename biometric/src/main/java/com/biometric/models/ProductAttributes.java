package com.biometric.models;

import com.biometric.enums.Colors;
import com.biometric.enums.ProductSizes;

public class ProductAttributes {
	private Colors[] color;
	private ProductSizes[] size;
	
	
	public Colors[] getColor() {
		return color;
	}
	public void setColor(Colors[] colors) {
		this.color = colors;
	}
	public ProductSizes[] getSize() {
		return size;
	}
	public void setSize(ProductSizes[] size) {
		this.size = size;
	}
	
}
