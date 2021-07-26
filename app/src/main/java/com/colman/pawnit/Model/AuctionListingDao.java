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
public interface AuctionListingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AuctionListing... listing);

    @Update
    void update(AuctionListing listing);

    @Delete
    void delete(AuctionListing listing);

    @Query("SELECT * FROM auction_listings_table ORDER BY dateOpened DESC")
    LiveData<List<AuctionListing>> getAllAuctions();

    @Query("SELECT * FROM auction_listings_table WHERE listingID = :id ORDER BY dateOpened DESC")
    AuctionListing getAuctionListing(String id);

    @Query("SELECT * FROM auction_listings_table WHERE ownerId = :uid ORDER BY dateOpened DESC")
    LiveData<List<AuctionListing>> getAllAuctionsPerUser(String uid);
}
