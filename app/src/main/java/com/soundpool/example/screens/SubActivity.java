package com.soundpool.example.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.soundpool.example.R;
import com.soundpool.example.sound.SoundFiles;
import com.soundpool.example.sound.SoundPoolManager;
import com.soundpool.example.util.Util;

public class SubActivity extends AppCompatActivity implements
        View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        setUp();
    }

    @Override
    public void onClick(View view) {
        if(SoundPoolManager.getSoundPoolManager().isSoundsLoaded()) {
            playRandomSound();
        }else{
            Toast.makeText(getApplicationContext(), getString(R.string.waiting),Toast.LENGTH_SHORT).show();
        }
    }

    private void playRandomSound(){
        SoundFiles soundFiles = new SoundFiles();
        int randomFileId = Util.getRandomNumberInRange(1, soundFiles.getFileCount());
        SoundPoolManager.getInstance(getApplicationContext()).playNote(randomFileId);
    }

    private void setUp(){
        Button button = findViewById(R.id.buttonRand);
        button.setOnClickListener(this);
    }


}
