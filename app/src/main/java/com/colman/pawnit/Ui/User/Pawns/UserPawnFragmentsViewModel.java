package com.colman.pawnit.Ui.User.Pawns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.PawnListing;

import java.util.List;

public class UserPawnFragmentsViewModel extends ViewModel {
    LiveData<List<PawnListing>> data;

    public UserPawnFragmentsViewModel(){
        data = Model.instance.getAllPawnListings();
    }

    public LiveData<List<PawnListing>> getData() {
        return data;
    }
}