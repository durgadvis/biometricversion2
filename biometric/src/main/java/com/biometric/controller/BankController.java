package com.biometric.controller;

import com.biometric.Service.UserService;
import com.biometric.forms.CardDetails;
import com.biometric.forms.User;
import com.biometric.util.BankNames;
import com.biometric.util.Util;
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
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by suvp on 11/14/2016.
 */
@Controller
public class BankController {
    private static final Logger log = LoggerFactory.getLogger(BankController.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/registration/bank", method = RequestMethod.GET)
    public ModelAndView bankRegistrationGet(Model model) {
        log.info("Bank URL");
        String message = "Welcome to Aadhaar Registration";
        model.addAttribute("userDetail", new User());
        List<BankNames> bankList = new ArrayList<BankNames>( Arrays.asList(BankNames.values()));
        model.addAttribute("bankDetails", bankList);
        return new ModelAndView("bankRegistration", "message", message);
    }

    @RequestMapping(value = "/registration/bank", method = RequestMethod.POST)
    public ModelAndView bankRegistrationPost(@ModelAttribute("userDetail") User userDetails, Model model) {
        log.info("> Bank Registration Post URL: "+userDetails.getBankName().getName());
        //boolean lIsInserted = addCardToDatabase(userDetails);
        boolean lIsInserted = addCardToDatabaseUsingHibernate(userDetails);

        if(lIsInserted){
            String message = "Card Details added";
            log.info("< Bank Registration Post URL");
            model.addAttribute("message", message);
            return new ModelAndView("index", message, model);
        }else{
            String message = "Failed to add card details";
            log.info("< Bank Registration Post URL");
            model.addAttribute("message", message);
            return new ModelAndView("index", message, model);
        }
    }

    private boolean addCardToDatabase(User aInUserDetails) {
        log.trace("Adding card details to database");
        if(aInUserDetails != null){
            log.trace("Connecting database...");
            java.sql.Connection connection = null;

            try{
                Class.forName("com.mysql.jdbc.Driver");
                connection = dataSource.getConnection();
                log.trace("Database connected using spring datasource!");
                int lPrimaryKey = aInUserDetails.getPk();

                for(CardDetails lCardDetails : aInUserDetails.getListCardDetails()){
                    log.trace("Inserting Card Details"+lCardDetails.getNameOnCard());
                    String query = " insert into carddetails (nameOnCard, cardNumber, cvv, expiryDate , bankName,  fk)"
                            + " values (?, ?, ?, ?, ?, ?)";

                    // create the mysql insert prepared statement
                    PreparedStatement  preparedStmt = connection.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
                    preparedStmt.setString(1, lCardDetails.getNameOnCard());
                    preparedStmt.setString(2, lCardDetails.getCardNumber());
                    preparedStmt.setInt(3, 1);
                    preparedStmt.setString(4, lCardDetails.getExpiryDate());
                    preparedStmt.setString(5, aInUserDetails.getBankName().getName());
                    preparedStmt.setInt(6, lPrimaryKey);

                    preparedStmt.executeUpdate();
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
                    }
                }
            }
            return true;
        }else{
            log.error("Card details not added to database since no data received");
            return false;
        }
    }

    private boolean addCardToDatabaseUsingHibernate(User aInUserDetails) {
        log.trace("Adding card details to database using hibernate");
        if(aInUserDetails != null){
            BankNames lBankName = aInUserDetails.getBankName();

            //Prepopulate the required values which are default.
            for(CardDetails lCardDetails :aInUserDetails.getListCardDetails()){
                lCardDetails.setBankName(lBankName);
                lCardDetails.setCvv(1);
            }
            log.trace("Connecting database through hibernate...");
            return  userService.addBankDetails(aInUserDetails);
        }
        return false;
    }
}
