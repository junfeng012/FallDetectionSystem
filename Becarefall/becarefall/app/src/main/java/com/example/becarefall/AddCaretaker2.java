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
import android.widget.TextView;

import java.util.ArrayList;

public class AddCaretaker2 extends AppCompatActivity {
    ConnectionDatabase connectDatabase;
    static c_Account user_Account;
    static c_User user_User;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    static c_Caretaker user_Caretaker;
    ImageButton homeButton_Image;
    static int userCaretakerArrayEmpty ;
    static int cCaretakerArray_index;
    static int userCaretakerArray_index;
    static int SensorIsActivated ;

    Button add_Button;
    Button cancel_Button;
    TextView caretakerID_Text;
    TextView name_Text;
    TextView phone_Text;
    TextView emailAddress_Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_caretaker2);

        Bundle extras = getIntent().getExtras();
        userCaretakerArrayEmpty = extras.getInt("userCaretakerArrayEmpty");
        cCaretakerArray_index = extras.getInt("cCaretakerArray_index");
        userCaretakerArray_index = extras.getInt("userCaretakerArray_index");
        SensorIsActivated = extras.getInt("SensorIsActivated");


        Intent i = getIntent();
        user_Account=(c_Account) i.getSerializableExtra("userAccount");
        user_User=(c_User) i.getSerializableExtra("userUser");
        c_CaretakerArray=(ArrayList<c_Caretaker>)i.getSerializableExtra("cCaretakerArray");
        if(userCaretakerArrayEmpty==0) {
            user_CaretakerArray = (ArrayList<c_Caretaker>) i.getSerializableExtra("userCaretakerArray");
        }
        user_Caretaker=(c_Caretaker) i.getSerializableExtra("userCaretaker");



        caretakerID_Text = (TextView) findViewById(R.id.ac2_CaretakerID_Text);
        name_Text = (TextView) findViewById(R.id.ac2_Name_Text);
        phone_Text = (TextView) findViewById(R.id.ac2_Phone_Text);
        emailAddress_Text = (TextView) findViewById(R.id.ac2_EmailAddress_Text);

        caretakerID_Text.setText(user_Caretaker.getCaretakerID());
        name_Text.setText(user_Caretaker.getName());
        phone_Text.setText(user_Caretaker.getPhone());
        emailAddress_Text.setText(user_Caretaker.getEmail());

        add_Button=(Button)findViewById(R.id.ac2_Add_Button);
        cancel_Button=(Button)findViewById(R.id.ac2_Cancel_Button);
        homeButton_Image=(ImageButton) findViewById(R.id.up_HomeButton_Image);

        homeButton_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCaretaker2.this,Home.class);
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

        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCaretakerInfo();
                connectDatabase();

                Intent intent = new Intent(AddCaretaker2.this,AddCaretakerFinish.class);
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

        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCaretaker2.this,AddCaretaker.class);
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
    public void updateCaretakerInfo()
    {
        user_Caretaker.setUserID(user_User.getUserID());
        c_CaretakerArray.get(cCaretakerArray_index).setUserID(user_User.getUserID());
        user_CaretakerArray = new ArrayList<>();
        userCaretakerArrayEmpty=1;

        user_CaretakerArray.add(user_Caretaker);

    }
    public void connectDatabase()
    {
        Log.e("Connect database in EditUserName", "connecting");
        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);
        connectDatabase.updateCaretakerInfo(user_Caretaker);

    }

}