package com.example.becarefall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SignUp2 extends AppCompatActivity {
    static c_Account user_Account;
    static c_User user_User;
    ConnectionDatabase connectDatabase;

    TextView phone_Error;

    EditText name_Input;
    EditText phone_Input;
    EditText address_Input;

    Button confirm;

    String userID;
    String accountID;
    String name;
    String phone;
    String status;
    String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        Intent i = getIntent();
        user_Account=(c_Account) i.getSerializableExtra("userAccount");
        Bundle extras = getIntent().getExtras();
        userID =extras.getString("userID");
        status = "normal";
        accountID = user_Account.getAccountID();
        Log.e("SignUp2 check userID","ID:"+userID);
        Log.e("Check SignUp2 userAccount info" ,"ID:" + user_Account.getAccountID() +" username:" + user_Account.getUsername() +" password "+user_Account.getPassword());

        phone_Error=(TextView)findViewById(R.id.su2_phone_error);
        phone_Error.setVisibility(View.INVISIBLE);
        name_Input =(EditText)findViewById(R.id.su2_name_box);
        phone_Input=(EditText)findViewById(R.id.su2_phone_box);
        address_Input=(EditText)findViewById(R.id.su2_address_box);
        confirm = (Button)findViewById(R.id.su2_confirm_button);
        connectDatabase();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_Input.getText().toString();
                phone_Input.getText().toString();
                address_Input.getText().toString();

                if(name_Input.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please Fill in your name",Toast.LENGTH_SHORT).show();
                }
                else if(phone_Input.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please Fill in your phone number",Toast.LENGTH_SHORT).show();
                }
                else if(phone_Input.length()!=10)
                {
                    phone_Error.setVisibility(View.VISIBLE);
                }
                else if(address_Input.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please Fill in your address",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    name = name_Input.getText().toString();
                    phone = phone_Input.getText().toString();
                    address = address_Input.getText().toString();
                    user_User = new c_User(userID,accountID,name,phone,status,address);
                    insertAccountInfo();
                    insertUserInfo();

                    Intent intent = new Intent(SignUp2.this,Home.class);
                    intent.putExtra("userAccount",user_Account);
                    intent.putExtra("userUser",user_User);
                    startActivity(intent);
                }


            }
        });



    }

    public void connectDatabase()
    {
        ConnectionDatabase connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);


    }

    public void insertAccountInfo()
    {

        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);
        connectDatabase.insertAccountInfo(user_Account);
    }
    public void insertUserInfo()
    {

        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);
        connectDatabase.insertUserInfo(user_User);
    }
}