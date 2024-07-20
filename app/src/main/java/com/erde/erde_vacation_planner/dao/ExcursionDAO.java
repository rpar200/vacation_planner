package com.erde.erde_vacation_planner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.erde.erde_vacation_planner.entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM Excursion ORDER BY excursionID ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM Excursion WHERE owner = :owner ORDER BY excursionID ASC")
    List<Excursion> getAllExcursionsWithOwner(String owner);

    @Query("SELECT * FROM Excursion WHERE vacationID=:vacation AND owner == Excursion.owner ORDER BY excursionID")
    List<Excursion> getAssociatedExcursions(int vacation);

}
