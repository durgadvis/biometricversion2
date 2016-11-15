package com.biometric.util;

import com.biometric.controller.HomepPageController;
import com.biometric.forms.CardDetails;
import com.biometric.forms.User;
import com.biometric.util.*;
import com.mysql.jdbc.*;
import mmm.cogent.fpCaptureApi.CapturedImageData;
import mmm.cogent.fpCaptureApi.MMMCogentCSD200DeviceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger homeControllerLogger = LoggerFactory.getLogger(HomepPageController.class);
    public static void main( String[] args )
    {
        try {
            captureAndCreateImage();
        } catch (IOException e) {
            System.out.println("Received error when saving the image"+e);
            e.printStackTrace();
        }
    }

    public static void captureAndCreateImage() throws IOException {
        MMMCogentCSD200DeviceImpl aInDevice = new MMMCogentCSD200DeviceImpl();
        System.out.println("Capturing image");
        CapturedImageData lCapturedData = com.biometric.util.Util.captureFingerPrint(true, aInDevice);
        if(lCapturedData !=null && lCapturedData.getBmpImageData() !=null){
            System.out.println("Image Captured");
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(lCapturedData.getBmpImageData()));
            File lOutputFile = new File("myImage.bmp");
            ImageIO.write(img, "bmp", lOutputFile);
            System.out.println("Image stored in file myImage.bmp");
        }else{
            System.out.println("Image couldn't be captured");
        }
    }

    public static void insertUsersWithCardDetails(){
        User lUser = new User("Sunil",1234L, "s.g@.com", "myAddress", 12, new byte[12], new byte[12]);
        String url = "jdbc:mysql://localhost:3306/biometric";
        String username = "root";
        String password = "admin";

        System.out.println("Connecting database...");
        java.sql.Connection connection = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");

            String query = " insert into userdetails (name, phoneNumber, emailId, address , age, fpIso, fbBmpImage)"
                    + " values (?, ?, ?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, lUser.getName());
            preparedStmt.setLong(2, lUser.getPhonenumber());
            preparedStmt.setString(3, lUser.getEmailId());
            preparedStmt.setString(4, lUser.getAddress());
            preparedStmt.setInt(5, lUser.getAge());
            preparedStmt.setBlob(6, new javax.sql.rowset.serial.SerialBlob(lUser.getFgIso()));
            preparedStmt.setBlob(7, new javax.sql.rowset.serial.SerialBlob(lUser.getFgBmp()));

            // execute the prepared statement
            preparedStmt.executeUpdate();
            ResultSet rs = preparedStmt.getGeneratedKeys();
            int value =0;
            if(rs.next()) {
                value = rs.getInt(1);
                System.out.println("The primary key is: "+value);
            }

            CardDetails lCardDetails = new CardDetails("sunil", "12312312", 233, "02/12",BankNames.AXIS, value);

            for(int i=0; i<3;i++){
                query = " insert into carddetails (nameOnCard, cardNumber, cvv, expiryDate , fk)"
                        + " values (?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement
                preparedStmt = connection.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
                preparedStmt.setString(1, lCardDetails.getNameOnCard());
                preparedStmt.setString(2, lCardDetails.getCardNumber());
                preparedStmt.setInt(3, lCardDetails.getCvv());
                preparedStmt.setString(4, lCardDetails.getExpiryDate());
                preparedStmt.setInt(5, lCardDetails.getFk());

                preparedStmt.executeUpdate();
            }

            System.out.println("One row created successfully");
        } catch (SQLException e) {
            System.out.println("Cannot connect the database!"+ e);
            throw new IllegalStateException("Cannot connect the database!", e);
        } catch (ClassNotFoundException e) {
            System.out.println("Class Class Exception in sql registration");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Cannot close the connection!"+ e);
                }
            }
        }
    }
}
