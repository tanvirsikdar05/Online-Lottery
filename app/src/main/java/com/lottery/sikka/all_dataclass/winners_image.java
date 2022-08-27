package com.lottery.sikka.all_dataclass;

public class winners_image {
    String doc,url;

    public winners_image(String doc, String url) {
        this.doc = doc;
        this.url = url;
    }

    public winners_image() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String details) {
        this.doc = details;
    }
}
