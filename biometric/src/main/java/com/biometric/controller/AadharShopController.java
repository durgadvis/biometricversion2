package com.biometric.controller;

        import com.biometric.forms.CardDetails;
        import com.biometric.forms.User;
        import com.biometric.util.BankNames;
        import com.biometric.util.FingerPrintDetails;
        import com.biometric.util.Util;
        import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
        import mmm.cogent.fpCaptureApi.CapturedImageData;
        import mmm.cogent.fpCaptureApi.MMMCogentCSD200DeviceImpl;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.ModelAttribute;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.servlet.ModelAndView;

        import java.sql.*;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

/**
 * Created by suvp on 11/15/2016.
 */
@Controller
public class AadharShopController {
    private static final Logger log = LoggerFactory.getLogger(AadharShopController.class);

    @RequestMapping(value="/shop/aadhaar", method= RequestMethod.GET)
    public ModelAndView getShopPage(Model model){
        log.trace("> Shop Aadhaar URL");
        String message = "Welcome to Aadhaar Registration";
        model.addAttribute("userDetail", new User());
        List<BankNames> bankList = new ArrayList<BankNames>(Arrays.asList(BankNames.values()));
        model.addAttribute("bankDetails", bankList);
        log.trace("< Shop Aadhaar URL");
        return new ModelAndView("aadhaarShop", "message", message);
    }

    @RequestMapping(value = "/shop/cardDetails", method = RequestMethod.GET)
    public ModelAndView getCardDetailsOfBank(@ModelAttribute("userDetail") User userDetails, Model aInModel) {
        log.info("> getDetailsOnScan");
        MMMCogentCSD200DeviceImpl aInDevice = new MMMCogentCSD200DeviceImpl();
        CapturedImageData lCapturedImage  = Util.captureFingerPrint(true, aInDevice);
        BankNames lBankName = userDetails.getBankName();
        User lMatchedUser = findMatchingUser(aInDevice, lCapturedImage, lBankName);

        if(lMatchedUser != null){
            if(lMatchedUser.getListCardDetails() != null && lMatchedUser.getListCardDetails().size() > 0){
                ModelAndView model = new ModelAndView("cardDetails");
                model.addObject("userDetail", lMatchedUser);
                log.info("< getDetailsOnScan");
                return model;
            }else{
                String message = "Failed to shop , User has no card details associated to Aaadhaar ";
                aInModel.addAttribute("message", message);
                log.info("< getDetailsOnScan");
                return new ModelAndView("index", message, aInModel);
            }
        }else{
            String message = "Failed to shop , No Matching user found";
            aInModel.addAttribute("message", message);
            log.info("< getDetailsOnScan");
            return new ModelAndView("index", message, aInModel);
        }
    }

    @RequestMapping(value="/shop/payment", method= RequestMethod.POST)
    public ModelAndView getPaymentPage(Model model){
        log.trace("> Get Payment Page");
        String message = "Welcome to Aadhaar Registration";
        log.trace("< Get Payment Page");
        return new ModelAndView("paymentPage", "message", message);
    }

    private User findMatchingUser(MMMCogentCSD200DeviceImpl aInDevice, CapturedImageData aInReferenceData, BankNames aInBankName){
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
                    query = "select card.cardNumber, card.nameOnCard, card.expiryDate, user.pk, user.name from biometric.userdetails as user , biometric.carddetails as card where user.pk=card.fk and user.pk="+$RetMatchedUser.getPk()+" and card.bankName=\""+aInBankName.getName()+"\"";
                    log.trace("Executing the query:"+query);
                    ResultSet rsCard = lStatement.executeQuery(query);
                    List<CardDetails> lCardDetails = new ArrayList<>();
                    while (rsCard.next())
                    {
                        String lCardNumber = rsCard.getString("cardNumber");
                        String lNameOnCard = rsCard.getString("nameOnCard");
                        String lExpiryDate = rsCard.getString("expiryDate");
                        lCardDetails.add(new CardDetails(lNameOnCard, lCardNumber, lExpiryDate));
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
