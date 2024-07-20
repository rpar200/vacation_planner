package com.erde.erde_vacation_planner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private String housing;
    private String startDate;
    private String endDate;

    public Vacation(int vacationID, String vacationName, String housing, String startDate, String endDate) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.housing = housing;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public String getHousing() {
        return housing;
    }

    public void setHousing(String housing) {
        this.housing = housing;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String toString (){
        return vacationName;
    }
}