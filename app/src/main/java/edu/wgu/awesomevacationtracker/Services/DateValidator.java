package edu.wgu.awesomevacationtracker.Services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class DateValidator {
    private String date1;
    private String date2;
    static DateTimeFormatter dateFormatter;
    static LocalDate dateToday;

    public DateValidator(){
        dateToday = LocalDate.now();
        dateFormatter = DateTimeFormatter.ofPattern("MM-dd-uuuu", Locale.ENGLISH).withResolverStyle(ResolverStyle.STRICT);
    }

    public static boolean isDateOK(String date){
        boolean answer1 = isDateInCorrectFormat(date);
        boolean answer2 = false;
        if(answer1) {
            answer2 = isDateAfterToday(date);
        }

        if(answer1 && answer2) {
            return true;
        }else{
            return false;
        }
    }

    public static boolean isDateInCorrectFormat(String date){
        try {
            LocalDate.parse(date, dateFormatter);
        }catch (DateTimeParseException e) {
            System.out.println("ISDATEINCORRECTFORMAT EXCEPTION : "+e);
            return false;
        }
        return true;
    }

    public static boolean isDateAfterToday(String date){
        LocalDate dateTemp = LocalDate.parse(date, dateFormatter);
        boolean temp = dateTemp.isAfter(dateToday);
        //System.out.println("ISDATEAFTERTODAY BOOLEAN : "+temp);
        return temp;
    }

    public static boolean isEndAfterStart(String date1, String date2){
        LocalDate start = LocalDate.parse(date1, dateFormatter);
        LocalDate end = LocalDate.parse(date2, dateFormatter);
        boolean temp = end.isAfter(start);
        return temp;
    }

    public static boolean isExcursionDateOK(String excursionDate, String vacationStartDate, String vacationEndDate){
        boolean check1 = isDateInCorrectFormat(excursionDate);
        if(check1) {
            LocalDate exDateTemp = LocalDate.parse(excursionDate, dateFormatter);
            LocalDate exVStart = LocalDate.parse(vacationStartDate, dateFormatter);
            LocalDate exVEnd = LocalDate.parse(vacationEndDate, dateFormatter);
            boolean check2 = exDateTemp.isAfter(exVStart.minusDays(1));
            boolean check3 = exDateTemp.isBefore(exVEnd.plusDays(1));
            if(check2 && check3){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public static LocalDate parseThis(String date){
        LocalDate temp = LocalDate.parse(date, dateFormatter);
        return temp;
    }

    public static LocalDate getToday() {
        return LocalDate.now();
    }

}
