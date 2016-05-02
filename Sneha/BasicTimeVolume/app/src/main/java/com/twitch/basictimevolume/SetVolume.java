package com.twitch.basictimevolume;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Date;

public class SetVolume extends AppCompatActivity {


    public String extra_message;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_volume);
        Intent intent=getIntent();
        String extra_mesg=intent.getStringExtra(extra_message);
        float extra_msg_time=Float.parseFloat(extra_mesg);
        int extra_msg_min_time=(int)extra_msg_time;
        int min=(int)((extra_msg_time-extra_msg_min_time)*100);
        Date dt = new Date();
        int hours = dt.getHours();
        int minutes = dt.getMinutes();
        if (hours>extra_msg_min_time)
        {
            AudioManager myAudioManager;
            myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            TextView show_text_view=(TextView)findViewById(R.id.show_readed_text);
            String msg="Ringing mode set to silent";
            show_text_view.setText(msg);
        }
        else if (hours==extra_msg_min_time && min<minutes)
        {
            AudioManager myAudioManager;
            myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            TextView show_text_view=(TextView)findViewById(R.id.show_readed_text);
            String msg="Ringing mode set to silent";
            show_text_view.setText(msg);
        }
        else
        {
            AudioManager myAudioManager;
            myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            TextView show_text_view = (TextView) findViewById(R.id.show_readed_text);
            String msg = "Ringing mode set as Normal. Will set silent at "+extra_msg_min_time+":"+min;
            show_text_view.setText(msg);
        }
    }


}
