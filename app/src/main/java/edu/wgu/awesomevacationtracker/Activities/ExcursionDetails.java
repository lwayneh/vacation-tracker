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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;

import edu.wgu.awesomevacationtracker.Database.DBRepository;
import edu.wgu.awesomevacationtracker.Entities.Excursion;
import edu.wgu.awesomevacationtracker.Entities.Vacation;
import edu.wgu.awesomevacationtracker.R;
import edu.wgu.awesomevacationtracker.Services.DateValidator;

public class ExcursionDetails extends AppCompatActivity {
    String title;
    Double price;
    String date;
    int excursionID;
    int vacationID;
    String vacationTitle;
    String vacationStartDate;
    String vacationEndDate;

    Excursion currentExcursion;
    Vacation currentVacation;

    EditText editTitle;
    EditText editPrice;
    EditText editDate;
    TextView setID;
    TextView setVID;
    TextView setVTitle;

    DBRepository repository;
    DateValidator dateValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateValidator = new DateValidator();
        setContentView(R.layout.activity_excursion_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        vacationID = (int) getIntent().getIntExtra("vacationID",0);
        setVID = findViewById(R.id.excursionVID);
        setVID.setText(String.valueOf(vacationID));

        repository = new DBRepository(getApplication());
        currentVacation = repository.getAVacation(vacationID);

        vacationTitle = getIntent().getStringExtra("vacationTitle");
        setVTitle = findViewById(R.id.excursionVTitle);
        setVTitle.setText(String.valueOf(vacationTitle));

        vacationStartDate = currentVacation.getVacationStartDate();
        vacationEndDate = currentVacation.getVacationEndDate();

        excursionID = (int) getIntent().getIntExtra("id", 0);
        setID = findViewById(R.id.excursionID);
        setID.setText(String.valueOf(excursionID));

        title = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.excursionTitle);
        editTitle.setText(title);

        price = getIntent().getDoubleExtra("price", 0.0);
        editPrice = findViewById(R.id.excursionPrice);
        editPrice.setText(Double.toString(price));

        date = getIntent().getStringExtra("date");
        date = getIntent().getStringExtra("date");
        editDate = findViewById(R.id.excursionDate);
        editDate.setText(date);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(ExcursionDetails.this, VacationDetails.class);
            intent.putExtra("vacationID", currentVacation.getVacationID());
            setResult(-1, intent);
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.excursionUpdate) {
            if(onExcursionUpdate()){
                finish();
                return true;
            }else{
                return false;
            }
        }

        if (item.getItemId() == R.id.excursionDelete) {
            boolean result = onExcursionDelete();
            if(result){
                this.finish();
                return true;
            }else{
                return false;
            }
        }

        if(item.getItemId()== R.id.notify) {
            return onNotify();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onExcursionUpdate(){
        Excursion excursion;
        String titleTemp = editTitle.getText().toString();
        double priceTemp = Double.parseDouble(editPrice.getText().toString());
        String dateTemp = editDate.getText().toString();
        boolean isDateOK = dateValidator.isExcursionDateOK(dateTemp, currentVacation.getVacationStartDate(), currentVacation.getVacationEndDate());
        if (!isDateOK) {
            Toast.makeText(ExcursionDetails.this, "Please enter a valid Date, within the associated Vacation calendar period.", Toast.LENGTH_LONG).show();
            return false;
        }else {
            try {
                excursion = new Excursion(excursionID, vacationID, vacationTitle, vacationStartDate, titleTemp, dateTemp, priceTemp);
                repository.update(excursion);
                Toast.makeText(ExcursionDetails.this, "SUCCESS! Your excursion list has been updated!", Toast.LENGTH_LONG).show();
                return true;
            } catch (Exception e) {
                System.out.println("~~~~~~~~~~~ EXCURSION EXCEPTION : " + e);
                return false;
            }
        }

    }

    public boolean onExcursionDelete(){
        for (Excursion excursionTemp : repository.getAllExcursions()) {
            if (excursionTemp.getExcursionID() == excursionID) {
                currentExcursion = excursionTemp;
            }
        }
        repository.delete(currentExcursion);
        Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionTitle() + " was deleted", Toast.LENGTH_LONG).show();
        finish();
        return true;
    }

// TASK B) 5.d. Alert that the user can set to trigger
//              on the excursion date, sating the excursion title.
    public boolean onNotify(){
        try {
            onExcursionUpdate();
        }catch(Exception e){

        }
        LocalDate myDate = dateValidator.parseThis(currentExcursion.getExcursionDate());
        try {
            ZoneId zone = ZoneId.of("US/Eastern");
            LocalTime Now1 = LocalTime.now();
            LocalDateTime Now2 = LocalDateTime.now();
            ZoneOffset zoneOffSet = zone.getRules().getOffset(Now2);
            Long trigger = myDate.toEpochSecond(Now1, zoneOffSet);

            Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
            System.out.println("################ INTENT CREATED");

            String msg =  "message I want to see";
            intent.putExtra("MessageText", msg);
            PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            System.out.println("################ PENDING INTENT CREATED");

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            System.out.println("################ alarmManager set");


            Toast.makeText(ExcursionDetails.this, "SUCCESS! Your Notification has been set!", Toast.LENGTH_LONG).show();
            return true;
        }catch (Exception e){
            System.out.println("EXCEPTION NOTIF : "+e);
            return false;
        }
    }


}
