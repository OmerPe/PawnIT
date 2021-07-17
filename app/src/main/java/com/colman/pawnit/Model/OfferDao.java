package com.colman.pawnit.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

@Dao
public interface OfferDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Listing listing);

    @Update
    void update(Listing listing);

    @Delete
    void delete(Listing listing);
}
