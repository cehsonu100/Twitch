package com.twitch.basictimevolume;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public String extra_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
    public void setTime(View view)
    {
        EditText user_input=(EditText)findViewById(R.id.enter_here);
        String user_input_string=user_input.getText().toString();
        Intent intent=new Intent(this,SetVolume.class);
        intent.putExtra(extra_message,user_input_string);
        startActivity(intent);



    }



}
