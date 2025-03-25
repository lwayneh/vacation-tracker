package edu.wgu.awesomevacationtracker.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.awesomevacationtracker.Services.DateConverter;
import edu.wgu.awesomevacationtracker.DAO.ExcursionDAO;
import edu.wgu.awesomevacationtracker.DAO.VacationDAO;
import edu.wgu.awesomevacationtracker.Entities.Excursion;
import edu.wgu.awesomevacationtracker.Entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class VacationTrackerDBBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();

    private static volatile VacationTrackerDBBuilder INSTANCE;

    static VacationTrackerDBBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (VacationTrackerDBBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),VacationTrackerDBBuilder.class,"VacationTracker.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

