package com.example.becarefall;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewCaretaker extends AppCompatActivity {
    static c_Account user_Account;
    static c_User user_User;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    static c_Caretaker user_Caretaker;
    RadioGroup  caretakerList_RadioGroup;
    ImageButton homeButton_Image;
    static int userCaretakerArrayEmpty ;
    Button view_Button;
    static int chosenRadioID;
    static int SensorIsActivated ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_caretaker);

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



        Log.e("Check User Account in Manage Account class","ID:"+user_Account.getAccountID()+" username:"+user_Account.getUsername());
        Log.e("Check User User in Manage Account class","userID:"+user_User.getUserID()+" accountID:"+user_User.getAccountID());

        addRadioButtons();

        view_Button =(Button)findViewById(R.id.vc_View_Button);
        homeButton_Image=(ImageButton) findViewById(R.id.vc_HomeButton_Image);

        homeButton_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCaretaker.this,Home.class);
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

        view_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenRadioID= -1;
                chosenRadioID = caretakerList_RadioGroup.getCheckedRadioButtonId();
                if(userCaretakerArrayEmpty==1)
                {
                    Toast.makeText(getApplicationContext(),"no caretaker in the list",Toast.LENGTH_SHORT).show();
                }
                else if(chosenRadioID!=-1)
                {
                    Toast.makeText(getApplicationContext(),"Chosen Radio :" + String.valueOf(chosenRadioID),Toast.LENGTH_SHORT).show();
                    setChosenCaretaker();
                    Intent intent = new Intent(ViewCaretaker.this,ViewCaretaker2.class);
                    intent.putExtra("userAccount",user_Account);
                    intent.putExtra("userUser",user_User);
                    intent.putExtra("cCaretakerArray",c_CaretakerArray);
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                    intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                    intent.putExtra("userCaretaker",user_Caretaker);
                    intent.putExtra("SensorIsActivated",SensorIsActivated);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please select a caretaker",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setChosenCaretaker()
    {
        String caretakerID = user_CaretakerArray.get(chosenRadioID).getCaretakerID();
        String accountID =user_CaretakerArray.get(chosenRadioID).getAccountID();
        String userID = user_CaretakerArray.get(chosenRadioID).getUserID();
        String name = user_CaretakerArray.get(chosenRadioID).getName();
        String email = user_CaretakerArray.get(chosenRadioID).getEmail();
        String phone = user_CaretakerArray.get(chosenRadioID).getPhone();
        String availability = user_CaretakerArray.get(chosenRadioID).getAvailability();
        String priority =user_CaretakerArray.get(chosenRadioID).getPriority();
        user_Caretaker = new c_Caretaker(caretakerID,accountID,userID,name,email,phone,availability,priority);
    }

    public void addRadioButtons() {

        caretakerList_RadioGroup=(RadioGroup) findViewById(R.id.vc_CaretakerList_RadioGroup);
        caretakerList_RadioGroup.setOrientation(LinearLayout.VERTICAL);

        if(userCaretakerArrayEmpty==0) {
            for (int i = 0; i < user_CaretakerArray.size(); i++) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId(i);
                rdbtn.setText(user_CaretakerArray.get(i).getName());
                rdbtn.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                rdbtn.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                rdbtn.setGravity(Gravity.START);
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 230, getResources().getDisplayMetrics());
                rdbtn.setWidth(width);
                caretakerList_RadioGroup.addView(rdbtn);
            }
        }
    }


}