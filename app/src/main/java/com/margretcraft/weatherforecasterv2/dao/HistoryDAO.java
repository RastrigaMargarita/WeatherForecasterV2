package com.margretcraft.weatherforecasterv2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface HistoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHistoryRecord(History history);

    @Update
    void updateHistoryRecord(History history);

    @Delete
    void deleteHistoryRecord(History history);

    @Query("Select * from history order by data desc")
    History[] selectAll();

    @Query("Select id from history where data = :data and town = :town")
    long selectByDataTown(long data, String town);

    @Query("SELECT COUNT() FROM history")
    long getCountRecords();
}
