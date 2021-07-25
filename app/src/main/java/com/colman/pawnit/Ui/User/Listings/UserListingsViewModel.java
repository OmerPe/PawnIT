package com.colman.pawnit.Ui.User.Listings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.pawnit.Model.Listing;
import com.colman.pawnit.Model.Model;


import java.util.List;

public class UserListingsViewModel extends ViewModel {
    LiveData<List<Listing>> data;

    public UserListingsViewModel(){
        data = Model.instance.getMarketList("0");
    }

    public LiveData<List<Listing>> getData() {
        return data;
    }
}