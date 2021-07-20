package com.colman.pawnit.Ui.Pawn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.PawnListing;

import java.util.List;

public class PawnViewModel extends ViewModel {
    LiveData<List<PawnListing>> data;

    public PawnViewModel(){data = Model.instance.getAllPawnListings();}

    public LiveData<List<PawnListing>> getData(){return data;}
}