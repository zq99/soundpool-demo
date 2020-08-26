package com.soundpool.example.sound;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class SoundPoolManager {
    private static SoundPoolManager soundPoolManager = null;
    private final Context context;
    private int[] soundIds;
    private SoundPool soundPool;
    private final ArrayList<SoundPoolManagerListener> listeners = new ArrayList<>();
    private boolean isSoundsLoaded = false;
    private boolean loadingInProgress = false;
    private FileList soundFiles = null;
    private static final String TAG = "TAG";

    public interface SoundPoolManagerListener {
        void onItemsLoaded();
    }

    public static SoundPoolManager getSoundPoolManager(){
        return soundPoolManager;
    }

    public static SoundPoolManager getInstance(Context context) {
        if (soundPoolManager == null) {
            soundPoolManager = new SoundPoolManager(context);
        }
        return soundPoolManager;
    }

    public static void createInstance(Context context,SoundPoolManagerListener soundPoolManagerListener,FileList soundFiles){
        soundPoolManager = new SoundPoolManager(context,soundPoolManagerListener,soundFiles);
    }

    private SoundPoolManager(Context context,SoundPoolManagerListener soundPoolManagerListener,FileList soundFiles){
        this.context = context.getApplicationContext();
        this.listeners.add(soundPoolManagerListener);
        this.soundFiles = soundFiles;
        init();
    }

    private SoundPoolManager(Context context) {
        this.context = context.getApplicationContext();
        init();
    }

    private void setUpSoundPool(){
        if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.soundPool = createSoundPool_POST_API21();
        } else {
            this.soundPool = createSoundPool_PRE_API21();
        }
    }

    private void init() {
        setUpSoundPool();
        loadSounds();
    }

    @TargetApi(21)
    private SoundPool createSoundPool_POST_API21() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build();
        return soundPool;
    }

    private SoundPool createSoundPool_PRE_API21() {
        return new SoundPool(2, 3, 0);
    }


    private synchronized void loadSounds() {
        loadingInProgress = true;
        new Thread(() -> {
            try {
                populateSoundPool();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void populateSoundPool(){

        this.soundIds = new int[soundFiles.getFileCount()];
        int counter=1;

        for(int fileNumber=1;fileNumber<=soundFiles.getFileCount();fileNumber++){
            int fileId = soundFiles.getFileId(fileNumber);
            this.soundIds[fileNumber] = this.soundPool.load(this.context, fileId, 1);
            counter++;
            Log.i(TAG,"loading file number:" + counter);
            this.soundPool.setOnLoadCompleteListener((soundPool, i, i1) -> {
                Log.i(TAG,"listener is being called after file load");
                if(listeners.size() > 0){
                    Log.i(TAG,"there are listeners to notify");
                    if(i == soundFiles.getFileCount()) {
                        Log.i(TAG,"all files have been loaded!");
                        isSoundsLoaded = true;
                        loadingInProgress = false;

                        for(SoundPoolManagerListener listener : listeners){
                            Log.i(TAG,"updating all listeners to confirm process completed!");
                            listener.onItemsLoaded();
                        }
                    }
                }else{
                    Log.i(TAG,"files have not yet loaded, no listeners notified!");
                }
            });
        }

    }

    public boolean isSoundsLoaded(){
        return this.isSoundsLoaded;
    }

    public void playNote(int note) {
        try {
            this.soundPool.play(this.soundIds[note], 1.0f, 1.0f, 1, 0, 1.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy(){
        this.soundPool.release();
        soundPool = null;
    }

    public void stopNote(int note) {
        try {
            this.soundPool.stop(note);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getLoadedFilesCount(){
        return soundIds.length;
    }


}