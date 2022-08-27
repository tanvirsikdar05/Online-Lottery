package com.lottery.sikka.all_dataclass;

public class notificationDataClass {

    String notificationTitle,notificationBody,notificationTime;

    public notificationDataClass(String notificationTitle, String notificationBody, String notificationTime) {
        this.notificationTitle = notificationTitle;
        this.notificationBody = notificationBody;
        this.notificationTime = notificationTime;
    }

    public notificationDataClass() {
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }
}
