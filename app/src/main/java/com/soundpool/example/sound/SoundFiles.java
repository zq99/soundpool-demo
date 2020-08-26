package com.soundpool.example.sound;

import android.util.SparseIntArray;

import com.soundpool.example.R;

public class SoundFiles implements FileList {

    private static final SparseIntArray SOUND_MAP = new SparseIntArray();

    static {
        SOUND_MAP.put(1,R.raw.p1);
        SOUND_MAP.put(2,R.raw.p2);
        SOUND_MAP.put(3,R.raw.p3);
        SOUND_MAP.put(4,R.raw.p4);
        SOUND_MAP.put(5,R.raw.p5);
        SOUND_MAP.put(6,R.raw.p6);
        SOUND_MAP.put(7,R.raw.p7);
        SOUND_MAP.put(8,R.raw.p8);
        SOUND_MAP.put(9,R.raw.p9);
        SOUND_MAP.put(10,R.raw.p10);
        SOUND_MAP.put(11,R.raw.p11);
        SOUND_MAP.put(12,R.raw.p12);
        SOUND_MAP.put(13,R.raw.p13);
    }

    @Override
    public int getFileCount() {
        return SOUND_MAP.size();
    }

    @Override
    public int getFileId(int note) {
        return SOUND_MAP.get(note);
    }

}
