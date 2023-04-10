package com.example.becarefall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewCaretaker2 extends AppCompatActivity  {
    static c_Account user_Account;
    static c_User user_User;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    static c_Caretaker user_Caretaker;
    ImageButton homeButton_Image;
    static int userCaretakerArrayEmpty ;

    TextView caretakerID_Text;
    TextView name_Text;
    TextView phone_Text;
    TextView emailAddress_Text;

    static int SensorIsActivated ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_caretaker2);

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
        user_Caretaker=(c_Caretaker) i.getSerializableExtra("userCaretaker");


        caretakerID_Text = (TextView) findViewById(R.id.vc2_CaretakerID_Text);
        name_Text = (TextView) findViewById(R.id.vc2_Name_Text);
        phone_Text = (TextView) findViewById(R.id.vc2_Phone_Text);
        emailAddress_Text = (TextView) findViewById(R.id.vc2_EmailAddress_Text);

        caretakerID_Text.setText(user_Caretaker.getCaretakerID());
        name_Text.setText(user_Caretaker.getName());
        phone_Text.setText(user_Caretaker.getPhone());
        emailAddress_Text.setText(user_Caretaker.getEmail());

        homeButton_Image=(ImageButton) findViewById(R.id.up_HomeButton_Image);

        homeButton_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCaretaker2.this,Home.class);
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