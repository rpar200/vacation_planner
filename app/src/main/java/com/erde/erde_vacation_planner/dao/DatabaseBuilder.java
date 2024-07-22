package com.erde.erde_vacation_planner.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.erde.erde_vacation_planner.entities.Excursion;
import com.erde.erde_vacation_planner.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 23, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    // Inheritance can be seen here. The abstract class DatabaseBuilder inherits from RoomDatabase.
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DatabaseBuilder.class, "VacationTrackerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
