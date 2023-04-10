package com.example.becarefall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ManageCaretaker extends AppCompatActivity  {
    static c_Account user_Account;
    static c_User user_User;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    static int userCaretakerArrayEmpty ;
    Button viewCaretaker_Button;
    Button updateCaretaker_Button;
    ImageButton homeButton_Image;
    static int SensorIsActivated ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_caretaker);

        Bundle extras = getIntent().getExtras();
        userCaretakerArrayEmpty = extras.getInt("userCaretakerArrayEmpty");
        SensorIsActivated = extras.getInt("SensorIsActivated");

        Intent i = getIntent();
        user_Account=(c_Account) i.getSerializableExtra("userAccount");
        user_User=(c_User) i.getSerializableExtra("userUser");
        c_CaretakerArray=(ArrayList<c_Caretaker>)i.getSerializableExtra("cCaretakerArray");
        if(userCaretakerArrayEmpty==0) {
            user_CaretakerArray = (ArrayList<c_Caretaker>) i.getSerializableExtra("userCaretakerArray");
        }
        for(int j =0; j<c_CaretakerArray.size();j++)
        {
            Log.e("Check Caretaker ID at ManageCaretaker" ,"ID:"+c_CaretakerArray.get(j).getCaretakerID());
        }
        Log.e("Check userCaretakerArrayEmpty at ManageCaretaker", String.valueOf(userCaretakerArrayEmpty));

        if(userCaretakerArrayEmpty==0) {
            for (int j = 0; j < user_CaretakerArray.size(); j++) {
                Log.e("Check User Caretaker ID at ManageCaretaker", "ID:" + user_CaretakerArray.get(j).getCaretakerID());
            }
        }




        Log.e("Check User Account in ManageCaretaker class","ID:"+user_Account.getAccountID()+" username:"+user_Account.getUsername());
        Log.e("Check User User in ManageCaretaker class","userID:"+user_User.getUserID()+" accountID:"+user_User.getAccountID());

        viewCaretaker_Button = (Button) findViewById(R.id.mc_ViewCaretaker_Button);
        updateCaretaker_Button = (Button) findViewById(R.id.mc_UpdataCaretaker_Button);
        homeButton_Image=(ImageButton) findViewById(R.id.mc_HomeButton_Image);

        viewCaretaker_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageCaretaker.this,ViewCaretaker.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                intent.putExtra("SensorIsActivated",SensorIsActivated);
                startActivity(intent);
            }
        });

        updateCaretaker_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageCaretaker.this,UpdateCaretaker.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                intent.putExtra("SensorIsActivated",SensorIsActivated);
                startActivity(intent);
            }
        });

        homeButton_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageCaretaker.this,Home.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                intent.putExtra("SensorIsActivated",SensorIsActivated);
                startActivity(intent);
            }
        });
    }


}