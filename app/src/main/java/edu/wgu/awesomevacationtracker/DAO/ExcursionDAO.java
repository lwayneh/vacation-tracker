package edu.wgu.awesomevacationtracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.wgu.awesomevacationtracker.Entities.Excursion;

@Dao
public interface ExcursionDAO {

   // @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM EXCURSIONS WHERE excursionVID LIKE :vacationID ORDER BY excursionID ASC")
    List<Excursion> getAllExcursionsForThisVacation(int vacationID);

    @Query("SELECT * FROM EXCURSIONS ORDER BY excursionID ASC")
    List<Excursion> getAllExcursions();

}
