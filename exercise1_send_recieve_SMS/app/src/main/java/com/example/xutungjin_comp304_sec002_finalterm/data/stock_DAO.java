package com.example.xutungjin_comp304_sec002_finalterm.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface stock_DAO {

    @Query("select * from StockInfo")
    List<StockInfo> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStock(StockInfo...stockInfos);

    @Query("DELETE FROM StockInfo")
    public void nukeTable();
}
