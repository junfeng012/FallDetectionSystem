package com.example.becarefall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ConfirmStatusBad extends AppCompatActivity {
    private static final int REQUEST_CALL = 0;
    ConnectionDatabase connectDatabase;
    ConnectionDatabase connectDatabase2;

    static c_Account user_Account;
    static c_User user_User;
    static c_ContactRecord user_ContactRecord;
    static c_Caretaker user_Caretaker;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    static ArrayList<c_ContactRecord> c_ContactRecordArray;
    static int userCaretakerArrayEmpty ;
    static String  user_FallRecordID;
    ImageButton homeButton_Image;

    static MediaPlayer mediaPlayer;
    static SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_status_bad);

        Bundle extras = getIntent().getExtras();
        userCaretakerArrayEmpty = extras.getInt("userCaretakerArrayEmpty");
        user_FallRecordID = extras.getString("userFallRecordID");

        Intent i = getIntent();
        user_Account=(c_Account) i.getSerializableExtra("userAccount");
        user_User=(c_User) i.getSerializableExtra("userUser");
        c_CaretakerArray=(ArrayList<c_Caretaker>)i.getSerializableExtra("cCaretakerArray");

        if(userCaretakerArrayEmpty==0) {
            user_CaretakerArray = (ArrayList<c_Caretaker>) i.getSerializableExtra("userCaretakerArray");
            selectCaretakerContact();
            selectContactRecord();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    setContactRecord();
                    insertContactRecord();
                    cancel();
                }
            }, 1000);
            //contactCaretaker();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"User's caretaker list is empty ,Please add caretaker",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ConfirmStatusBad.this,Home.class);
            intent.putExtra("userAccount",user_Account);
            intent.putExtra("userUser",user_User);
            intent.putExtra("cCaretakerArray",c_CaretakerArray);
            if(userCaretakerArrayEmpty==0) {
                intent.putExtra("userCaretakerArray", user_CaretakerArray);
            }
            intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
            intent.putExtra("SensorIsActivated",1);
            startActivity(intent);
        }

        //stopSound();
        soundManager.stopSound();

        homeButton_Image=(ImageButton) findViewById(R.id.csb_HomeButton_Image);

        homeButton_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmStatusBad.this,Home.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                intent.putExtra("SensorIsActivated",1);
                startActivity(intent);
            }
        });



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

    public void setContactRecord()
    {
        int contactRecordArraySize = c_ContactRecordArray.size()+1;
        String contactRecordID = "";
        if(c_ContactRecordArray.size()<9) {
            contactRecordID = "CR00"+String.valueOf(contactRecordArraySize);
        }
        else if(c_ContactRecordArray.size()<99)
        {
            contactRecordID = "CR0"+String.valueOf(contactRecordArraySize);
        }
        String userID =user_User.getUserID();
        String name = user_Caretaker.getName();
        String phone = user_Caretaker.getPhone();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        String time =date.substring(11,19);
        String pickUp = " ";

        Log.e("Check user_ContactRecord","CRID:"+contactRecordID+" fallRecordID:"+user_FallRecordID );
        user_ContactRecord = new c_ContactRecord(contactRecordID,user_FallRecordID,userID,name,phone,time,pickUp);

    }


    public void selectContactRecord()
    {
        Log.e("Connect database in ConfirmStatusBad", "connecting");
        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);
        connectDatabase.selectContactRecordInfo(new volleyCallback() {
            @Override
            public void onSuccess(ArrayList<c_Account> accountArray) {

            }
            public void onUserSuccess(ArrayList<c_User> userArray) {

            }
            public void onCaretakerSuccess(ArrayList<c_Caretaker> caretakerArray) {


            }
            public void onFallRecordSuccess(ArrayList<c_FallRecord> fallRecordArray) {

            }
            public void onContactRecordSuccess(ArrayList<c_ContactRecord> contactRecordArray) {
                c_ContactRecordArray = contactRecordArray;
                for (int i = 0;i<c_ContactRecordArray.size();i++) {
                    Log.e("check contact record", "ID:" + c_ContactRecordArray.get(i).getContactRecordID());
                }


            }
        });
    }

    public void insertContactRecord()
    {
        Log.e("Connect database in ConfirmStatusBad", "connecting");
        connectDatabase2 = new ConnectionDatabase();
        connectDatabase2.setActivity(this);
        connectDatabase2.insertContactRecordInfo(user_ContactRecord);
    }

    public void contactCaretaker()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String phoneNumber = "+6"+user_Caretaker.getPhone();
            Intent dial = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            startActivity(dial);
        }
    }

    public void stopSound() {
        mediaPlayer  = MediaPlayer.create(this,R.raw.alarm);
        mediaPlayer.seekTo(0);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}