package com.biometric.forms;

import com.biometric.util.BankNames;

import javax.smartcardio.Card;

/**
 * Created by suvp on 11/7/2016.
 */
public class CardDetails {

    private int pk;
    private String nameOnCard;
    private String cardNumber;
    private int cvv;
    private String expiryDate;
    private BankNames bankName;
    private int fk;

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
}
