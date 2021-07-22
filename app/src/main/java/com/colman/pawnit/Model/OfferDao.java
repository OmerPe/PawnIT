package com.colman.pawnit.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OfferDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Offer... offer);

    @Update
    void update(Offer offer);

    @Delete
    void delete(Offer offer);

    @Query("SELECT * FROM offer_table WHERE pawnListingID = :listingID")
    LiveData<List<Offer>> getAllOffersForListing(String listingID);
}
