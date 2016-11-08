package com.biometric.util;

import com.biometric.controller.MyDetails;
import mmm.cogent.fpCaptureApi.CapturedImageData;
import mmm.cogent.fpCaptureApi.MMMCogentCSD200DeviceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by suvp on 11/7/2016.
 */
public class Util {
    private static final Logger log = LoggerFactory.getLogger(MyDetails.class);
    
    public static CapturedImageData captureFingerPrint(Boolean aInShouldDenit, MMMCogentCSD200DeviceImpl aInDevice){
        log.info("Started Capturing Image");
        int lRet = aInDevice.initDevice();
        CapturedImageData lCapturedImage = null;

        if(lRet == 0) {
            log.info("Device Successfully Initialized");

            //Capture First image
            CallbackFunction lFirstCallBackFunction = new CallbackFunction("First Image");
            CaptureFingerPrint lFirstCaptureFingerPrint = new CaptureFingerPrint(aInDevice, lFirstCallBackFunction, 1);
            int lRetCode = lFirstCaptureFingerPrint.captureFingerPrint();

            if (lRetCode == 0) {
                log.info("Capture return code is successful");
                while (!lFirstCallBackFunction.isCaptureComplete()) {
                    ;
                }
                lCapturedImage = lFirstCallBackFunction.getCapturedImageData_();
            } else {
                log.info("Capture was un-successful");
            }
            log.info("Finger print action completed");
        }else{
            log.info("Failed to initialize the device");
        }

        if(aInShouldDenit){
            log.info("De initializing the device");
            aInDevice.deinitDevice();
        }

        log.info("Ended Capturing Image");
        return  lCapturedImage;
    }
}
