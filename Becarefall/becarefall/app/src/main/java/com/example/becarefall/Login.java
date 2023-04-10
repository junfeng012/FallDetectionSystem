package com.example.becarefall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    ConnectionDatabase connectDatabase;
    ConnectionDatabase connectDatabase2;
    String accountID;
    String username;
    String password;
    String c_AccountAccountID;
    String c_AccountUsername;
    String c_AccountPassword;
    String c_UserUserID;
    String c_UserAccountID;
    String c_UserName;
    String c_UserPhone;
    String c_UserStatus;
    String c_UserAddress;
    int username_Length;
    int password_Length;
    int valid_input;

    TextView loginError;
    EditText username_Input;
    EditText password_Input;

    Button login_Button;
    static ArrayList<c_Account> c_AccountArray;
    static ArrayList<c_User> c_UserArray;
    static c_Account user_Account;
    static c_User user_User;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //SignUp Text -Start
        TextView l_Login_Text = findViewById(R.id.l_Login_Text);

        String Login_Text = "Sign Up";
        SpannableString ss = new SpannableString(Login_Text);
        ClickableSpan ClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                openSignUp();
            }
        };

        ss.setSpan(ClickableSpan,0,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        l_Login_Text.setText(ss);
        l_Login_Text.setMovementMethod(LinkMovementMethod.getInstance());
        //SignUp Text -End

        //get username and password Input -start
        loginError =(TextView) findViewById(R.id.l_error_text);
        loginError.setVisibility(View.INVISIBLE);
        username_Input = (EditText) findViewById(R.id.eupassword_OldPassword_Box);
        password_Input =(EditText) findViewById(R.id.l_Password_Box);
        login_Button =(Button) findViewById(R.id.l_Login_Button);
        c_AccountArray = new ArrayList<>();
        connectDatabase();
        user_Account = new c_Account(" "," "," ");

        login_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            //    Log.e("Check Account Array Size","size:"+c_AccountArray.size());
            //    Log.e("Check Account Array Size","size:"+c_UserArray.size());

                username = username_Input.getText().toString();
                password = password_Input.getText().toString();
                valid_input=0;
               // Log.e("Check User Input" , " Username(" + username.length()+"):"+username+" Password("+ password.length()+")"+password);
                username_Length=username.length();
                password_Length=password.length();

                if(username_Length ==0 || password_Length ==0)
                {
                    Toast.makeText(getApplicationContext(),"Please Fill in Username and Password",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (c_AccountArray.size() == 0) {
                       // Log.e("Account Array Error", "c_AccountAray.size = 0 ");
                        finish();
                        System.exit(0);
                    }
                    for (int i = 0; i < c_AccountArray.size(); i++) {

                        c_AccountAccountID = c_AccountArray.get(i).getAccountID();
                        String temp_AccountID = c_AccountAccountID.substring(0,2);
                        c_AccountUsername = c_AccountArray.get(i).getUsername();
                        c_AccountUsername = trimName(c_AccountUsername);
                        c_AccountPassword = c_AccountArray.get(i).getPassword();
                        c_AccountPassword = trimName(c_AccountPassword);
                       // Log.e("Check Account Array", "ID:" + c_AccountAccountID + " Username:" + c_AccountUsername + " Password:" + c_AccountPassword);
                       // Log.e("Check login Matching" ,"username:"+c_AccountUsername.substring(0,username_Length) +" password:"+ c_AccountPassword.substring(0,password_Length) );
                        if(username.equals(c_AccountUsername)  && password.equals(c_AccountPassword) && temp_AccountID.equals("AU"))
                        {
                            valid_input = 1;
                            user_Account.setAccountID(c_AccountAccountID);
                            user_Account.setUsername(username);
                            user_Account.setPassword(password);
                        }

                    }
                    for (int i = 0; i < c_UserArray.size(); i++){
                        c_UserUserID = c_UserArray.get(i).getUserID();
                        c_UserAccountID = c_UserArray.get(i).getAccountID();
                        c_UserName=c_UserArray.get(i).getName();
                        c_UserPhone=c_UserArray.get(i).getPhoneNumber();
                        c_UserStatus=c_UserArray.get(i).getStatus();
                        c_UserAddress=c_UserArray.get(i).getAddress();

                        if(user_Account.getAccountID().equals(c_UserAccountID) && valid_input ==1)
                        {
                            user_User= new c_User(c_UserUserID,c_UserAccountID,c_UserName,c_UserPhone,c_UserStatus,c_UserAddress);
                        }
                    }

                    if(valid_input == 1)
                    {
                        Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Login.this,Home.class);
                        intent.putExtra("userAccount",user_Account);
                        intent.putExtra("userUser",user_User);

                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Wrong Username or Password",Toast.LENGTH_SHORT).show();
                        loginError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        //get username and password Input -end
    }

    public void openSignUp()
    {
        Intent intent  = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    public void connectDatabase()
    {
        Log.e("Connect database in Login", "connecting");
        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);
        connectDatabase.selectAccount(new volleyCallback() {
            @Override
            public void onSuccess(ArrayList<c_Account> accountArray) {
                c_AccountArray =accountArray;
            }
            public void onUserSuccess(ArrayList<c_User> userArray) {
                c_UserArray =userArray;
            }
            public void onCaretakerSuccess(ArrayList<c_Caretaker> caretakerArray) {

            }
            public void onFallRecordSuccess(ArrayList<c_FallRecord> fallRecordArray) {

            }
            public void onContactRecordSuccess(ArrayList<c_ContactRecord> contactRecordArray) {


            }

        });

        connectDatabase2 = new ConnectionDatabase();
        connectDatabase2.setActivity(this);
        connectDatabase2.selectUser(new volleyCallback() {
            @Override
            public void onSuccess(ArrayList<c_Account> accountArray) {
                c_AccountArray =accountArray;
            }
            public void onUserSuccess(ArrayList<c_User> userArray) {
                c_UserArray =userArray;
            }
            public void onCaretakerSuccess(ArrayList<c_Caretaker> caretakerArray) {

            }
            public void onFallRecordSuccess(ArrayList<c_FallRecord> fallRecordArray) {

            }
            public void onContactRecordSuccess(ArrayList<c_ContactRecord> contactRecordArray) {


            }
        });



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