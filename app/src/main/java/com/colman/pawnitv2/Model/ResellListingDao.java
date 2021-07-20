package com.colman.pawnitv2.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ResellListingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ResellListing... listing);

    @Update
    void update(ResellListing listing);

    @Delete
    void delete(ResellListing listing);

    @Query("SELECT * FROM resell_table")
    LiveData<List<ResellListing>> getAllResells();

    @Query("SELECT * FROM resell_table WHERE listingID = :id")
    ResellListing getResellListing(int id);
}
