package com.colman.pawnit.Ui.Market;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.pawnit.Model.Listing;
import com.colman.pawnit.Model.Model;

import java.util.List;

public class MarketViewModel extends ViewModel {
    LiveData<List<Listing>> data;

    public LiveData<List<Listing>> getData(){
        data = Model.instance.getMarketList();
        return data;
    }
}