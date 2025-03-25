package edu.wgu.awesomevacationtracker.Database;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.wgu.awesomevacationtracker.DAO.ExcursionDAO;
import edu.wgu.awesomevacationtracker.DAO.VacationDAO;
import edu.wgu.awesomevacationtracker.Entities.Excursion;
import edu.wgu.awesomevacationtracker.Entities.Vacation;

public class DBRepository {
    private VacationDAO mVacationDAO;
    private ExcursionDAO mExcursionDAO;
    private List<Vacation> mAllVacations;
    private Vacation mAVacation;
    private List<Excursion> mAllExcursions;
    private List<Excursion> mAllExcursionsForVacation;
    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor2 = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public DBRepository(Application application){
        VacationTrackerDBBuilder db = VacationTrackerDBBuilder.getDatabase(application);
        mExcursionDAO=db.excursionDAO();
        mVacationDAO=db.vacationDAO();
    }
    public List<Vacation> getAllVacations(){
        databaseExecutor2.execute(()->{
            mAllVacations=mVacationDAO.getAllVacations();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllVacations;
    }
    public Vacation getAVacation(int ID){
        databaseExecutor2.execute(()->{
            mAVacation=mVacationDAO.getAVacation(ID);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAVacation;
    }
    public void insert(Vacation vacation){
        databaseExecutor2.execute(()->{
            mVacationDAO.insert(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Vacation vacation){
        databaseExecutor2.execute(()->{
            mVacationDAO.update(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Vacation vacation){
        databaseExecutor2.execute(()->{
            mVacationDAO.delete(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Excursion>getAllExcursions(){
        databaseExecutor2.execute(()->{
            mAllExcursions=mExcursionDAO.getAllExcursions();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }
    public List<Excursion>getAllExcursionsForThisVacation(int excursionVID){
        Integer tempID = excursionVID;
        if(tempID == 0 || tempID == null){
            tempID = 0;
        }
        int temp = tempID;
        databaseExecutor2.execute(()->{
            mAllExcursionsForVacation=mExcursionDAO.getAllExcursionsForThisVacation(temp);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursionsForVacation;
    }
    public void insert(Excursion excursion){
        databaseExecutor2.execute(()->{
            mExcursionDAO.insert(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Excursion excursion){
        databaseExecutor2.execute(()->{
            mExcursionDAO.update(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Excursion excursion){
        databaseExecutor2.execute(()->{
            mExcursionDAO.delete(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
