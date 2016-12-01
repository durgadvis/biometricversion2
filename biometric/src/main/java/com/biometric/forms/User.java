package com.biometric.forms;

import com.biometric.util.BankNames;
import org.hibernate.type.BlobType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by suvp on 11/7/2016.
 */
@Entity
@Table(name = "userdetails1")
public class User implements Serializable{

    @Column(length = 45, nullable = false)
    private String name;

    @Column(nullable = false)
    private Long phonenumber ;

    @Column(length = 45, nullable = false)
    private String emailId;

    @Column(length = 45, nullable = false)
    private String address;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] fgIso;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] fgBmp;

    @Transient
    private BankNames bankName;

    //In model to retrieve the selected card.
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CardDetails> listCardDetails;

    @Id
    @Column(name="pk")
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public BankNames getBankName() {
        return bankName;
    }

    public void setBankName(BankNames bankName) {
        this.bankName = bankName;
    }


}
