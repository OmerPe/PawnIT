package com.colman.pawnit.Ui.Market;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.pawnit.Model.Listing;
import com.colman.pawnit.Model.Model;

import java.util.List;

public class MarketListViewModel extends ViewModel {

    LiveData<List<Listing>> data;

    public MarketListViewModel() {
        data = Model.instance.getMarketList();
    }

    public LiveData<List<Listing>> getData(){
        return data;
    }

}
