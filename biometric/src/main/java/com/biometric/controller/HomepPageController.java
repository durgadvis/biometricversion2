package com.biometric.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biometric.forms.CardDetails;
import com.biometric.forms.User;
import com.biometric.util.FingerPrintDetails;
import com.biometric.util.Util;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import mmm.cogent.fpCaptureApi.CapturedImageData;
import mmm.cogent.fpCaptureApi.MMMCogentCSD200DeviceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.biometric.forms.UserDetails;

import javax.imageio.ImageIO;
import javax.smartcardio.Card;

@Controller
public class HomepPageController {

	private static final Logger log = LoggerFactory.getLogger(HomepPageController.class);

	@RequestMapping("/user/newUser")
	public ModelAndView helloWorld(Model model) {
        log.info("NewUser URL");
		String message = "Welcome to Biometric";
		model.addAttribute("userDetail", new User());
		return new ModelAndView("NewUser", "message", message);
	}

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView firstPage(Model model) {
        log.info("Main URL");
        return new ModelAndView("index");
    }

   @RequestMapping(value="/user", method = RequestMethod.POST)
    public ModelAndView createUser(@ModelAttribute("userDetail") User userDetails) {
        log.info("> Create User URL");
        boolean lIsInserted = addUserToDatabase(userDetails);

        if(lIsInserted){
            String message = "New User has been added to the database";
            log.info("< Create User URL");
            return new ModelAndView("index");
        }else{
            String message = "Failed to insert new user";
            log.info("< Create User URL");
            return new ModelAndView("index");
        }
    }

    @ResponseBody
    @RequestMapping(value="/user/fingerPrint", method = RequestMethod.GET, produces = "application/json")
    public FingerPrintDetails getFingerPrint() {

        log.info(">User FingerPrint URL");
        FingerPrintDetails lUser=new FingerPrintDetails();
        MMMCogentCSD200DeviceImpl aInDevice = new MMMCogentCSD200DeviceImpl();

        //Calling Util Method to capture the fingerPrint
        CapturedImageData lCapturedImage  = Util.captureFingerPrint(true, aInDevice);
        if(lCapturedImage != null && lCapturedImage.getIso19794_2Template() != null){
            log.info("Image Capture success, converting to string and sending it");
            lUser.setFgIso(Base64.encode(lCapturedImage.getIso19794_2Template()));
            lUser.setFgBmp(Base64.encode(lCapturedImage.getBmpImageData()));
        }else{
            log.info("Image Capture failed");
        }

        log.info("<User FingerPrint URL");
        return lUser;
    }

    /*@ResponseBody
    @RequestMapping(value="/user/fingerPrintWithoutAjax", method = RequestMethod.GET)
    public ModelAndView getFingerPrintWithoutAjax(@ModelAttribute("userDetail") User aInModel, Model userModel) {

        log.info(">User FingerPrint URL");
        MMMCogentCSD200DeviceImpl aInDevice = new MMMCogentCSD200DeviceImpl();

        //Calling Util Method to capture the fingerPrint
        CapturedImageData lCapturedImage  = Util.captureFingerPrint(true, aInDevice);
        if(lCapturedImage != null && lCapturedImage.getIso19794_2Template() != null){
            log.info("Image Capture success, converting to string and sending it");
            aInModel.setFgIso(lCapturedImage.getIso19794_2Template());
            aInModel.setFgBmp(lCapturedImage.getBmpImageData());
        }else{
            log.info("Image Capture failed");
        }
        userModel.addAttribute("userDetail", aInModel);
        log.info("<User FingerPrint URL");
        return new ModelAndView("NewUser", "message", userModel);
    }*/

    /*@RequestMapping(value="/user", method = RequestMethod.POST)
    public ModelAndView addUserAllDetails(@ModelAttribute("userDetail") User aInUserModel, Model aInModel, @RequestParam(value = "scanSubmit") String scanSubmit) {
        log.info("> User post URL");
        if(scanSubmit.equalsIgnoreCase("submit")){
            log.info("Entering the submit case");
            boolean lIsInserted = addUserToDatabase(aInUserModel);

            if(lIsInserted){
                String message = "New User has been added to the database";
                log.info("< user post URL");
                return new ModelAndView("index");
            }else{
                String message = "Failed to insert new user";
                log.info("< User post URL");
                return new ModelAndView("index");
            }
        }else{
            log.info("Entering the scan case");
            MMMCogentCSD200DeviceImpl aInDevice = new MMMCogentCSD200DeviceImpl();

            //Calling Util Method to capture the fingerPrint
            CapturedImageData lCapturedImage  = Util.captureFingerPrint(true, aInDevice);
            if(lCapturedImage != null && lCapturedImage.getIso19794_2Template() != null){
                log.info("Image Capture success, converting to string and sending it");
                aInUserModel.setFgIso(lCapturedImage.getIso19794_2Template());
                aInUserModel.setFgBmp(lCapturedImage.getBmpImageData());
            }else{
                log.info("Image Capture failed");
            }

            aInModel.addAttribute("userDetail", aInUserModel);
            log.info("< user post URL");
            return new ModelAndView("NewUser", "message", aInModel);
        }
    }*/
	


