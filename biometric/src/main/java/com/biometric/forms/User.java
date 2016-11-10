package com.biometric.forms;

import java.io.Serializable;
import java.util.List;

/**
 * Created by suvp on 11/7/2016.
 */
public class User implements Serializable{

    private String name;
    private Long phonenumber ;
    private String emailId;
    private String address;
    private int age;
    private byte[] fgIso;
    private byte[] fgBmp;



    private List<CardDetails> listCardDetails;

    private int pk;

    public User(){}

    public User(String aInName, Long aInPhoneNumber, String aInEmail, String aInAddress, int aInAge, byte[] aInFgIso, byte[] aInFgBmp){
        this.name =aInName;
        this.phonenumber = aInPhoneNumber;
        this.address =aInAddress;
        this.emailId= aInEmail;
        this.age = aInAge;
        this.fgIso =aInFgIso;
        this.fgBmp =aInFgBmp;
    }

    public List<CardDetails> getListCardDetails() {
        return listCardDetails;
    }

    public void setListCardDetails(List<CardDetails> listCardDetails) {
        this.listCardDetails = listCardDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public byte[] getFgIso() {
        return fgIso;
    }

    public void setFgIso(byte[] fgIso) {
        this.fgIso = fgIso;
    }

    public byte[] getFgBmp() {
        return fgBmp;
    }

    public void setFgBmp(byte[] fgBmp) {
        this.fgBmp = fgBmp;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }
}
