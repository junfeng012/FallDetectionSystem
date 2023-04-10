package com.example.becarefall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckStatus extends AppCompatActivity {
    static c_Account user_Account;
    static c_User user_User;
    static c_Caretaker user_Caretaker;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    static ArrayList<c_FallRecord> c_fallRecordArray;
    Button okay_Button;
    Button okayDisable_Button;
    TextView timer_Text;
    static int userCaretakerArrayEmpty ;
    CountDownTimer countDownTimer;

    static int timer;
    static SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);

        Bundle extras = getIntent().getExtras();
        userCaretakerArrayEmpty = extras.getInt("userCaretakerArrayEmpty");

        Intent i = getIntent();
        user_Account=(c_Account) i.getSerializableExtra("userAccount");
        user_User=(c_User) i.getSerializableExtra("userUser");
        c_CaretakerArray=(ArrayList<c_Caretaker>)i.getSerializableExtra("cCaretakerArray");
        if(userCaretakerArrayEmpty==0) {
            user_CaretakerArray = (ArrayList<c_Caretaker>) i.getSerializableExtra("userCaretakerArray");
        }


        timer = 30;
        if(countDownTimer== null) {
            startTimer();

        }

        okay_Button =(Button) findViewById(R.id.checkS_Okay_Button);
        okayDisable_Button =(Button) findViewById(R.id.checkS_OkayDisable_Button);
        timer_Text= (TextView) findViewById(R.id.checkS_timer_Text);

        okay_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                soundManager.stopSound();
                Intent intent = new Intent(CheckStatus.this,Home.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                intent.putExtra("SensorIsActivated",1);
                startActivity(intent);
            }
        });

        okayDisable_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                soundManager.stopSound();
                SharedPreferences sharedPreferences = getSharedPreferences(user_User.getUserID(), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("statusSwitch", 0);
                editor.apply();
                Intent intent = new Intent(CheckStatus.this,Home.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                intent.putExtra("SensorIsActivated",1);
                startActivity(intent);
            }
        });

    }

    public void startTimer()
    {
        countDownTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer_Text.setText("Remaining :" + String.valueOf(timer) +" seconds.....");
                timer -=1;
            }

            @Override
            public void onFinish() {

                Intent intent = new Intent(CheckStatus.this,ConfirmStatus.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                startActivity(intent);

            }
        }.start();
    }
}