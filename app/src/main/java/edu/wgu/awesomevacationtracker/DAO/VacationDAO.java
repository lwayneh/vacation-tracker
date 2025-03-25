package edu.wgu.awesomevacationtracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.wgu.awesomevacationtracker.Entities.Vacation;

@Dao
public interface VacationDAO {

   // @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM VACATIONS ORDER BY vacationID ASC")
    List<Vacation> getAllVacations();

    @Query("SELECT * FROM VACATIONS WHERE vacationID like :givenID")
    Vacation getAVacation(int givenID);


}
