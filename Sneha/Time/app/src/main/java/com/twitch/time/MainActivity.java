package com.twitch.time;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public TimePicker time_Picker;
    private Button set_silent;
    private Button set_normal;
    public String extra_message;
    public  int hour_string;
    public int minute_string;
    public String volume;
    public String extra_mess;
    int hours;
    int minutes;
    int hour;
    int minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        time_Picker=(TimePicker)findViewById(R.id.timePicker);
        hour=time_Picker.getHour();
        minute=time_Picker.getMinute();
        Date dt = new Date();
        hours = dt.getHours();
        minutes = dt.getMinutes();
        time_Picker=(TimePicker)findViewById(R.id.timePicker);
        set_silent=(Button)findViewById(R.id.button);


        setSilent();

    }

    public void setSilent()
    {

        set_silent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(hour<hours)
                {
                    AudioManager myAudioManager;
                    myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    extra_mess=""+hour;
                }
                else if(hour==hours && minute<minutes)
                {
                    AudioManager myAudioManager;
                    myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    extra_mess="Set to Silent";
                }
                else
                {
                    extra_mess="Now Normal. Will set to silent at "+hour+":"+minute;
                }
                Intent intent = new Intent(getApplicationContext(),SetVolume.class);
                intent.putExtra(extra_message,extra_mess);
                startActivity(intent);
            }
        });
    }

}
