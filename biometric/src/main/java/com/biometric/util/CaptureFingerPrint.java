package com.biometric.util;

import com.biometric.controller.HomepPageController;
import mmm.cogent.fpCaptureApi.MMMCogentCSD200DeviceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by suvp on 11/3/2016.
 */
public class CaptureFingerPrint {
    private static final Logger homeControllerLogger = LoggerFactory.getLogger(HomepPageController.class);

    MMMCogentCSD200DeviceImpl device_;
    CallbackFunction callbackFunction_;
    Integer sessionId_;

    CaptureFingerPrint(MMMCogentCSD200DeviceImpl aInDevice, CallbackFunction aInCallbackFunction, Integer aInSessionId){
        device_ = aInDevice;
        callbackFunction_ = aInCallbackFunction;
        sessionId_ = aInSessionId;
    }

    public int captureFingerPrint(){
        homeControllerLogger.trace("Starting capture");
        return device_.startCapture(callbackFunction_, sessionId_, 30);
    }
}
