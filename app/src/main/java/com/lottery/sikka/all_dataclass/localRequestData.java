package com.lottery.sikka.all_dataclass;

public class localRequestData {
    String trx,date,bankname,status,type,tk;

    public localRequestData(String trx, String date, String bankname, String status, String type, String tk) {
        this.trx = trx;
        this.date = date;
        this.bankname = bankname;
        this.status = status;
        this.type = type;
        this.tk = tk;
    }
    public localRequestData(){

    }

    public String getTrx() {
        return trx;
    }

    public void setTrx(String trx) {
        this.trx = trx;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }
}
