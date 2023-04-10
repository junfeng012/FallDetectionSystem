package com.example.becarefall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {
    private static final int REQUEST_CALL_AND_SMS = 0;
    ConnectionDatabase connectDatabase;
    ConnectionDatabase connectDatabase2;
    static String accountID;
    static String username;
    static String password;
    static String c_AccountAccountID;
    static String userID;
    String c_AccountUsername;
    String c_AccountPassword;
    int username_Length;
    int password_Length;
    int account_input_taken;

    TextView signUpError;
    EditText username_Input;
    EditText password_Input;

    Button signUp_Button;

    static ArrayList<c_Account> c_AccountArray;
    static ArrayList<c_User> c_UserArray;
    static c_Account user_Account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Login Text -Start
        TextView su_Login_Text = findViewById(R.id.su_Login_Text);

        String Login_Text = "Login";
        SpannableString ss = new SpannableString(Login_Text);
        ClickableSpan ClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                openLogin();
            }
        };

        ss.setSpan(ClickableSpan,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        su_Login_Text.setText(ss);
        su_Login_Text.setMovementMethod(LinkMovementMethod.getInstance());
        //Login Text -End

        //get username and password Input -start
        signUpError = (TextView) findViewById(R.id.su_error_text);
        signUpError.setVisibility(View.INVISIBLE);
        username_Input = (EditText) findViewById(R.id.su_Username_Box);
        password_Input =(EditText) findViewById(R.id.su_Password_Box);
        signUp_Button =(Button) findViewById(R.id.su_SignUp_Button);
        c_AccountArray = new ArrayList<>();
        connectDatabase();
        enableCallAndSMSPermission();
        enableNotificationPermission();

        signUp_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                username = username_Input.getText().toString();
                password = password_Input.getText().toString();

                account_input_taken=0;
                //Log.e("Check User Input" , " Username(" + username.length()+"):"+username+" Password("+ password.length()+")"+password);
                username_Length=username.length();
                password_Length=password.length();

                if(username_Length ==0 || password_Length ==0)
                {
                    Toast.makeText(getApplicationContext(),"Please Fill in Username and Password",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (c_AccountArray.size() == 0) {
                        Log.e("Account Array Error", "c_AccountAray.size = 0 ");
                        finish();
                        System.exit(0);
                    }
                    for (int i = 0; i < c_AccountArray.size(); i++) {

                        c_AccountAccountID = c_AccountArray.get(i).getAccountID();
                        c_AccountUsername = c_AccountArray.get(i).getUsername();
                        c_AccountPassword = c_AccountArray.get(i).getPassword();
                       // Log.e("size of AccountArray" , "size:" +c_AccountArray.size());
                       // Log.e("Check Account Array", "ID:" + c_AccountAccountID + " Username:" + c_AccountUsername + " Password:" + c_AccountPassword);
                       // Log.e("Check login Matching" ,"username:"+c_AccountUsername.substring(0,username_Length) +" password:"+ c_AccountPassword.substring(0,password_Length) );
                        if(username.equals(c_AccountUsername.substring(0,username_Length)))
                        {
                            account_input_taken= 1;

                        }

                    }

                    if(account_input_taken == 1)
                    {
                        Toast.makeText(getApplicationContext(),"Username has been taken , please use different username",Toast.LENGTH_SHORT).show();
                        signUpError.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        if (c_AccountArray.size()<9)
                        {
                            accountID = "AU00"+Integer.toString(c_AccountArray.size()+1);
                        }
                        else if(c_AccountArray.size()<99)
                        {
                            accountID = "AU0"+Integer.toString(c_AccountArray.size()+1);
                        }
                        if (c_UserArray.size()<9)
                        {
                            userID = "U00"+Integer.toString(c_UserArray.size()+1);
                        }
                        else if(c_UserArray.size()<99)
                        {
                            userID = "U0"+Integer.toString(c_UserArray.size()+1);
                        }

                        user_Account= new c_Account(accountID,username,password);
                       // insertAccountInfo();
                        Intent intent = new Intent(MainActivity.this,SignUp2.class);
                        intent.putExtra("userAccount",user_Account);
                        intent.putExtra("userID",userID);
                        startActivity(intent);
                    }
                }

            }
        });
        //get username and password Input -end
    }

    public void openLogin()
    {
        Intent intent  = new Intent(this,Login.class);
        startActivity(intent);
    }



    public void connectDatabase()
    {
        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);
        connectDatabase.selectAccount(new volleyCallback() {
            @Override
            public void onSuccess(ArrayList<c_Account> accountArray) {
                c_AccountArray =accountArray;
            }
            @Override
            public void onUserSuccess(ArrayList<c_User> userArray) {

            }
            public void onCaretakerSuccess(ArrayList<c_Caretaker> caretakerArray) {

            }
            public void onFallRecordSuccess(ArrayList<c_FallRecord> fallRecordArray) {

            }
            public void onContactRecordSuccess(ArrayList<c_ContactRecord> contactRecordArray) {


            }
        });

       // Log.e("Check Account Array Size","size:"+c_AccountArray.size());
        for (int i = 0 ;  i < c_AccountArray.size();i++)
        {
      //      Log.e("Check Account Array" , c_AccountArray.get(i).getAccountID()+c_AccountArray.get(i).getUsername()+c_AccountArray.get(i).getPassword() );
        }

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

    public void insertAccountInfo()
    {

        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);
        connectDatabase.insertAccountInfo(user_Account);
    }
    public void enableCallAndSMSPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS}, REQUEST_CALL_AND_SMS);
        }
    }
    public void enableNotificationPermission()
    {
        String channelId = "channel_01";
        CharSequence channelName = "My Channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

        NotificationManager notificationManager =
                getSystemService(NotificationManager.class);

        notificationManager.createNotificationChannel(channel);
    }


}