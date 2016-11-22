package com.biometric.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.biometric.enums.Colors;
import com.biometric.enums.ProductSizes;
import com.biometric.models.Product;
import com.biometric.models.ProductAttributes;

@Controller
public class ProductController {

	
	@RequestMapping(value="/shop" , method=RequestMethod.GET)
	public ModelAndView getProduct(Model model){
		Product product= setAndGetProduct();
		model.addAttribute("productData", product);
		return new ModelAndView("productPage");
	}

	private Product setAndGetProduct() {
		Product product=new Product();
		ProductAttributes productAttributes=new ProductAttributes();
		product.setProductId("SHIRT-1000001");
		product.setProductName("Slim Shirt");
		product.setManufacturerName("Levis Shirt");
		product.setListPrice(300.00);
		product.setDescription("Levis as a brand is one of the finest brand in the world. Shirt is made of pure cotton and stitched delicately by experts.");
		
		productAttributes.setColor(Colors.values());
		productAttributes.setSize(ProductSizes.values());
		
		product.setProductAttributes(productAttributes);
		product.setPrimarImageUrl("/resources/img/shirt.jpg");
		
		return product;
	}
	
	
}
