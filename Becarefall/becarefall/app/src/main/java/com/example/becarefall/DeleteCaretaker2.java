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

public class DeleteCaretaker2 extends AppCompatActivity {
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

    TextView delete_Text;
    Button delete_Button;
    Button cancel_Button;

    static int SensorIsActivated ;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_caretaker2);

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

        delete_Text = (TextView) findViewById(R.id.dc2_Delete_Text);
        delete_Button=(Button) findViewById(R.id.dc2_Delete_Button);
        cancel_Button=(Button) findViewById(R.id.dc2_Cancel_Button);

        String name = trimName(user_Caretaker.getName());

        delete_Text.setText("Confirm to delete " +name +"?");



        homeButton_Image=(ImageButton) findViewById(R.id.dc2_HomeButton_Image);

        homeButton_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteCaretaker2.this,DeleteCaretakerFinish.class);
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

        delete_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCaretakerInfo();
                connectDatabase();

                Intent intent = new Intent(DeleteCaretaker2.this,DeleteCaretakerFinish.class);
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
                Intent intent = new Intent(DeleteCaretaker2.this,DeleteCaretaker.class);
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
        c_CaretakerArray.get(cCaretakerArray_index).setUserID(user_Caretaker.getUserID());
        user_CaretakerArray.remove(userCaretakerArray_index);

    }
    public void connectDatabase()
    {
        Log.e("Connect database in EditUserName", "connecting");
        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);
        connectDatabase.updateCaretakerInfo(user_Caretaker);

    }
    public  String trimName(String name)
    {
        String temp_Name;
        String char_Name;
        String next_char;
        int next;
        next = 0 ;

        temp_Name = "";

        for(int i = 0 ;i<name.length()-1 ;i++)
        {
            char_Name = name.substring(i, i + 1);
            next_char = name.substring(i+1,i+2);

            if(char_Name.equals(" ") && !next_char.equals(" "))
            {
                next+=1;
            }

        }

        for (int i = 0 ;i<name.length() ;i++) {
            char_Name = name.substring(i, i + 1);


            if (!char_Name.equals(" "))
            {
                temp_Name = temp_Name+ char_Name;
            }
            else if (char_Name.equals(" ") && next >0)
            {
                temp_Name = temp_Name+" ";
                next -=1;
            }

        }

        return temp_Name;
    }



}