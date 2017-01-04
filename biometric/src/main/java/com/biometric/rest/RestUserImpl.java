package com.biometric.rest;

import com.biometric.controller.AadharShopController;
import com.biometric.forms.CardDetails;
import com.biometric.forms.User;
import com.biometric.util.BankNames;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by suvp on 1/3/2017.
 */
@RestController
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class RestUserImpl {
    private static final Logger log = LoggerFactory.getLogger(RestUserImpl.class);

    @Autowired
    DataSource dataSource;


    //For mapping /rest/user/61 get all bank details also.
    @GET
    @Path("{id}")
    public User getUserOnKey(@PathParam("id") int id){
        log.trace("Rest Interface for getting user id");
        User $UserObject = new User();
        java.sql.Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = dataSource.getConnection();
            String query = " SELECT * FROM userdetails where pk ="+id;
            log.trace("Selecting User");
            // create the mysql insert prepared statement
            Statement lStatement = connection.createStatement();
            ResultSet rs = lStatement.executeQuery(query);

            while (rs.next())
            {
                log.trace("Got the User Object of that Id");
                $UserObject = AadharShopController.getMatchedUser(rs);
                query = "select card.cardNumber, card.nameOnCard, card.expiryDate, user.pk, user.name, card.bankName from biometric.userdetails as user , biometric.carddetails as card where user.pk=card.fk and user.pk="+id;
                log.trace("Executing the query:"+query);
                ResultSet rsCard = lStatement.executeQuery(query);
                List<CardDetails> lCardDetails = new ArrayList<>();
                while (rsCard.next())
                {
                    String lCardNumber = rsCard.getString("cardNumber");
                    String lNameOnCard = rsCard.getString("nameOnCard");
                    String lExpiryDate = rsCard.getString("expiryDate");
                    lCardDetails.add((new CardDetails(lNameOnCard, lCardNumber, lExpiryDate)));
                    log.trace("Adding Card with Card Number:"+lCardNumber);
                }
                $UserObject.setListCardDetails(lCardDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
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
        return $UserObject;
    }

    //For mapping http://localhost:8080/biometric-1/rest/user?id=61
    @GET
    public User getUserOnQueryKey(@QueryParam("id") int id, @QueryParam("bankName") BankNames aInBankName){
        log.trace("Rest Interface for getting user id using query param");
        User $UserObject = new User();
        java.sql.Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = dataSource.getConnection();
            String query = " SELECT * FROM userdetails where pk ="+id;
            log.trace("Selecting User");
            // create the mysql insert prepared statement
            Statement lStatement = connection.createStatement();
            ResultSet rs = lStatement.executeQuery(query);

            while (rs.next())
            {
                log.trace("Got the User Object of that Id");
                $UserObject = AadharShopController.getMatchedUser(rs);
                if(aInBankName !=null) {
                    query = "select card.cardNumber, card.nameOnCard, card.expiryDate,card.bankName, user.pk, user.name from biometric.userdetails as user , biometric.carddetails as card where user.pk=card.fk and user.pk=" + id + " and card.bankName=\"" + aInBankName.getName() + "\"";
                }else{
                    query = "select card.cardNumber, card.nameOnCard, card.expiryDate, user.pk, user.name from biometric.userdetails as user , biometric.carddetails as card where user.pk=card.fk and user.pk=" + id;
                }
                ResultSet rsCard = lStatement.executeQuery(query);
                List<CardDetails> lCardDetails = new ArrayList<>();
                while (rsCard.next()) {
                    String lCardNumber = rsCard.getString("cardNumber");
                    String lNameOnCard = rsCard.getString("nameOnCard");
                    String lExpiryDate = rsCard.getString("expiryDate");
                    String lBankName = rsCard.getString("bankName");

                    CardDetails lDbCardDetails = new CardDetails(lNameOnCard, lCardNumber, lExpiryDate);
                    lDbCardDetails.setBankName(aInBankName);
                    lCardDetails.add(lDbCardDetails);

                    log.trace("Adding Card with Card Number:" + lCardNumber);
                }
                $UserObject.setListCardDetails(lCardDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
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
        return $UserObject;
    }

    @DELETE
    @Path("{id}")
    public JSONObject deleteUser(@PathParam("id")int aInId){
        log.trace("Rest Interface for getting user id using query param");
        JSONObject $RetObject = new JSONObject();
        java.sql.Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = dataSource.getConnection();
            String query = " delete FROM userdetails where pk ="+aInId;
            Statement lStatement = connection.createStatement();
            int lRowsAffected = lStatement.executeUpdate(query);
            if(lRowsAffected > 0){
                $RetObject.put("Delete Action","Successful");
                log.trace("Delete successful");
            }else{
                $RetObject.put("Delete Action","Un-Successful");
                log.trace("Delete Un-successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
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
        return  $RetObject;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postBankDetails(CardDetails[] aInBody){
        log.trace("Received in post method");
        java.sql.Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = dataSource.getConnection();
            String query = " insert into carddetails (nameOnCard, cardNumber, cvv, expiryDate , bankName,  fk)"
                    + " values (?, ?, ?, ?, ?, ?)";
            for(int i=0; i< aInBody.length;i++) {
                // create the mysql insert prepared statement
                CardDetails lCardDetails = aInBody[i];
                PreparedStatement preparedStmt = connection.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
                preparedStmt.setString(1, lCardDetails.getNameOnCard());
                preparedStmt.setString(2, lCardDetails.getCardNumber());
                preparedStmt.setInt(3, 1);
                preparedStmt.setString(4, lCardDetails.getExpiryDate());
                preparedStmt.setString(5, lCardDetails.getBankName().getName());
                preparedStmt.setInt(6, lCardDetails.getFk());
                preparedStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            log.error("Class Exception in sql registration");
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
    }
}
