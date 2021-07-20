package com.colman.pawnit.Ui.Market;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.pawnit.Model.AuctionListing;
import com.colman.pawnit.Model.Model;

import java.util.List;

public class AuctionListViewmodel extends ViewModel {

    LiveData<List<AuctionListing>> data;

    public AuctionListViewmodel(){data = Model.instance.getAuctionData();}

    public LiveData<List<AuctionListing>> getData(){return data;}

}
