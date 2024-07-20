package com.erde.erde_vacation_planner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String excursionName;
    // foreign key
    private int vacationID;
    private String startDate;
    private String owner;

    public Excursion(int excursionID, String excursionName, int vacationID, String startDate, String owner) {
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.vacationID = vacationID;
        this.startDate = startDate;
        this.owner = owner;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner() {
        this.owner = owner;
    }
}
