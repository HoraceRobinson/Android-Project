package com.example.ticketpurchase.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IdiomDao {

    @Insert
    void insertIdioms(Idiom ...idioms);

    @Delete
    void deleteIdioms(Idiom ...idioms);

    @Query("select * from idioms")
    List<Idiom> getAllIdioms();

    @Query("select * from idioms where idiom = :idiom")
    Idiom getTheIdiom(String idiom);

    @Query("delete from idioms where idiom = :idiom")
    void deleteTheIdiom(String idiom);
}
