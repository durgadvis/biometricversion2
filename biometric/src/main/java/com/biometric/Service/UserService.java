package com.biometric.Service;

import com.biometric.Dao.UserDao;
import com.biometric.forms.CardDetails;
import com.biometric.forms.User;
import com.biometric.util.BankNames;
import mmm.cogent.fpCaptureApi.CapturedImageData;
import mmm.cogent.fpCaptureApi.MMMCogentCSD200DeviceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by suvp on 11/29/2016.
 */
@Service("userService")
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao dao;

    public void addUserToDb(User aInUser){
        dao.saveUser(aInUser);
    }

    public boolean addBankDetails(User aInUser){
        final User lDbUser = dao.findUser(aInUser);
        if(lDbUser != null){

            aInUser.getListCardDetails().forEach(cardDetails -> cardDetails.setUser(lDbUser));

            lDbUser.getListCardDetails().addAll(aInUser.getListCardDetails());
            dao.updateUser(lDbUser);
            return true;
        }else{
            log.error("Failed to find corresponding user in Database");
            return false;
        }
    }

    public User findUserOfBank(MMMCogentCSD200DeviceImpl aInDevice, CapturedImageData aInReferenceData, BankNames aInBankName){
        List<User> lListOfUser = dao.findAllUser();
        byte[] lInputFingerPrint = aInReferenceData.getIso19794_2Template();
        User lMatchedUser = null;

        log.debug("Executing matching algorithm with existing database");
        for(User lUser : lListOfUser){
            boolean lIsMatchFound  =  aInDevice.matchIso19794_2Templates(lInputFingerPrint, lUser.getFgIso());
            if(lIsMatchFound){
                lMatchedUser = lUser;
                break;
            }
        }

        if(lMatchedUser != null){
            log.debug("User Found with Name: "+lMatchedUser.getName());
            return dao.findUserOnBankDetails(lMatchedUser, aInBankName);
        }else{
            log.error("Failed to find corresponding user in Database");
            return null;
        }
    }
}
