package com.lottery.sikka.all_dataclass;

public class depositeRequestData {

    String trnsNumber,amount,number,bankName,user;

    public depositeRequestData(String amount, String number, String trnsNumber,String bankName,String user) {
        this.amount = amount;
        this.number = number;
        this.trnsNumber = trnsNumber;
        this.bankName = bankName;
        this.user =user;
    }

    public depositeRequestData() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTrnsNumber() {
        return trnsNumber;
    }

    public void setTrnsNumber(String trnsNumber) {
        this.trnsNumber = trnsNumber;
    }
}
