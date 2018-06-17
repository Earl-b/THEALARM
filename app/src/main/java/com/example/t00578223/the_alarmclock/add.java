package com.example.t00578223.the_alarmclock;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class add extends AppCompatActivity {


    TimePicker time;

    SharedPreferences sharedPreferences;
    String key = "shared";
    String ALARMS="";
    StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        sharedPreferences = getApplicationContext().getSharedPreferences(key, MODE_PRIVATE);
        time = findViewById(R.id.timePicker);

        if(sharedPreferences != null){
            ALARMS = sharedPreferences.getString(key,"");
        }
    }



    public void save(View view){
        int hour = time.getHour();
        int min = time.getMinute();
        String am_pm;

        String timeString;

        if(hour == 0){
            am_pm = "AM";
            hour = 12;
        }
        else if(hour == 12){
            am_pm = "PM";
            hour = 12;
        }
        else if(hour>12){
            am_pm = "PM";
            hour = hour - 12;
        }
        else am_pm = "AM";

        if(min < 10) timeString = hour + ":0"+ min + am_pm;
        else
            timeString = hour + ":"+ min + am_pm;


        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,min);
        c.set(Calendar.SECOND,0);
        startAlarm(c);


        sb.append(ALARMS).append(timeString).append(",");

        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key,sb.toString());
        edit.apply();



        startActivity(new Intent(add.this, MainActivity.class));


    }

    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    }


}
