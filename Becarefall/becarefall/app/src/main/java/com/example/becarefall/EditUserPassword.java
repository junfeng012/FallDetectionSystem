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

public class EditUserPassword extends AppCompatActivity  {
    ConnectionDatabase connectDatabase;
    static c_Account user_Account;
    static c_User user_User;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    EditText oldPassword_Input;
    EditText newPassword_Input;
    Button save_Button;
    ImageButton homeButton_Image;
    static int userCaretakerArrayEmpty ;
    static int SensorIsActivated ;


    String oldPassword;
    int oldPassword_Length;
    String newPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_password);

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



        Log.e("Check User Account in EditUserName class","ID:"+user_Account.getAccountID()+" username:"+user_Account.getUsername());
        Log.e("Check User User in EditUserName class","userID:"+user_User.getUserID()+" accountID:"+user_User.getAccountID());

        save_Button = (Button) findViewById(R.id.eupassword_Save_Button);
        oldPassword_Input = (EditText)findViewById(R.id.eupassword_OldPassword_Box);
        newPassword_Input = (EditText)findViewById(R.id.eupassword_NewPassword_Box);
        homeButton_Image=(ImageButton) findViewById(R.id.eupassword_HomeButton_Image);



        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword = oldPassword_Input.getText().toString();
                oldPassword_Length=oldPassword.length();
                newPassword = newPassword_Input.getText().toString();
                if(newPassword.isEmpty() || oldPassword.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Fill in the Password",Toast.LENGTH_SHORT).show();
                }
                else if(!oldPassword.equals(user_Account.getPassword().substring(0,oldPassword_Length)))
                {
                    Toast.makeText(getApplicationContext(),"Incorrect Old password",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    user_Account.setPassword(newPassword);
                    connectDatabase();
                    Toast.makeText(getApplicationContext(),"User's password changed successfully",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(EditUserPassword.this,EditPasswordFinish.class);
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
                Intent intent = new Intent(EditUserPassword.this,Home.class);
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
        Log.e("Connect database in EditUserPassword", "connecting");
        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);
        connectDatabase.updateAccountInfo(user_Account);

    }

}