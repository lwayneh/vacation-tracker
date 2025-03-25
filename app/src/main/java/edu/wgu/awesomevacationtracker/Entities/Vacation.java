package edu.wgu.awesomevacationtracker.Entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

import edu.wgu.awesomevacationtracker.Services.DateConverter;


@Entity(tableName ="vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int vacationID;

    private String vacationTitle;

    private String vacationHotel;

    private double vacationPrice;

    @TypeConverters(DateConverter.class)
    private String vacationStartDate; // FORMAT mm-dd-yyyy

    @TypeConverters(DateConverter.class)
    private String vacationEndDate;

    static DateTimeFormatter dateFormatter;
    static LocalDate dateToday;


    public Vacation(int vacationID, String vacationTitle, String vacationHotel, double vacationPrice, String vacationStartDate, String vacationEndDate
    ) {
        dateToday = LocalDate.now();
        dateFormatter = DateTimeFormatter.ofPattern("MM-dd-uuuu", Locale.ENGLISH).withResolverStyle(ResolverStyle.STRICT);
        this.vacationID = vacationID;
        this.vacationTitle = vacationTitle;
        this.vacationHotel = vacationHotel;
        this.vacationPrice = vacationPrice;
        if(vacationStartDate.equals("")){
            LocalDate startTemp = dateToday.plusDays(1);
            this.vacationStartDate = dateFormatter.format(startTemp);
        }else {
            this.vacationStartDate = vacationStartDate;
        }
        if(vacationEndDate.equals("")){
            LocalDate endTemp = dateToday.plusDays(2);
            this.vacationEndDate = dateFormatter.format(endTemp);
        }else {
            this.vacationEndDate = vacationEndDate;
        }
    }

    public int getVacationID() {
        return vacationID;
    }
    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationTitle() {
        return vacationTitle;
    }
    public void setVacationTitle(String vacationTitle) {
        this.vacationTitle = vacationTitle;
    }


    public String getVacationHotel() {
        return vacationHotel;
    }
    public void setVacationHotel(String vacationHotel) {
        this.vacationHotel = vacationHotel;
    }

    public double getVacationPrice() {
        return vacationPrice;
    }
    public void setVacationPrice(double vacationPrice) {
        this.vacationPrice = vacationPrice;
    }

    public String getVacationStartDate() {
        return vacationStartDate;
    }
    public void setVacationStartDate(String vacationStartDate) {
        this.vacationStartDate = vacationStartDate;
    }

    public String getVacationEndDate() {
        return vacationEndDate;
    }
    public void setVacationEndDate(String vacationEndDate) {
        this.vacationEndDate = vacationEndDate;
    }


}