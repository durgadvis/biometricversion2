package com.biometric.forms;

import com.biometric.util.BankNames;

import javax.persistence.*;
import javax.smartcardio.Card;
import java.io.Serializable;

/**
 * Created by suvp on 11/7/2016.
 */
@Entity
@Table(name = "carddetails1")
public class CardDetails implements Serializable{

    @Id
    @Column(name="pk")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pk;

    @Column(nullable = false)
    private String nameOnCard;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private int cvv;

    @Column(nullable = false)
    private String expiryDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BankNames bankName;

    @Transient
    private int fk;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    public CardDetails(String aInNameOnCard, String aInCardNumber, int aInCvv, String aInExpiryDate, BankNames aInBankName, int aInFk){
        this.nameOnCard =aInNameOnCard;
        this.cardNumber =aInCardNumber;
        this.cvv =aInCvv;
        this.expiryDate =aInExpiryDate;
        this.bankName =aInBankName;
        this.fk =aInFk;
    }

    public CardDetails(String aInNameOnCard, String aInCardNumber,String aInExpiryDate ){
        this.nameOnCard =aInNameOnCard;
        this.cardNumber =aInCardNumber;
        this.expiryDate =aInExpiryDate;
    }

    public CardDetails(){

    }

    public int getFk() {
        return fk;
    }

    public void setFk(int fk) {
        this.fk = fk;
    }


    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BankNames getBankName() {
        return bankName;
    }

    public void setBankName(BankNames bankName) {
        this.bankName = bankName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
