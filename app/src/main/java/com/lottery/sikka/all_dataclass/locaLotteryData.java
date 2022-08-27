package com.lottery.sikka.all_dataclass;

public class locaLotteryData {
    String lotteryName,lotteryNumber,BuyDate,DrawDate,Status;

    public locaLotteryData(String lotteryName, String lotteryNumber, String buyDate, String drawDate, String status) {
        this.lotteryName = lotteryName;
        this.lotteryNumber = lotteryNumber;
        this.BuyDate = buyDate;
        this.DrawDate = drawDate;
        this.Status = status;
    }

    public locaLotteryData() {
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getLotteryNumber() {
        return lotteryNumber;
    }

    public void setLotteryNumber(String lotteryNumber) {
        this.lotteryNumber = lotteryNumber;
    }

    public String getBuyDate() {
        return BuyDate;
    }

    public void setBuyDate(String buyDate) {
        BuyDate = buyDate;
    }

    public String getDrawDate() {
        return DrawDate;
    }

    public void setDrawDate(String drawDate) {
        DrawDate = drawDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
