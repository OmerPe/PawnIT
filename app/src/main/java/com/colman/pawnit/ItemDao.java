package com.colman.pawnit;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);
    @Update
    void update(Item item);
    @Delete
    void delete(Item item);
    @Query("DELETE FROM item_tablev2")
    void deleteAllItems();
    @Query("SELECT * FROM item_tablev2 ORDER BY price DESC")
    LiveData<List<Item>> getAllItems();
}
