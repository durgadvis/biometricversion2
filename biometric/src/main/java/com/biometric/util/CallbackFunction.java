package com.biometric.util;

import com.biometric.controller.HomepPageController;
import mmm.cogent.fpCaptureApi.CapturedImageData;
import mmm.cogent.fpCaptureApi.IFingerprintCaptureCallbackAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by suvp on 11/3/2016.
 */
public class CallbackFunction implements IFingerprintCaptureCallbackAPI {
    private static final Logger homeControllerLogger = LoggerFactory.getLogger(HomepPageController.class);
    volatile private boolean isCaptureComplete = false;

    private CapturedImageData capturedImageData_;
    private String functionName_;

    CallbackFunction(String aInFunctionName){
        this.functionName_ = aInFunctionName;
    }

    @Override
    public void onPreviewImageAvailable(int i, byte[] bytes, int i1, int i2) {
        homeControllerLogger.trace("Preview action completed");
    }

    @Override
    public void onFingerprintCaptureCompleted(int i, CapturedImageData capturedImageData) {
        capturedImageData_ = capturedImageData;
        homeControllerLogger.debug(functionName_ +" : "+"The captured image Bmp Image is"+capturedImageData_.getBmpImageData());
        homeControllerLogger.debug(functionName_ +" : "+"The captured image Iso is"+capturedImageData_.getIso19794_2Template());
        homeControllerLogger.debug(functionName_ +" : "+ "Capture action Completed");
        isCaptureComplete = true;
}

    public boolean isCaptureComplete() {
        return isCaptureComplete;
    }

    public void setCaptureComplete(boolean captureComplete) {
        isCaptureComplete = captureComplete;
    }

    public CapturedImageData getCapturedImageData_() {
        return capturedImageData_;
    }
}
