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
public interface PawnDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Pawn... pawn);

    @Update
    void update(Pawn pawn);

    @Delete
    void delete(Pawn pawn);

    @Query("SELECT * FROM PAWN_TABLE WHERE borrowerId = :uid")
    LiveData<List<Pawn>> getAllPawnsForUser(String uid);
}
