package com.biometric.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.biometric.forms.UserDetails;

@Controller
public class MyDetails {
	private static final Logger log = LoggerFactory.getLogger(MyDetails.class);
	
	@RequestMapping(value="/mydetails", method=RequestMethod.GET)
	public ModelAndView getDetailsBasedOnFingerPrint(Model model){
		return new ModelAndView("mydetails");
	}
	
	@ResponseBody
	@RequestMapping(value="/getuserdetails", method=RequestMethod.GET, produces="application/json")
	public UserDetails getUserDetailsAfterscanning(){
		System.out.println("Inside user details");
		log.info("inside function");
		UserDetails userDetails=new UserDetails();
		userDetails.setFirstName("Vishal Durgad");
		userDetails.setPhonenumber(1213);
		userDetails.setUser_id(1);
		System.out.println("User details object" + userDetails.getFirstName());
		return userDetails;
	}
	
}
