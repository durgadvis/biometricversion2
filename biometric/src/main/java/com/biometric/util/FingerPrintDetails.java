package com.biometric.util;

import java.io.Serializable;

/**
 * Created by suvp on 11/7/2016.
 */
public class FingerPrintDetails implements Serializable {
    private String fgIso;
    private String fgBmp;

    public FingerPrintDetails(){}

    public String getFgIso() {
        return fgIso;
    }

    public void setFgIso(String fgIso) {
        this.fgIso = fgIso;
    }

    public String getFgBmp() {
        return fgBmp;
    }

    public void setFgBmp(String fgBmp) {
        this.fgBmp = fgBmp;
    }
}
