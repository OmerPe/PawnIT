package com.colman.pawnit.Ui.Market;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.ResellListing;

import java.util.List;

public class ResellListViewModel extends ViewModel {

    LiveData<List<ResellListing>> data;

    public ResellListViewModel(){data = Model.instance.getResellData();}

    public LiveData<List<ResellListing>> getData(){return data;}
}
