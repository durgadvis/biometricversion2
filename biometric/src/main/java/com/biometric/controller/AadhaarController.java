package com.biometric.controller;

import com.biometric.forms.CardDetails;
import com.biometric.forms.User;
import com.biometric.util.FingerPrintDetails;
import com.biometric.util.Util;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import mmm.cogent.fpCaptureApi.CapturedImageData;
import mmm.cogent.fpCaptureApi.MMMCogentCSD200DeviceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by suvp on 11/14/2016.
 */
@Controller
public class AadhaarController {

    private static final Logger log = LoggerFactory.getLogger(AadhaarController.class);

    @Autowired
    DataSource dataSource;

    @RequestMapping(value = "/registration/aadhaar", method = RequestMethod.GET)
    public ModelAndView aadhaarRegistrationGet(Model model) {
        log.info("NewUser URL");
        String message = "Welcome to Aadhaar Registration";
        model.addAttribute("userDetail", new User());
        return new ModelAndView("aadhaarRegistration", "message", message);
    }

    @RequestMapping(value = "/registration/aadhaar", method = RequestMethod.POST)
    public ModelAndView aadhaarRegistrationPost(@ModelAttribute("userDetail") User userDetails, Model model) {
        log.info("> Aadhaar Registration Post URL");
        Integer lInsertedIndex = addUserToDatabase(userDetails);
        if(lInsertedIndex != 0){
            String message = "New User has been added to the database with Aadhaar Id : "+ lInsertedIndex;
            log.info("< Aadhaar Registration Post URL");
            model.addAttribute("message", message);
            return new ModelAndView("index", message, model);
        }else{
            String message = "Failed to insert new user";
            log.info("< Aadhaar Registration Post URL");
            model.addAttribute("message", message);
            return new ModelAndView("index", message, model);
        }
    }

    private Integer addUserToDatabase(User aInUserDetails) {
        MMMCogentCSD200DeviceImpl aInDevice = new MMMCogentCSD200DeviceImpl();

        //Calling Util Method to capture the fingerPrint
        CapturedImageData lCapturedImage  = Util.captureFingerPrint(true, aInDevice);
        byte[] lIsoDb = lCapturedImage.getIso19794_2Template();
        byte[] lBmp = lCapturedImage.getBmpImageData();

        log.trace("Adding new user to database");
        if(lCapturedImage != null && lIsoDb != null){
            //if(aInUserDetails != null && aInUserDetails.getFgIso()!=null ){
            log.trace("Connecting database d...");
            java.sql.Connection connection = null;
            int $RetValue =0;

            try{
                Class.forName("com.mysql.jdbc.Driver");
                connection = dataSource.getConnection();
                log.trace("Database connected using spring datasource!");

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
                //Retrieve the auto generated Primary Key
                if(rs.next()) {
                    $RetValue = rs.getInt(1);
                    log.debug("The primary key is:"+$RetValue);
                    return $RetValue;
                }
            } catch (SQLException e) {
                log.error("Cannot connect the database!", e);
                throw new IllegalStateException("Cannot connect the database!", e);
            } catch (ClassNotFoundException e) {
                log.error("Class Class Exception in sql registration");
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        log.error("Cannot close the connection!", e);
                        return $RetValue;
                    }
                }
                return $RetValue;
            }
        }else{
            log.error("User not Added since fingerPrint was not captured properly");
            return 0;
        }
    }

}
