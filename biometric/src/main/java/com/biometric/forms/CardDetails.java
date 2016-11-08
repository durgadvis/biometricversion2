package com.biometric.forms;

/**
 * Created by suvp on 11/7/2016.
 */
public class CardDetails {

    private int pk;
    private String nameOnCard;
    private String cardNumber;
    private int cvv;
    private String expiryDate;
    private String bankName;
    private int fk;

    public CardDetails(String aInNameOnCard, String aInCardNumber, int aInCvv, String aInExpiryDate, String aInBankName, int aInFk){
        this.nameOnCard =aInNameOnCard;
        this.cardNumber =aInCardNumber;
        this.cvv =aInCvv;
        this.expiryDate =aInExpiryDate;
        this.bankName =aInBankName;
        this.fk =aInFk;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
