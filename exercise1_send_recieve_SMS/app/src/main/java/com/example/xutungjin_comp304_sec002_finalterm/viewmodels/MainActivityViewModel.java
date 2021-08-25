package com.example.xutungjin_comp304_sec002_finalterm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.xutungjin_comp304_sec002_finalterm.data.StockInfo;

public class MainActivityViewModel extends ViewModel {

    MutableLiveData<StockInfo> stocks;

    public MainActivityViewModel(){
        stocks = new MutableLiveData<StockInfo>();
        //stocks.postValue(some_stock_value);
    }

    // Return stocks from view model to main activity
    public LiveData<StockInfo> getStocks(){
        return stocks;
    }

    public void insert_button(){ }

    public void display_click(){ }
}