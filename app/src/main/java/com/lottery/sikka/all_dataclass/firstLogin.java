package com.lottery.sikka.all_dataclass;

public class firstLogin {
    String notification,token,request;
    int blance;

    public firstLogin(int blance, String notification, String token,String request) {
        this.blance = blance;
        this.notification = notification;
        this.token = token;
        this.request =request;
    }

    public firstLogin() {
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getBlance() {
        return blance;
    }

    public void setBlance(int blance) {
        this.blance = blance;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
