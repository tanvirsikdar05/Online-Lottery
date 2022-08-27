package com.lottery.sikka.all_dataclass;

public class getDepositeData {
    String msg,status,bankNumber;
    int min;

    public getDepositeData(String msg, String status, int min, String bankNumber) {
        this.msg = msg;
        this.status = status;
        this.min = min;
        this.bankNumber = bankNumber;
    }
    public getDepositeData(){

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getBankNUmber() {
        return bankNumber;
    }

    public void setBankNUmber(String bankNUmber) {
        this.bankNumber = bankNUmber;
    }
}
