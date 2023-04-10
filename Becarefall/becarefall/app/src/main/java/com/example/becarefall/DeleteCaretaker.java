package com.example.becarefall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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

public class DeleteCaretaker extends AppCompatActivity  {
    static c_Account user_Account;
    static c_User user_User;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    static c_Caretaker user_Caretaker;
    RadioGroup caretakerList_RadioGroup;
    ImageButton homeButton_Image;
    static int userCaretakerArrayEmpty ;
    Button delete_Button;
    static int chosenRadioID;
    static int cCaretakerArray_index;
    static int userCaretakerArray_index;
    static int SensorIsActivated ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_caretaker);

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

        addRadioButtons();

        delete_Button =(Button)findViewById(R.id.dc_Delete_Button);
        homeButton_Image=(ImageButton) findViewById(R.id.dc_HomeButton_Image);

        homeButton_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteCaretaker.this,Home.class);
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
                    userCaretakerArray_index = chosenRadioID;
                    find_cCaretakerArrayIndex();
                    Intent intent = new Intent(DeleteCaretaker.this,DeleteCaretaker2.class);
                    intent.putExtra("userAccount",user_Account);
                    intent.putExtra("userUser",user_User);
                    intent.putExtra("cCaretakerArray",c_CaretakerArray);
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                    intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                    intent.putExtra("userCaretaker",user_Caretaker);
                    intent.putExtra("cCaretakerArrayIndex",cCaretakerArray_index);
                    intent.putExtra("userCaretakerArrayIndex",userCaretakerArray_index);
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
        String userID = " ";
        String name = user_CaretakerArray.get(chosenRadioID).getName();
        String email = user_CaretakerArray.get(chosenRadioID).getEmail();
        String phone = user_CaretakerArray.get(chosenRadioID).getPhone();
        String availability = user_CaretakerArray.get(chosenRadioID).getAvailability();
        String priority ="no";
        user_Caretaker = new c_Caretaker(caretakerID,accountID,userID,name,email,phone,availability,priority);
    }

    public void addRadioButtons() {

        caretakerList_RadioGroup=(RadioGroup) findViewById(R.id.dc_CaretakerList_RadioGroup);
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

    public void find_cCaretakerArrayIndex()
    {
        cCaretakerArray_index = -1;
        String caretakerID = user_Caretaker.getAccountID();

        for(int i = 0 ; i< c_CaretakerArray.size() ; i++)
        {
            if(caretakerID == c_CaretakerArray.get(i).getCaretakerID())
            {
                cCaretakerArray_index = i;
            }
        }

    }

}