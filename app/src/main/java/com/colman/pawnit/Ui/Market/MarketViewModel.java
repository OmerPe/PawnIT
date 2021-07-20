package com.colman.pawnitv2.Ui.Market;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.pawnitv2.Model.Listing;
import com.colman.pawnitv2.Model.Model;

import java.util.List;

public class MarketViewModel extends ViewModel {
    LiveData<List<Listing>> data;

    public LiveData<List<Listing>> getData(){
        data = Model.instance.getMarketList();
        return data;
    }
}