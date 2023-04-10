package com.example.becarefall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import org.w3c.dom.Text;
import android.telephony.SmsManager;

import java.util.ArrayList;

public class ConfirmStatus extends AppCompatActivity {
    ConnectionDatabase connectDatabase;
    ConnectionDatabase connectDatabase2;

    static c_Account user_Account;
    static c_User user_User;
    static c_Caretaker user_Caretaker;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    static ArrayList<c_FallRecord> c_fallRecordArray;
    ImageButton okay_Image;
    ImageButton help_Image;
    TextView timer_Text;
    static int userCaretakerArrayEmpty ;
    CountDownTimer countDownTimer;

    static c_FallRecord user_FallRecord;
    static int timer;



    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_status);


        Bundle extras = getIntent().getExtras();
        userCaretakerArrayEmpty = extras.getInt("userCaretakerArrayEmpty");

        Intent i = getIntent();
        user_Account=(c_Account) i.getSerializableExtra("userAccount");
        user_User=(c_User) i.getSerializableExtra("userUser");
        c_CaretakerArray=(ArrayList<c_Caretaker>)i.getSerializableExtra("cCaretakerArray");
        if(userCaretakerArrayEmpty==0) {
            user_CaretakerArray = (ArrayList<c_Caretaker>) i.getSerializableExtra("userCaretakerArray");
        }





        if (userCaretakerArrayEmpty ==0) {
            selectCaretakerContact();
            //checkPriority();
            //checkSMS();
            //sendSMSTest();
            //sendSMSIntent();

            //sendSMS();
            selectFallRecord();


            timer = 30;
            if(countDownTimer== null) {
                startTimer();

            }
        }
        else
        {
            Intent intent = new Intent(ConfirmStatus.this,Home.class);
            intent.putExtra("userAccount",user_Account);
            intent.putExtra("userUser",user_User);
            intent.putExtra("cCaretakerArray",c_CaretakerArray);
            if(userCaretakerArrayEmpty==0) {
                intent.putExtra("userCaretakerArray", user_CaretakerArray);
            }
            intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
            intent.putExtra("SensorIsActivated",1);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"No linked caretaker found",Toast.LENGTH_SHORT).show();
        }

        okay_Image =(ImageButton) findViewById(R.id.cs_Okay_ImageButton);
        help_Image =(ImageButton) findViewById(R.id.cs_Help_ImageButton);
        timer_Text= (TextView) findViewById(R.id.cs_timer_Text);

        okay_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserFallRecord("no");
                insertFallRecord();
                countDownTimer.cancel();
                Intent intent = new Intent(ConfirmStatus.this,ConfirmStatusOk.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                intent.putExtra("userFallRecordID",user_FallRecord.getFallRecordID());
                startActivity(intent);
            }
        });

        help_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserFallRecord("yes");
                insertFallRecord();
                countDownTimer.cancel();
                Intent intent = new Intent(ConfirmStatus.this,ConfirmStatusBad.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                intent.putExtra("userFallRecordID",user_FallRecord.getFallRecordID());
                startActivity(intent);
            }
        });

    }

    public void startTimer()
    {
        countDownTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer_Text.setText("Remaining :" + String.valueOf(timer) +" seconds.....");
                timer -=1;
            }

            @Override
            public void onFinish() {
                setUserFallRecord("yes");
                insertFallRecord();
                Intent intent = new Intent(ConfirmStatus.this,ConfirmStatusBad.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                intent.putExtra("userFallRecordID",user_FallRecord.getFallRecordID());
                startActivity(intent);

            }
        }.start();
    }

    public void selectFallRecord()
    {
        Log.e("Connect database in ConfirmStatus", "connecting");
        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);
        connectDatabase.selectFallRecordInfo(new volleyCallback() {
            @Override
            public void onSuccess(ArrayList<c_Account> accountArray) {

            }
            public void onUserSuccess(ArrayList<c_User> userArray) {

            }
            public void onCaretakerSuccess(ArrayList<c_Caretaker> caretakerArray) {


            }
            public void onFallRecordSuccess(ArrayList<c_FallRecord> fallRecordArray) {
                c_fallRecordArray = fallRecordArray;
                for (int i = 0;i<c_fallRecordArray.size();i++) {
                    Log.e("check fall record", "ID:" + c_fallRecordArray.get(i).getFallRecordID());
                }
            }
            public void onContactRecordSuccess(ArrayList<c_ContactRecord> contactRecordArray) {


            }
        });
    }

    public void setUserFallRecord(String call)
    {
        int fallRecordArraySize = c_fallRecordArray.size()+1;
        String fallRecordID = "";
        if(c_fallRecordArray.size()<9) {
             fallRecordID = "FR00"+String.valueOf(fallRecordArraySize);
        }
        else if(c_fallRecordArray.size()<99)
        {
             fallRecordID = "FR0"+String.valueOf(fallRecordArraySize);
        }
        String userID =user_User.getUserID();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        String time =date.substring(11,19);
        date = date.substring(0,10);
        String emergencyCall = call;

        user_FallRecord = new c_FallRecord(fallRecordID,userID,date,time,emergencyCall);

    }

    public void insertFallRecord()
    {
        Log.e("Connect database in ConfirmStatus", "connecting");
        connectDatabase2 = new ConnectionDatabase();
        connectDatabase2.setActivity(this);
        connectDatabase2.insertFallRecordInfo(user_FallRecord);

    }

    public void selectCaretakerContact()
    {
        int priority_index=-1;
        String priority;
        String caretakerID;
        String accountID ;
        String userID ;
        String name ;
        String email ;
        String phone ;
        String availability ;


        for(int i=0 ; i<user_CaretakerArray.size();i++)
        {
            priority = user_CaretakerArray.get(i).getPriority();
            if(priority.equals("yes"))
            {
                Log.e("Priority Checking","Priority caretaker is :"+user_CaretakerArray.get(i).getCaretakerID());
                priority_index =i;
            }
        }
        if (priority_index == -1)
        {
            caretakerID=user_CaretakerArray.get(0).getCaretakerID();
            accountID = user_CaretakerArray.get(0).getAccountID();
            userID = user_CaretakerArray.get(0).getUserID();
            name = user_CaretakerArray.get(0).getName();
            email = user_CaretakerArray.get(0).getEmail();
            phone = user_CaretakerArray.get(0).getPhone();
            availability = user_CaretakerArray.get(0).getAvailability();
            priority = user_CaretakerArray.get(0).getPriority();
            user_Caretaker = new c_Caretaker(caretakerID, accountID, userID, name, email, phone, availability, priority);

        }
        else
        {
            caretakerID=user_CaretakerArray.get(priority_index).getCaretakerID();
            accountID = user_CaretakerArray.get(priority_index).getAccountID();
            userID = user_CaretakerArray.get(priority_index).getUserID();
            name = user_CaretakerArray.get(priority_index).getName();
            email = user_CaretakerArray.get(priority_index).getEmail();
            phone = user_CaretakerArray.get(priority_index).getPhone();
            availability = user_CaretakerArray.get(priority_index).getAvailability();
            priority = user_CaretakerArray.get(priority_index).getPriority();
            user_Caretaker = new c_Caretaker(caretakerID, accountID, userID, name, email, phone, availability, priority);

        }
    }

    public void checkPriority()
    {
        Toast.makeText(getApplicationContext(),"Caretaker Priority : "+user_Caretaker.getName(),Toast.LENGTH_SHORT).show();
    }
    public void checkSMS()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    public void sendSMSTest()
    {
        String phoneNo = "+6"+user_Caretaker.getPhone();
        String message = "The system had detected the person :" + user_User.getName()+"Fall down" ;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
    }
    public void sendSMS()
    {
        String phoneNo = "+6"+user_Caretaker.getPhone();
        String message = "The system had detected the person :" + user_User.getName()+"Fall down" ;
       //String message = "The system had detected the person :" + user_User.getName() +" fell down at " +user_User.getAddress() +" .Please contact the user to make sure he is okay ";

       if(ContextCompat.checkSelfPermission(ConfirmStatus.this,Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
           SmsManager smsManager = SmsManager.getDefault();
           smsManager.sendTextMessage(phoneNo, null, message, null, null);
           Log.e("SMS sent","SMS sent to" +user_Caretaker.getName() +"("+phoneNo+")");
           Toast.makeText(getApplicationContext(),"SMS sent to" +user_Caretaker.getName() +"("+phoneNo+")",Toast.LENGTH_SHORT).show();
       }
       else
       {
           Toast.makeText(getApplicationContext(),"Fail to send SMS to "+user_Caretaker.getName()+"("+phoneNo+")"+", please allow the application to send SMS",Toast.LENGTH_SHORT).show();
       }
    }

    public void sendSMSIntent()
    {
        String phoneNo = "+6"+user_Caretaker.getPhone();
        String message = "The system had detected the person :" + user_User.getName() +" fell down at " +user_User.getAddress() +" .Please contact the user to make sure he is okay ";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phoneNo));  // This ensures only SMS apps respond
        intent.putExtra("sms_body", message);
        startActivity(intent);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Fail to send SMS to "+user_Caretaker.getName()+"("+phoneNo+")"+", please allow the application to send SMS",Toast.LENGTH_SHORT).show();

        }
    }

}