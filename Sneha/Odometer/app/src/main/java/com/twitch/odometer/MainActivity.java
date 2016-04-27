package com.twitch.odometer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private OdometerService odometer;
    private boolean bound = false;



    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {

            OdometerService.OdometerBinder odometerBinder =
                    (OdometerService.OdometerBinder) binder;
            odometer = odometerBinder.getOdometer();
            bound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            bound=false;

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        watchMileage();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, OdometerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }
    private void watchMileage() {
        final TextView distanceView = (TextView)findViewById(R.id.distance);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                double distance = 0.0;
                if (odometer != null) {
                    distance = odometer.getMeters();
                }
                String distanceStr = String.format("%1$,.2f meters", distance);
                distanceView.setText(distanceStr);
                handler.postDelayed(this, 1000);
                if (distance < 10) {
                    AudioManager myAudioManager;
                    myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    Toast.makeText(MainActivity.this, "Now in Vibrate Mode", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AudioManager myAudioManager;
                    myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    Toast.makeText(MainActivity.this, "Now in Normal Mode", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
