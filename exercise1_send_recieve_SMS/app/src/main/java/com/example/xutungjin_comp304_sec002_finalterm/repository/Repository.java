package com.example.xutungjin_comp304_sec002_finalterm.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.room.Room;

import com.example.xutungjin_comp304_sec002_finalterm.data.Room_database;
import com.example.xutungjin_comp304_sec002_finalterm.data.StockInfo;

import java.util.List;

public class Repository {

    Room_database db;
    Context context;

    public Repository(Context context){
        this.context = context;

        // Create ubs
        db = Room_database.getDbInstance(context);
        // Clean all data first
        db.stock_DAO().nukeTable();
        Toast.makeText(context,"Stock table created.",Toast.LENGTH_SHORT).show();
    }

    public void insertStock(){

        // Insert hardcore data
        StockInfo amazon = new StockInfo("AMZN","Amazon",980);
        StockInfo google = new StockInfo("GOOGL","Google",970);
        StockInfo samsung = new StockInfo("SSNLF","Samsung",990);

        db.stock_DAO().insertStock(amazon);
        db.stock_DAO().insertStock(google);
        db.stock_DAO().insertStock(samsung);

        Toast.makeText(context,"Hardcore data inserted.",Toast.LENGTH_SHORT).show();
    }

    public List<StockInfo> getAllStocks()
    {
        return db.stock_DAO().getAll();
    }
}
