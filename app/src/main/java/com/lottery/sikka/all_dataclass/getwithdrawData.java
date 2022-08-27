package com.lottery.sikka.all_dataclass;

public class getwithdrawData {
    String msg;
    int minBlance;

    public getwithdrawData(String msg, int minBlance) {
        this.msg = msg;
        this.minBlance = minBlance;
    }

    public getwithdrawData() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getMinBlance() {
        return minBlance;
    }

    public void setMinBlance(int minBlance) {
        this.minBlance = minBlance;
    }
}
