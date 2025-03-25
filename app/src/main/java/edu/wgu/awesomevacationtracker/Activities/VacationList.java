package edu.wgu.awesomevacationtracker.Activities;

import static android.util.Log.ERROR;
import static android.util.Log.WARN;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.wgu.awesomevacationtracker.Database.DBRepository;
import edu.wgu.awesomevacationtracker.Entities.Vacation;
import edu.wgu.awesomevacationtracker.R;

public class VacationList extends AppCompatActivity {
    private DBRepository dbRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);

        dbRepository = new DBRepository(getApplication());
        List<Vacation> allVacations = dbRepository.getAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(!allVacations.isEmpty()){
            Log.println(WARN, "VLIST","onCreate/ allvacations.isEmpty()");
            vacationAdapter.setVacations(allVacations);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addNewVacation) {
            return onAddNewVacation();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Vacation> allVacations = dbRepository.getAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    private boolean onAddNewVacation(){
        List<Vacation> listTemp = dbRepository.getAllVacations();
        Vacation vacationTemp;
        if(listTemp.isEmpty()) {
            Log.println(WARN, "VLIST","onAddNew/ listTemp.isEmpty()");
            vacationTemp = new Vacation(1, "New Vacation", "Hotel Name", 0.0, "", "");
        }else{
            int idTemp = listTemp.size()+1;
            Log.println(WARN, "VLIST","onAddNew/ listTemp is NOT Empty()");
            vacationTemp = new Vacation(idTemp, "New Vacation", "Hotel Name", 0.0, "", "");
        }
        try {
            dbRepository.insert(vacationTemp);
            listTemp = dbRepository.getAllVacations();
            RecyclerView recyclerView = findViewById(R.id.recyclerview);
            final VacationAdapter vacationAdapter = new VacationAdapter(this);
            recyclerView.setAdapter(vacationAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            vacationAdapter.setVacations(listTemp);
            return true;
        }catch(Exception e){
            Log.println(ERROR, "VLIST","onAddNew: "+e);
            return false;
        }
    }
}