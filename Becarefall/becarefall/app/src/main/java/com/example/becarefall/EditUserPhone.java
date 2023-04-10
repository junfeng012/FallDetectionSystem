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

public class EditUserPhone extends AppCompatActivity {
    ConnectionDatabase connectDatabase;
    static c_Account user_Account;
    static c_User user_User;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    EditText name_Input;
    Button save_Button;
    ImageButton homeButton_Image;
    String phone;
    static int userCaretakerArrayEmpty ;
    static int SensorIsActivated ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_phone);

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



        Log.e("Check User Account in EditUserPhone class","ID:"+user_Account.getAccountID()+" username:"+user_Account.getUsername());
        Log.e("Check User User in EditUserPhone class","userID:"+user_User.getUserID()+" accountID:"+user_User.getAccountID());

        save_Button = (Button) findViewById(R.id.euphone_Save_Button);
        name_Input = (EditText)findViewById(R.id.euphone_Phone_Box);
        homeButton_Image=(ImageButton) findViewById(R.id.euphone_HomeButton_Image);



        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = name_Input.getText().toString();
                if(phone.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Fill in the phone number",Toast.LENGTH_SHORT).show();
                }
                else if (phone.length()!=10)
                {
                    Toast.makeText(getApplicationContext(),"Please input valid phone number",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    user_User.setPhoneNumber(phone);
                    connectDatabase();
                    Toast.makeText(getApplicationContext(),"User's phone changed successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditUserPhone.this,EditUserFinish.class);
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
            }
        });
        homeButton_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUserPhone.this,Home.class);
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
        Log.e("Connect database in EditUserPhone", "connecting");
        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);
        connectDatabase.updateUserInfo(user_User);

    }


}