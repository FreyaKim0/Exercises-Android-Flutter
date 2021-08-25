package com.example.xutungjin_comp304_sec002_finalterm.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StockInfo {

    // Attributes
    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String stockSymbol=""; //primary key

    @ColumnInfo(name="Company Name")
    public String companyName;

    @ColumnInfo(name="Stock Quote")
    public double stockQuote;

    // getter and setter
    public String getStockSymbol() {
        return stockSymbol;
    }
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public double getStockQuote() {
        return stockQuote;
    }
    public void setStockQuote(double stockQuote) {
        this.stockQuote = stockQuote;
    }

    // Constructor
    public StockInfo(String stockSymbol, String companyName, double stockQuote) {
        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
        this.stockQuote = stockQuote;
    }
}
