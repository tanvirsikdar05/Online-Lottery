package com.lottery.sikka.all_dataclass;

public class fourplayerData {

    String name,date,prize,type,board,roomcode,winner,playerone,playertwo,
    playerthree,playerfour,childkey;
    int fee;

    public fourplayerData(String name, String date, String prize, String type, String board, String roomcode, String winner, String playerone, String playertwo, String playerthree, String playerfour, String childkey, int fee) {
        this.name = name;
        this.date = date;
        this.prize = prize;
        this.type = type;
        this.board = board;
        this.roomcode = roomcode;
        this.winner = winner;
        this.playerone = playerone;
        this.playertwo = playertwo;
        this.playerthree = playerthree;
        this.playerfour = playerfour;
        this.childkey = childkey;
        this.fee = fee;
    }

    public fourplayerData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getRoomcode() {
        return roomcode;
    }

    public void setRoomcode(String roomcode) {
        this.roomcode = roomcode;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getPlayerone() {
        return playerone;
    }

    public void setPlayerone(String playerone) {
        this.playerone = playerone;
    }

    public String getPlayertwo() {
        return playertwo;
    }

    public void setPlayertwo(String playertwo) {
        this.playertwo = playertwo;
    }

    public String getPlayerthree() {
        return playerthree;
    }

    public void setPlayerthree(String playerthree) {
        this.playerthree = playerthree;
    }

    public String getPlayerfour() {
        return playerfour;
    }

    public void setPlayerfour(String playerfour) {
        this.playerfour = playerfour;
    }

    public String getChildkey() {
        return childkey;
    }

    public void setChildkey(String childkey) {
        this.childkey = childkey;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
