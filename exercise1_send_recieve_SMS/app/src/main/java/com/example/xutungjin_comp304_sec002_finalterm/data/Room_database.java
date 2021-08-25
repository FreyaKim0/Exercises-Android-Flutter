package com.example.xutungjin_comp304_sec002_finalterm.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities ={StockInfo.class},version=1,exportSchema = false)
public abstract class Room_database extends RoomDatabase {

    public abstract stock_DAO stock_DAO();
    private static Room_database INSTANCE;

    public static  Room_database getDbInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    Room_database.class,
                    "stock_table"
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
