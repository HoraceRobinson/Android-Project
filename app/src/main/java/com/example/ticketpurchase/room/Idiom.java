package com.example.ticketpurchase.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "idioms")
public class Idiom {

    @PrimaryKey
    @NonNull
    private String idiom;

    public Idiom(String idiom, String pinyin, String meaning) {
        this.idiom = idiom;
        this.pinyin = pinyin;
        this.meaning = meaning;
    }

    private String pinyin;
    private String meaning;

    public String getIdiom() {
        return idiom;
    }

    public void setIdiom(String idiom) {
        this.idiom = idiom;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getMeaning() {
        return meaning;
    }

    @Override
    public String toString() {
        return "Idiom{" +
                "idiom='" + idiom + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", meaning='" + meaning + '\'' +
                '}';
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
