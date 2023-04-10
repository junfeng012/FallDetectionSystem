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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class AddCaretaker extends AppCompatActivity  {
    ConnectionDatabase connectDatabase;
    static c_Account user_Account;
    static c_User user_User;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    static c_Caretaker user_Caretaker;
    static int cCaretakerArray_index;
    EditText caretakerID_Input;
    Button add_Button;
    ImageButton homeButton_Image;
    static int userCaretakerArrayEmpty ;
    static int SensorIsActivated ;


    String caretakerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_caretaker);

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

        add_Button = (Button) findViewById(R.id.ac_Add_Button);
        caretakerID_Input = (EditText)findViewById(R.id.ac_caretakerID_Box);
        homeButton_Image=(ImageButton) findViewById(R.id.ac_HomeButton_Image);


        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean validCaretaker =false;
                caretakerID = caretakerID_Input.getText().toString();
                if(caretakerID.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Fill in the CaretakerID",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    validCaretaker=findCaretakerID(caretakerID);
                }

                if(validCaretaker)
                {
                    Toast.makeText(getApplicationContext(),"Valid Caretaker",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddCaretaker.this,AddCaretaker2.class);
                    intent.putExtra("userAccount",user_Account);
                    intent.putExtra("userUser",user_User);
                    intent.putExtra("cCaretakerArray",c_CaretakerArray);
                    if(userCaretakerArrayEmpty==0) {
                        intent.putExtra("userCaretakerArray", user_CaretakerArray);
                    }
                    intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                    intent.putExtra("userCaretaker",user_Caretaker);
                    intent.putExtra("cCaretakerArray_index",cCaretakerArray_index);
                    intent.putExtra("SensorIsActivated",SensorIsActivated);
                    startActivity(intent);

                }

            }
        });
        homeButton_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCaretaker.this,Home.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                intent.putExtra("cCaretakerArray_index",cCaretakerArray_index);
                intent.putExtra("SensorIsActivated",SensorIsActivated);
                startActivity(intent);
            }
        });

    }
    public boolean findCaretakerID(String caretakerID)
    {
        int valid_caretakerID =0 ;
        int caretaker_Added=0;
        cCaretakerArray_index=0;


        for(int i=0 ; i<c_CaretakerArray.size();i++)
        {
            if(caretakerID.equals(c_CaretakerArray.get(i).getCaretakerID()))
            {
                valid_caretakerID =1;
                cCaretakerArray_index =i;
            }
        }

        if(userCaretakerArrayEmpty==0) {
            for (int i = 0; i < user_CaretakerArray.size(); i++) {
                if (caretakerID.equals(user_CaretakerArray.get(i).getCaretakerID())) {
                    caretaker_Added = 1;
                }
            }
        }
        else
        {
            caretaker_Added=0;
        }

        if(valid_caretakerID == 1 && caretaker_Added==0) {
            String accountID = c_CaretakerArray.get(cCaretakerArray_index).getAccountID();
            String userID = c_CaretakerArray.get(cCaretakerArray_index).getUserID();
            String name = c_CaretakerArray.get(cCaretakerArray_index).getName();
            String email = c_CaretakerArray.get(cCaretakerArray_index).getEmail();
            String phone = c_CaretakerArray.get(cCaretakerArray_index).getPhone();
            String availability = c_CaretakerArray.get(cCaretakerArray_index).getAvailability();
            String priority = c_CaretakerArray.get(cCaretakerArray_index).getPriority();
            user_Caretaker = new c_Caretaker(caretakerID, accountID, userID, name, email, phone, availability, priority);

            return true;
        }
        else if (caretaker_Added ==1)
        {
            Toast.makeText(getApplicationContext(),"Caretaker has been added before",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid CaretakerID",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}