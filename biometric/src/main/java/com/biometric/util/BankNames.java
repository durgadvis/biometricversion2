package com.biometric.util;

/**
 * Created by suvp on 11/14/2016.
 */
public enum  BankNames {
    SBI ("SBI"),
    SBM ("SBM"),
    CANARA ("Canara") ,
    CORPORATION ("Corporation") ,
    HDFC ("HDFC"),
    AXIS ("AXIS");

    private String name;

    BankNames(String aInName){
       this.name = aInName;
    }

    BankNames(){}

    public String getName() {
        return name;
    }
}
