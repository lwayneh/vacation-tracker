package edu.wgu.awesomevacationtracker.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.wgu.awesomevacationtracker.R;

public class MainActivity extends AppCompatActivity {
// This application uses an image by Designed by rawpixel.com / Freepik
    public static int numAlert;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button2=findViewById(R.id.buttonTracker);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, VacationList.class);
                startActivity(intent);
            }
        });
    }
}