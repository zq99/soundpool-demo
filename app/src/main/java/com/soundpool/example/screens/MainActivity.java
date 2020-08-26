package com.soundpool.example.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.soundpool.example.R;
import com.soundpool.example.sound.SoundFiles;
import com.soundpool.example.sound.SoundPoolManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, SoundPoolManager.SoundPoolManagerListener {

    private TextView tvProgress;
    private TextView tvStart;
    private TextView tvEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActivity();
        tvStart.setText(String.valueOf(Calendar.getInstance().getTime()));

        // this will start the sound loading process
        SoundPoolManager.createInstance(getApplicationContext(),this,new SoundFiles());
    }


    @Override
    public void onClick(View view) {

        // user cannot go to next screen until all sounds are loaded
        boolean loaded = SoundPoolManager.getInstance(getApplicationContext()).isSoundsLoaded();
        if(loaded) {
            Intent intent = new Intent(this, SubActivity.class);
            startActivity(intent);
        }else{
           Toast.makeText(getApplicationContext(),getString(R.string.waiting), Toast.LENGTH_SHORT).show();
         }
    }

    @Override
    public void onItemsLoaded() {
        tvProgress.setText(getString(R.string.complete));
        tvEnd.setText(String.valueOf(Calendar.getInstance().getTime()));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(SoundPoolManager.getSoundPoolManager()!=null) {
            SoundPoolManager.getSoundPoolManager().onDestroy();
        }
    }

    private void setUpActivity(){
        // need to replace with view binding
        tvStart = findViewById(R.id.tvStartTimeId);
        tvEnd = findViewById(R.id.tvEndTimeId);
        tvProgress = findViewById(R.id.tvProgress);
        tvProgress.setText(getString(R.string.waiting));
        Button button = findViewById(R.id.buttonId);
        button.setOnClickListener(this);
    }
}