    private boolean addUserToDatabase(User aInUserDetails) {
        MMMCogentCSD200DeviceImpl aInDevice = new MMMCogentCSD200DeviceImpl();

        //Calling Util Method to capture the fingerPrint
        CapturedImageData lCapturedImage  = Util.captureFingerPrint(true, aInDevice);
        byte[] lIsoDb = lCapturedImage.getIso19794_2Template();
        byte[] lBmp = lCapturedImage.getBmpImageData();

        log.trace("Adding new user to database");
        if(lCapturedImage != null && lIsoDb != null){
        //if(aInUserDetails != null && aInUserDetails.getFgIso()!=null ){
            String url = "jdbc:mysql://localhost:3306/biometric";
            String username = "root";
            String password = "admin";

            log.trace("Connecting database...");
            java.sql.Connection connection = null;

            try{
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                log.trace("Database connected!");

                String query = " insert into userdetails (name, phoneNumber, emailId, address , age, fpIso, fbBmpImage)"
                        + " values (?, ?, ?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = connection.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
                preparedStmt.setString(1, aInUserDetails.getName());
                preparedStmt.setLong(2, aInUserDetails.getPhonenumber());
                preparedStmt.setString(3, aInUserDetails.getEmailId());
                preparedStmt.setString(4, aInUserDetails.getAddress());
                preparedStmt.setInt(5, aInUserDetails.getAge());
                preparedStmt.setBlob(6, new javax.sql.rowset.serial.SerialBlob(lIsoDb));
                //preparedStmt.setBlob(6, new javax.sql.rowset.serial.SerialBlob(aInUserDetails.getFgIso()));
                preparedStmt.setBlob(7, new javax.sql.rowset.serial.SerialBlob(lBmp));
                //preparedStmt.setBlob(7, new javax.sql.rowset.serial.SerialBlob(aInUserDetails.getFgBmp()));

                // execute the prepared statement
                preparedStmt.executeUpdate();
                ResultSet rs = preparedStmt.getGeneratedKeys();
                int value =0;
                //Retrieve the auto generated Primary Key
                if(rs.next()) {
                    value = rs.getInt(1);
                    log.debug("The primary key is:"+value);
                }

                for(CardDetails lCardDetails : aInUserDetails.getListCardDetails()){
                    log.trace("Inserting Card Details"+lCardDetails.getNameOnCard());
                    query = " insert into carddetails (nameOnCard, cardNumber, cvv, expiryDate , fk)"
                            + " values (?, ?, ?, ?, ?)";

                    // create the mysql insert prepared statement
                    preparedStmt = connection.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
                    preparedStmt.setString(1, lCardDetails.getNameOnCard());
                    preparedStmt.setString(2, lCardDetails.getCardNumber());
                    preparedStmt.setInt(3, 1);
                    preparedStmt.setString(4, lCardDetails.getExpiryDate());
                    preparedStmt.setInt(5, value);

                    preparedStmt.executeUpdate();
                }

                /*log.trace("> Storing Image");
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(lBmp));
                File lOutputFile = new File("biometric-1/resources/img/"+value+".bmp");
                ImageIO.write(img, "bmp", lOutputFile);
                log.trace("< Storing Image");*/
            } catch (SQLException e) {
                log.error("Cannot connect the database!", e);
                throw new IllegalStateException("Cannot connect the database!", e);
            } catch (ClassNotFoundException e) {
                log.error("Class Class Exception in sql registration");
                e.printStackTrace();
            } /*catch (IOException e) {
                e.printStackTrace();
            } */finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        log.error("Cannot close the connection!", e);
                    }
                }
            }
            return true;
        }else{
            log.error("User not Added since fingerPrint was not captured properly");
            return false;
        }
    }

}
