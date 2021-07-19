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
public interface ListingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Listing... listing);

    @Update
    void update(Listing listing);

    @Delete
    void delete(Listing listing);

    @Query("SELECT * FROM listings_table")
    LiveData<List<Listing>> getAllListings();

}
