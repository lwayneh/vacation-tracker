package edu.wgu.awesomevacationtracker.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import edu.wgu.awesomevacationtracker.Database.DBRepository;
import edu.wgu.awesomevacationtracker.Entities.Excursion;
import edu.wgu.awesomevacationtracker.Entities.Vacation;
import edu.wgu.awesomevacationtracker.R;
import edu.wgu.awesomevacationtracker.Services.DateValidator;

public class VacationDetails extends AppCompatActivity {
    String vacationTitle;
    String vacationHotel;
    double vacationPrice;
    int vacationID;
    String vacationStartDate;
    String vacationEndDate;

    TextView setID;
    EditText editTitle;
    EditText editPrice;
    EditText editHotel;
    EditText editStartDate;
    EditText editEndDate;

    DBRepository repository;
    Vacation currentVacation;
    int numExcursions;
    DateValidator dateValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateValidator = new DateValidator();
        setContentView(R.layout.activity_vacation_details);

        vacationID = (int) getIntent().getIntExtra("id", 0);
        setID = findViewById(R.id.vacationID);
        setID.setText(String.valueOf(vacationID));

        vacationTitle = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.vacationTitle);
        editTitle.setText(vacationTitle);

        vacationHotel = getIntent().getStringExtra("hotel");
        editHotel = findViewById(R.id.vacationHotel);
        editHotel.setText(vacationHotel);
        //        this.date = (Date)intent.getSerializableExtra(DATE_EXTRA);

        vacationStartDate = getIntent().getStringExtra("startDate");
        editStartDate = findViewById(R.id.vacationStartDate);
        editStartDate.setText(vacationStartDate);

        vacationEndDate = getIntent().getStringExtra("endDate");
        editEndDate = findViewById(R.id.vacationEndDate);
        editEndDate.setText(vacationEndDate);

        vacationPrice = getIntent().getDoubleExtra("price", 0.0);
        editPrice = findViewById(R.id.vacationPrice);
        editPrice.setText(Double.toString(vacationPrice));

        repository = new DBRepository(getApplication());
        try {
            List<Excursion> allExcursions = repository.getAllExcursionsForThisVacation(vacationID);
            RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
            final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
            recyclerView.setAdapter(excursionAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            excursionAdapter.setExcursions(allExcursions);
        }catch(Exception e){
            System.out.println("DEBUG HERE "+e);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.vacationUpdate){
            if(onVacationUpdate()){
                finish();
                return true;
            }else{
                return false;
            }
        }
        if(item.getItemId()== R.id.vacationDelete) {
            return onVacationDelete();
        }
        if(item.getItemId()== R.id.addExcursion){
            return onAddExcursion();
        }
        if (item.getItemId()== R.id.share) {
            return onShare();
        }
        if(item.getItemId()== R.id.notify) {
            return onNotify();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = repository.getAllExcursionsForThisVacation(vacationID);
        excursionAdapter.setExcursions(filteredExcursions);
        //Toast.makeText(VacationDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1) {
            if(data != null) {
                int vIDtemp = (int) data.getIntExtra("vacationID", 0);
                Vacation vEntityTemp = repository.getAVacation(vIDtemp);
            }
        }
    }

    public boolean onVacationUpdate(){
        Vacation vacation;
        int idTemp = Integer.parseInt(setID.getText().toString());
        String titleTemp = editTitle.getText().toString();
        String hotelTemp = editHotel.getText().toString();
        double priceTemp = Double.parseDouble(editPrice.getText().toString());
        String startTemp = editStartDate.getText().toString();
        String endTemp = editEndDate.getText().toString();
        boolean isStartDateOK = dateValidator.isDateOK(startTemp);
        if(!isStartDateOK){
            Toast.makeText(VacationDetails.this, "Please enter a valid Start Date, not earlier than tomorrow.", Toast.LENGTH_LONG).show();
            return false;
        }
        boolean isEndDateOK = dateValidator.isDateOK(endTemp);
        if(isStartDateOK && !isEndDateOK){
            Toast.makeText(VacationDetails.this, "Please enter a valid End Date, must be after today & the Start Date.", Toast.LENGTH_LONG).show();
            return false;
        }
        boolean isEndAfterStart = true;
        if(isStartDateOK && isEndDateOK) {
            isEndAfterStart = dateValidator.isEndAfterStart(startTemp, endTemp);
        }
        if(!isEndAfterStart){
            Toast.makeText(VacationDetails.this, "End Date must be after Start Date", Toast.LENGTH_LONG).show();
            return false;
        }
        if(isStartDateOK && isEndDateOK && isEndAfterStart) {
            try {
                vacation = new Vacation(idTemp, titleTemp, hotelTemp, priceTemp, startTemp, endTemp);
                currentVacation = vacation;
                repository.update(vacation);
                Toast.makeText(VacationDetails.this, "SUCCESS! Your vacation list has been updated!", Toast.LENGTH_LONG).show();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean onVacationDelete(){
        for (Vacation vacationTemp : repository.getAllVacations()) {
            if (vacationTemp.getVacationID() == vacationID) currentVacation = vacationTemp;
        }

        numExcursions = repository.getAllExcursionsForThisVacation(vacationID).size();
        if (numExcursions == 0) {
            repository.delete(currentVacation);
            Toast.makeText(VacationDetails.this, currentVacation.getVacationTitle() + " was deleted", Toast.LENGTH_LONG).show();
            finish();
            return true;
        } else {
            Toast.makeText(VacationDetails.this, "Can't delete a Vacation with saved Excursions", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean onAddExcursion(){
        onVacationUpdate();
        DBRepository repo = new DBRepository(getApplication());
        int excursionID;
        Excursion excursionTemp;
        if (repo.getAllExcursions().size()==0) {
            excursionID = 1;
            excursionTemp = new Excursion(excursionID, currentVacation.getVacationID(), currentVacation.getVacationTitle(), currentVacation.getVacationStartDate(), "New Excursion", "", 0.0);
        }else {
            excursionID = repo.getAllExcursions().size()+1;
            excursionTemp = new Excursion(excursionID, currentVacation.getVacationID(), currentVacation.getVacationTitle(), currentVacation.getVacationStartDate(), "New Excursion", "", 0.0);
        }
        try {
            repo.insert(excursionTemp);
            int vID = currentVacation.getVacationID();
            List<Excursion> filteredExcursions = repo.getAllExcursionsForThisVacation(vID);
            RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
            final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
            recyclerView.setAdapter(excursionAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            excursionAdapter.setExcursions(filteredExcursions);
            return true;
        }catch(Exception e){
            System.out.println("VACATION DETAILS DEBUGGING EXCUURSION INSERT : "+e);
            return false;
        }
    }

// TASK B . 3.e)  Alert that gets triggered on start/end dates,
//                with vacation title and if it is starting /ending.
// TASK B . 3.f)  Sharing features that autopopulates all vacation details
//                and uses either e-mail, clipboard or SMS

    public boolean onNotify(){
        try {
            onVacationUpdate();
        }catch(Exception e){

        }
        LocalDate myDate = dateValidator.parseThis(currentVacation.getVacationStartDate());
        try {
            ZoneId zone = ZoneId.of("US/Eastern");
            LocalTime Now1 = LocalTime.now();
            LocalDateTime Now2 = LocalDateTime.now();
            ZoneOffset zoneOffSet = zone.getRules().getOffset(Now2);
            Long trigger = myDate.toEpochSecond(Now1, zoneOffSet);

            Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
            System.out.println("################ INTENT CREATED");

            String msg =  "message I want to see";
            intent.putExtra("MessageText", msg);
            PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            System.out.println("################ PENDING INTENT CREATED");

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            System.out.println("################ alarmManager set");


            Toast.makeText(VacationDetails.this, "SUCCESS! Your Notification has been set!", Toast.LENGTH_LONG).show();
            return true;
        }catch (Exception e){
            System.out.println("EXCEPTION NOTIF : "+e);
            return false;
        }
    }

    public boolean onShare(){
        try {
            onVacationUpdate();
        }catch(Exception e){

        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
        return true;
    }

}