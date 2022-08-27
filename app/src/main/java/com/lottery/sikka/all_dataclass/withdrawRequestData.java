package com.lottery.sikka.all_dataclass;

public class withdrawRequestData {

    String amount,number,bankName,time,user;

    public withdrawRequestData(String amount, String number, String bankName,String time,String user) {
        this.amount = amount;
        this.number = number;
        this.bankName = bankName;
        this.time =time;
        this.user =user;
    }

    public withdrawRequestData() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
