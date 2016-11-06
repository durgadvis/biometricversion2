package com.biometric.controller;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.biometric.forms.UserDetails;

@Controller
public class HomepPageController {

	private static final Logger homeControllerLogger = LoggerFactory.getLogger(HomepPageController.class);

	@RequestMapping("/welcome")
	public ModelAndView helloWorld(Model model) {
 
		String message = "Welcome to Biometric";
		model.addAttribute("userDetail", new UserDetails());
		return new ModelAndView("welcome", "message", message);
	}
	
	
	@RequestMapping(value="/addUser", method = RequestMethod.POST)
	public ModelAndView addNewUser(@ModelAttribute("userDetail") UserDetails userDetails) {
        addEntryintheDatabase(userDetails);
        String message = "New User has been added to the database";
        homeControllerLogger.info("AddNewUser completed");
		return new ModelAndView("welcome", "newUSer", message);
	}
	
	
	@RequestMapping(value = "/ajaxfingerprint", method = RequestMethod.GET)
     @ResponseBody
     public String getFingerPrintAfterscanning() {
        String result = "Ajax file testing";
        System.out.println("inside ajax function");
        return result;
    }

	private void addEntryintheDatabase(UserDetails userDetails) {
		String url = "jdbc:mysql://localhost:3306/biometric";
		String username = "root";
		String password = "admin";

		homeControllerLogger.trace("Connecting database...");
        java.sql.Connection connection = null;

		try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
			homeControllerLogger.trace("Database connected!");

			String query = " insert into userdetails (User_Id, User_name, FingerPrint, phonenumber)"
				        + " values (?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, userDetails.getUser_id());
            preparedStmt.setString(2, userDetails.getFirstName());
            preparedStmt.setString(3, userDetails.getFingerPrint());
            preparedStmt.setInt(4, userDetails.getPhonenumber());

            // execute the prepared statement
            preparedStmt.execute();
            homeControllerLogger.trace("One row created successfully");
		} catch (SQLException e) {
			homeControllerLogger.error("Cannot connect the database!", e);
		    throw new IllegalStateException("Cannot connect the database!", e);
		} catch (ClassNotFoundException e) {
            homeControllerLogger.error("Class Class Exception in sql registration");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    homeControllerLogger.error("Cannot close the connection!", e);
                }
            }
        }
    }
	
}
