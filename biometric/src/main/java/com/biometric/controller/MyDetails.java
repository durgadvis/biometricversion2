package com.biometric.controller;

import com.biometric.forms.CardDetails;
import com.biometric.forms.User;
import com.biometric.util.Util;
import mmm.cogent.fpCaptureApi.CapturedImageData;
import mmm.cogent.fpCaptureApi.MMMCogentCSD200DeviceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.biometric.forms.UserDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class MyDetails {
	private static final Logger log = LoggerFactory.getLogger(MyDetails.class);
	
	@RequestMapping(value="/mydetails", method=RequestMethod.GET)
	public ModelAndView getDetailsBasedOnFingerPrint(Model model){
		return new ModelAndView("mydetails");
	}

	@RequestMapping(value="/shopPage", method=RequestMethod.GET)
	public ModelAndView getShopPage(Model model){
		return new ModelAndView("shopPage");
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

	@RequestMapping(value="/user", method=RequestMethod.GET)
	public ModelAndView getDetailsOnScan(){
		log.info("> getDetailsOnScan");
		MMMCogentCSD200DeviceImpl aInDevice = new MMMCogentCSD200DeviceImpl();
		CapturedImageData lCapturedImage  = Util.captureFingerPrint(true, aInDevice);
		User lMatchedUser = findMatchingUser(aInDevice, lCapturedImage);
		ModelAndView model = new ModelAndView("cardDetails");

		if(lMatchedUser != null){
			/*aInModel.addAttribute("name", lMatchedUser.getName());
			aInModel.addAttribute("userDetail", lMatchedUser);*/
			model.addObject("userDetail", lMatchedUser);
		}else{
			/*aInModel.addAttribute("name", "not Found");
			aInModel.addAttribute("userDetail", new User());*/
			model.addObject("userDetail", new User());
		}
		log.info("< getDetailsOnScan");
		//return new ModelAndView("cardDetails", "message", aInModel);
		return model;
	}

	private User findMatchingUser(MMMCogentCSD200DeviceImpl aInDevice, CapturedImageData aInReferenceData){
		String url = "jdbc:mysql://localhost:3306/biometric";
		String username = "root";
		String password = "admin";

		log.trace("Connecting database...");
		java.sql.Connection connection = null;
		User $RetMatchedUser =null;

		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			log.trace("Database connected!");

			String query = " SELECT * FROM userdetails";

			log.trace("Executing matching algo");
			// create the mysql insert preparedstatement
			Statement lStatement = connection.createStatement();
			ResultSet rs = lStatement.executeQuery(query);
			byte[] lInputFingerPrint = aInReferenceData.getIso19794_2Template();

			while (rs.next())
			{
				Blob lFingerPrint = rs.getBlob("fpIso");
				String lUserName = rs.getString("name");
				log.debug("Matching for User: "+lUserName);
				int blobLength = (int) lFingerPrint.length();

				byte[] blobAsBytes = lFingerPrint.getBytes(1, blobLength);
				boolean lIsMatchFound =  aInDevice.matchIso19794_2Templates(lInputFingerPrint, blobAsBytes);
				if(lIsMatchFound){
					log.trace("Match Found");
					$RetMatchedUser = getMatchedUser(rs);
					query = "select card.cardNumber, card.nameOnCard, card.expiryDate, user.pk, user.name from biometric.userdetails as user , biometric.carddetails as card where user.pk=card.fk and user.pk="+$RetMatchedUser.getPk();
					log.trace("Executing the query:"+query);
					ResultSet rsCard = lStatement.executeQuery(query);
					List<CardDetails> lCardDetails = new ArrayList<>();
					while (rsCard.next())
					{
						String lCardNumber = rsCard.getString("cardNumber");
						String lNameOnCard = rsCard.getString("nameOnCard");
						String lExpiryDate = rsCard.getString("expiryDate");
						lCardDetails.add(new CardDetails(lCardNumber, lNameOnCard, lExpiryDate));
						log.trace("Adding Card with Card Number:"+lCardNumber);
					}
					$RetMatchedUser.setListCardDetails(lCardDetails);
					break;
				}else{
					log.trace("Not Matching");
				}
			}
		} catch (SQLException e) {
			log.error("Cannot connect the database error :"+ e);
			throw new IllegalStateException("Cannot connect the database!", e);
		} catch (ClassNotFoundException e) {
			log.error("Class Class Exception in sql registration");
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					log.error("Cannot close the connection error: " +e);
				}
			}
		}

		if($RetMatchedUser == null){
			log.info("No Match Found");
		}
		return $RetMatchedUser;
	}

	private User getMatchedUser(ResultSet aInRs) throws SQLException {
		User $retUser= new User();
		$retUser.setName(aInRs.getString("name"));
		$retUser.setAddress(aInRs.getString("address"));
		$retUser.setAge(aInRs.getInt("age"));
		$retUser.setEmailId(aInRs.getString("emailId"));
		$retUser.setPhonenumber(aInRs.getLong("phoneNumber"));
		$retUser.setPk(aInRs.getInt("pk"));
		$retUser.setName(aInRs.getString("name"));
		return $retUser;
	}
}
