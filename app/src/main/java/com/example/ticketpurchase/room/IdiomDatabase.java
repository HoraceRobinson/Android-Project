package com.example.ticketpurchase.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//单例模式数据库对象
@Database(entities = {Idiom.class}, version = 1, exportSchema = false)
public abstract class IdiomDatabase extends RoomDatabase {
    public abstract IdiomDao getIdiomDao();
    private static IdiomDatabase INSTANCE;
    public static synchronized IdiomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),IdiomDatabase.class,"Idiom_database").build();
        }
        return INSTANCE;
    }
}