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
public interface PawnListingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(PawnListing... listing);

    @Update
    void update(PawnListing listing);

    @Delete
    void delete(PawnListing listing);

    @Query("SELECT * FROM pawn_listings_table")
    LiveData<List<PawnListing>> getAllPawns();

    @Query("SELECT * FROM pawn_listings_table WHERE listingID = :id ORDER BY dateOpened DESC")
    PawnListing getPawnListing(String id);

    @Query("SELECT * FROM pawn_listings_table WHERE ownerId = :uid ORDER BY dateOpened DESC")
    LiveData<List<PawnListing>> getAllPawnsPerUser(String uid);
}
