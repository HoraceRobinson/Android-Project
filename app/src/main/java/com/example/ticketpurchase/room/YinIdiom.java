package com.example.ticketpurchase.room;

public class YinIdiom {
    private String idiom;
    private String correct;
    private String wrong1;
    private String wrong2;
    private String wrong3;

    public YinIdiom() {
    }

    public String getIdiom() {
        return idiom;
    }

    public void setIdiom(String idiom) {
        this.idiom = idiom;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getWrong1() {
        return wrong1;
    }

    public void setWrong1(String wrong1) {
        this.wrong1 = wrong1;
    }

    public String getWrong2() {
        return wrong2;
    }

    public void setWrong2(String wrong2) {
        this.wrong2 = wrong2;
    }

    public String getWrong3() {
        return wrong3;
    }

    public void setWrong3(String wrong3) {
        this.wrong3 = wrong3;
    }

    public YinIdiom(String idiom, String correct, String wrong1, String wrong2, String wrong3) {
        this.idiom = idiom;
        this.correct = correct;
        this.wrong1 = wrong1;
        this.wrong2 = wrong2;
        this.wrong3 = wrong3;
    }
}
