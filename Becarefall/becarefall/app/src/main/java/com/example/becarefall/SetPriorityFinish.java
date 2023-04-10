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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class SetPriorityFinish extends AppCompatActivity  {
    ConnectionDatabase connectDatabase;
    static c_Account user_Account;
    static c_User user_User;
    ImageButton homeButton_Image;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    static c_Caretaker user_Caretaker;
    static int userCaretakerArrayEmpty ;
    static int cCaretakerArray_index;
    static int userCaretakerArray_index;
    static int SensorIsActivated ;


    TextView prority_Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_priority_finish);

        Bundle extras = getIntent().getExtras();
        userCaretakerArrayEmpty = extras.getInt("userCaretakerArrayEmpty");
        cCaretakerArray_index = extras.getInt("cCaretakerArrayIndex");
        userCaretakerArray_index = extras.getInt("userCaretakerArrayIndex");
        SensorIsActivated = extras.getInt("SensorIsActivated");

        Intent i = getIntent();
        user_Account=(c_Account) i.getSerializableExtra("userAccount");
        user_User=(c_User) i.getSerializableExtra("userUser");
        c_CaretakerArray=(ArrayList<c_Caretaker>)i.getSerializableExtra("cCaretakerArray");
        if(userCaretakerArrayEmpty==0) {
            user_CaretakerArray = (ArrayList<c_Caretaker>) i.getSerializableExtra("userCaretakerArray");
        }
        user_Caretaker=(c_Caretaker) i.getSerializableExtra("userCaretaker");



        homeButton_Image=(ImageButton) findViewById(R.id.spf_HomeButton_Image);
        prority_Text=(TextView)findViewById(R.id.spf_Priority_Text);
        connectDatabase();

        String name;
        name = trimName(user_Caretaker.getName());

        updateCaretakerArray();
        prority_Text.setText("The priority of "+name+" is now highest");

        homeButton_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetPriorityFinish.this,Home.class);
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
    public void connectDatabase()
    {
        Log.e("Connect database in EditUserName", "connecting");
        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);

        for(int i=0 ; i<user_CaretakerArray.size();i++) {
            String caretakerID = user_CaretakerArray.get(i).getCaretakerID();
            String accountID = user_CaretakerArray.get(i).getAccountID();
            String userID = user_CaretakerArray.get(i).getUserID();
            String name = user_CaretakerArray.get(i).getName();
            String email = user_CaretakerArray.get(i).getEmail();
            String phone = user_CaretakerArray.get(i).getPhone();
            String availability = user_CaretakerArray.get(i).getAvailability();
            String priority = "no";
            c_Caretaker temp_Caretaker = new c_Caretaker(caretakerID, accountID, userID, name, email, phone, availability, priority);
            connectDatabase.updateCaretakerInfo(temp_Caretaker);
        }
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

    public void updateCaretakerArray()
    {
        c_CaretakerArray.get(cCaretakerArray_index).setPriority(user_Caretaker.getPriority());
        user_CaretakerArray.get(userCaretakerArray_index).setPriority(user_Caretaker.getPriority());
    }


}