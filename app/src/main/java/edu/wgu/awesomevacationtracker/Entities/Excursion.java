package edu.wgu.awesomevacationtracker.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

@Entity(tableName ="EXCURSIONS")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private int excursionVID;
    private String excursionVacationTitle;
    private String excursionVacationStartDate;
    private String excursionVacationEndDate;
    private String excursionTitle;
    private String excursionDate;
    private double excursionPrice;

    static DateTimeFormatter dateFormatter;

    public Excursion(int excursionID, int excursionVID, String excursionVacationTitle, String excursionVacationStartDate, String excursionTitle, String excursionDate, double excursionPrice) {
        dateFormatter = DateTimeFormatter.ofPattern("MM-dd-uuuu", Locale.ENGLISH).withResolverStyle(ResolverStyle.STRICT);
        this.excursionID = excursionID;
        this.excursionVID = excursionVID;
        this.excursionVacationTitle = excursionVacationTitle;
        this.excursionVacationStartDate = excursionVacationStartDate;
        this.excursionTitle = excursionTitle;
        if(excursionDate.equals("")){
            this.excursionDate = excursionVacationStartDate;
        }else {
            this.excursionDate = excursionDate;
        }
        this.excursionPrice = excursionPrice;
    }

    public int getExcursionVID() {
        return excursionVID;
    }
    public void setExcursionVID(int excursionVID) {
        this.excursionVID = excursionVID;
    }

    public String getExcursionVacationStartDate() {
        return excursionVacationStartDate;
    }
    public void setExcursionVacationStartDate(String excursionVacationStartDate) {
        this.excursionVacationStartDate = excursionVacationStartDate;
    }
    public String getExcursionVacationEndDate() {
        return excursionVacationEndDate;
    }
    public void setExcursionVacationEndDate(String excursionVacationEndDate) {
        this.excursionVacationEndDate = excursionVacationEndDate;
    }

    public void setExcursionVacationTitle(String excursionVacationTitle) {
        this.excursionVacationTitle = excursionVacationTitle;
    }
    public String getExcursionVacationTitle() {
        return excursionVacationTitle;
    }

    public int getExcursionID() {
        return excursionID;
    }
    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }


    public String getExcursionTitle() {
        return excursionTitle;
    }
    public void setExcursionTitle(String excursionTitle) {
        this.excursionTitle = excursionTitle;
    }

    public String getExcursionDate() {
        return excursionDate;
    }
    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }

    public double getExcursionPrice() {
        return excursionPrice;
    }
    public void setExcursionPrice(double excursionPrice) {
        this.excursionPrice = excursionPrice;
    }
}

