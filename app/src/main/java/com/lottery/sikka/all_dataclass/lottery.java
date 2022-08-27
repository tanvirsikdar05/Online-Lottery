package com.lottery.sikka.all_dataclass;

public class lottery {
    String id,details,img,name,drawDate;
    int price;

    public lottery(String id, String details, String img,int price,String name,String drawDate) {
        this.id = id;
        this.details = details;
        this.img = img;
        this.price=price;
        this.name=name;
        this.drawDate=drawDate;
    }

    public lottery() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
