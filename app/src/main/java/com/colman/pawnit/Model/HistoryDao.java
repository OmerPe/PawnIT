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
public interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(History... history);

    @Update
    void update(History history);

    @Delete
    void delete(History history);

    @Query("SELECT * FROM history_table WHERE uid = :uid")
    LiveData<List<History>> getAllHistoryForUser(String uid);
}
