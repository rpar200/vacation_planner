package com.erde.erde_vacation_planner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.erde.erde_vacation_planner.entities.Vacation;

import java.util.List;

@Dao
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM Vacation ORDER BY vacationID ASC")
    List<Vacation> getAllVacations();

    @Query("SELECT * FROM Vacation WHERE owner = :owner ORDER BY vacationID ASC")
    List<Vacation> getAllVacationsWithOwner(String owner);
}
