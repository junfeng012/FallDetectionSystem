package com.example.becarefall;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;

public class StatusTimer {
    private static CountDownTimer countDownTimer;
    static int timer;
    static Context context;


    public static void startTimer() {
        timer = 20;
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(20000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timer -=1;
                    Log.e("check Timer","Time:" + Integer.toString(timer) );
                }

                @Override
                public void onFinish() {
                    timer = 0;
                }
            }.start();
        }

    }

    public static void stopTimer(){
        if(countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;

        }
    }

    public static int checkTimer(){
        return timer;
    }


}
