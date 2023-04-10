package com.example.becarefall;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
    private static MediaPlayer mediaPlayer;

    public static void playSound(Context context, int soundId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public static void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}