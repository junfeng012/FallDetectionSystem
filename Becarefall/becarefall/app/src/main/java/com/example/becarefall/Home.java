package com.example.becarefall;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;


public class Home extends AppCompatActivity implements SensorEventListener {
    private static final int REQUEST_CALL = 0;
    private static final int REQUEST_CALL_AND_SMS = 0;
    private static final int PERMISSION_SEND_SMS = 0;
    private static final int REQUEST_CODE_NOTIFICATION = 0;
    static int  notificationID=1;
    ConnectionDatabase connectDatabase;
    static c_Account user_Account;
    static c_User user_User;
    static c_Dashboard user_Dashboard;
    static c_FallRequest user_FallRequest;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    static int userCaretakerArrayEmpty ;
    static Boolean caretakerUpdate;


    Button manageCaretaker_Button;
    Button manageAccount_Button;
    Button logout_Button;
    Button checkSensor_Button;

    Switch activateSensor_Switch;
    Switch activateStatus_Switch;

    static int SensorIsActivated ;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    static int StatusIsActivated;
    static int turnOnStatus;

    MqttAndroidClient client;
    Handler mHandler = new Handler();
    Runnable mRunnableTask;

    static float xVal;
    static float yVal;
    static float zVal;

    static SoundManager soundManager;
    static boolean startIntentFall;
    static boolean startIntentStatus;

    static SharedPreferences sharedPreferences;
    static StatusTimer statusTimer;
    static int statusTimerValue;

    static int testNotificationCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = getIntent();
        user_Account=(c_Account) i.getSerializableExtra("userAccount");
        user_User=(c_User) i.getSerializableExtra("userUser");

        Bundle extras = getIntent().getExtras();
        SensorIsActivated = extras.getInt("SensorIsActivated");

        Log.e("Check User Account in Home class","ID:"+user_Account.getAccountID()+" username:"+user_Account.getUsername());
        Log.e("Check User User in Home class","userID:"+user_User.getUserID()+" accountID:"+user_User.getAccountID());

        activateSensor_Switch=(Switch)findViewById(R.id.h_Sensor_Switch);
        activateStatus_Switch=(Switch)findViewById(R.id.h_Status_Switch);

        if(SensorIsActivated == 1)
        {
            activateSensor_Switch.setChecked(true);
            statusTimer.stopTimer();
            statusTimer.startTimer();
            checkSensor();
        }

        sharedPreferences = getSharedPreferences(user_User.getUserID(), MODE_PRIVATE);
        StatusIsActivated = sharedPreferences.getInt("statusSwitch", 0);
        turnOnStatus = 0;

        if(StatusIsActivated ==1)
        {
            activateStatus_Switch.setChecked(true);
            statusTimer.stopTimer();
            statusTimer.startTimer();
        }




        manageCaretaker_Button = (Button) findViewById(R.id.h_ManageCaretaker_Button);
        manageAccount_Button = (Button) findViewById(R.id.h_ManageAccount_Button);
        checkSensor_Button=(Button) findViewById(R.id.h_CheckSensor_Button);
        logout_Button=(Button) findViewById(R.id.h_Logout_Button);



        //enableCallPermission();
        //enableSMSPermission();
        enableCallAndSMSPermission();
        enableNotificationPermission();
        connectDatabase();
        soundManager.stopSound();
        startIntentFall=true;
        startIntentStatus =true;





        FirebaseDatabase database = FirebaseDatabase.getInstance("https://becarefall-83c57-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("fallRequest");


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                Object value = dataSnapshot.getValue();
                // Handle child added event
                user_FallRequest =dataSnapshot.getValue(c_FallRequest.class);
                //Toast.makeText(getApplicationContext(),"key:" +key+" request:"+user_FallRequest.getRequest()+" userid"+user_FallRequest.getUserid(),Toast.LENGTH_SHORT).show();

                if(user_FallRequest.getUserid().equals(user_User.getUserID())  && user_FallRequest.getRequest().equals("true")) {
                    //Toast.makeText(getApplicationContext(),"resetRequest confirm and send Notification",Toast.LENGTH_SHORT).show();
                    resetRequest(key,user_FallRequest.getCaretakername(), user_FallRequest.getCaretakerphone());
                    sendNotification(key,user_FallRequest.getCaretakername(), user_FallRequest.getCaretakerphone());
                    testNotificationCount+=1;
                    Toast.makeText(getApplicationContext(),"NotificationCount:" + Integer.toString(testNotificationCount),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // Handle child changed event
                String key = dataSnapshot.getKey();
                Object value = dataSnapshot.getValue();
                // key and value will give you the key and value of the changed child
                user_FallRequest =dataSnapshot.getValue(c_FallRequest.class);
                //Toast.makeText(getApplicationContext(),"key:" +key +"is changed with value("+user_FallRequest.getRequest()+user_FallRequest.getUserid() +")",Toast.LENGTH_SHORT).show();
                if(user_FallRequest.getUserid().equals(user_User.getUserID())  && user_FallRequest.getRequest().equals("true")) {
                    //Toast.makeText(getApplicationContext(),"resetRequest confirm and send Notification",Toast.LENGTH_SHORT).show();
                    resetRequest(key,user_FallRequest.getCaretakername(), user_FallRequest.getCaretakerphone());
                    sendNotification(key,user_FallRequest.getCaretakername(), user_FallRequest.getCaretakerphone());
                    testNotificationCount+=1;
                    Toast.makeText(getApplicationContext(),"NotificationCount:" + Integer.toString(testNotificationCount),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Handle child removed event
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // Handle child moved event
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }

        });




        caretakerUpdate = false;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for(int j =0; j<c_CaretakerArray.size();j++)
                {
                    Log.e("Check Caretaker ID at Home" ,"ID:"+c_CaretakerArray.get(j).getCaretakerID());
                }
                filterCaretaker();
                if(user_CaretakerArray.size()>0) {
                    for (int j = 0; j < user_CaretakerArray.size(); j++) {
                        Log.e("Check User Caretaker ID at Home", "ID:" + user_CaretakerArray.get(j).getCaretakerID());
                    }
                    userCaretakerArrayEmpty =0;
                }
                else
                {
                    userCaretakerArrayEmpty =1;
                    Log.e("userCaretakerArray is empty", "empty array");
                }
                caretakerUpdate = true;
                cancel();
            }
        }, 1000);



        manageCaretaker_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (caretakerUpdate) {
                    Intent intent = new Intent(Home.this, ManageCaretaker.class);
                    intent.putExtra("userAccount", user_Account);
                    intent.putExtra("userUser", user_User);
                    intent.putExtra("cCaretakerArray", c_CaretakerArray);
                    if (userCaretakerArrayEmpty == 0) {
                        intent.putExtra("userCaretakerArray", user_CaretakerArray);
                    }
                    intent.putExtra("userCaretakerArrayEmpty", userCaretakerArrayEmpty);
                    intent.putExtra("SensorIsActivated", SensorIsActivated);
                    startActivity(intent);
                } else
                {
                    Log.e("update Caretaker","not finish");
                }
            }
        });

        manageAccount_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,ManageAccount.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty ==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                intent.putExtra("SensorIsActivated",SensorIsActivated);
                startActivity(intent);
            }
        });

        checkSensor_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,CheckSensor.class);
                intent.putExtra("userAccount",user_Account);
                intent.putExtra("userUser",user_User);
                intent.putExtra("cCaretakerArray",c_CaretakerArray);
                if(userCaretakerArrayEmpty ==0) {
                    intent.putExtra("userCaretakerArray", user_CaretakerArray);
                }
                intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
                intent.putExtra("SensorIsActivated",SensorIsActivated);
                startActivity(intent);

            }
        });
        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SensorIsActivated=0;
                checkSensor();
                Intent intent = new Intent(Home.this,Login.class);
                startActivity(intent);
            }
        });

        activateSensor_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    SensorIsActivated=1;
                    statusTimer.stopTimer();
                    statusTimer.startTimer();
                    Toast.makeText(getApplicationContext(),"Sensor activated",Toast.LENGTH_SHORT).show();
                    checkSensor();
                }
                else
                {
                    SensorIsActivated=0;
                    Toast.makeText(getApplicationContext(),"Disable Sensor ",Toast.LENGTH_SHORT).show();
                    checkSensor();
                }
            }
        });

        activateStatus_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    StatusIsActivated = 1;
                    Toast.makeText(getApplicationContext(),"Status Switch Activated",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences(user_User.getUserID(), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("statusSwitch", StatusIsActivated);
                    editor.apply();
                    statusTimer.stopTimer();
                    statusTimer.startTimer();

                }
                else
                {
                    StatusIsActivated = 0;
                    Toast.makeText(getApplicationContext(),"Status Switch Disabled",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences(user_User.getUserID(), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("statusSwitch", StatusIsActivated);
                    editor.apply();
                    statusTimer.stopTimer();
                    statusTimer.startTimer();
                }
            }
        });
    }

    public void connectDatabase()
    {
        Log.e("Connect database in Home", "connecting");
        connectDatabase = new ConnectionDatabase();
        connectDatabase.setActivity(this);
        connectDatabase.selectCaretaker(new volleyCallback() {
            @Override
            public void onSuccess(ArrayList<c_Account> accountArray) {

            }
            public void onUserSuccess(ArrayList<c_User> userArray) {

            }
            public void onCaretakerSuccess(ArrayList<c_Caretaker> caretakerArray) {
                c_CaretakerArray =caretakerArray;

            }
            public void onFallRecordSuccess(ArrayList<c_FallRecord> fallRecordArray) {

            }
            public void onContactRecordSuccess(ArrayList<c_ContactRecord> contactRecordArray) {


            }
        });
    }

    public void filterCaretaker()
    {
        String userID = user_User.getUserID();
        user_CaretakerArray = new ArrayList<>();
        for(int j =0; j<c_CaretakerArray.size();j++)
        {
            if (userID.equals(c_CaretakerArray.get(j).getUserID()))
            {
                String caretakerID = c_CaretakerArray.get(j).getCaretakerID();
                String accountID =c_CaretakerArray.get(j).getAccountID();
                String name = c_CaretakerArray.get(j).getName();
                String email = c_CaretakerArray.get(j).getEmail();
                String phone = c_CaretakerArray.get(j).getPhone();
                String availability = c_CaretakerArray.get(j).getAvailability();
                String priority =c_CaretakerArray.get(j).getPriority();
                Log.e("Check Caretaker Info at Home" ,"caretakerID:"+caretakerID +" accountID:"+accountID);
                c_Caretaker user_Caretaker = new c_Caretaker(caretakerID,accountID,userID,name,email,phone,availability,priority);
                user_CaretakerArray.add(user_Caretaker);
            }
        }
    }
    public void checkSensor()
    {
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        //Accelerometer Sensor
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        if(SensorIsActivated ==0)
        {
            sensorManager.unregisterListener(this,accelerometer);
        }

    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        int zValueInt = Math.round(event.values[2]);

        xVal = event.values[0];
        yVal = event.values[1];
        zVal = event.values[2];

        float xSquare = xVal*xVal;
        float ySquare = yVal*yVal;
        float zSquare = zVal*zVal;
        float total= xSquare+ySquare+zSquare;
        double sqrtTotal = Math.sqrt(total);

        statusTimerValue = statusTimer.checkTimer();
        Log.e("check Timer Value(Home)", "Timer:"+Integer.toString(statusTimerValue));
        if(statusTimerValue == 0 && StatusIsActivated ==0 )
        {
            if(sqrtTotal >11)
            {
                StatusIsActivated =1;
                activateStatus_Switch.setChecked(true);
                statusTimer.stopTimer();
                statusTimer.startTimer();
                SharedPreferences sharedPreferences = getSharedPreferences(user_User.getUserID(), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("statusSwitch", StatusIsActivated);
                editor.apply();

            }
        }
        else if(statusTimerValue ==0 && StatusIsActivated ==1 && SensorIsActivated ==1 && startIntentStatus)
        {
            soundManager.playSound(this,R.raw.alarm);
            sendCheckStatusNotification();

            startIntentFall = false;
            startIntentStatus=false;

            sensorManager.unregisterListener(this,accelerometer);

            Intent intent = new Intent(this,CheckStatus.class);
            intent.putExtra("userAccount",user_Account);
            intent.putExtra("userUser",user_User);
            intent.putExtra("cCaretakerArray",c_CaretakerArray);
            if(userCaretakerArrayEmpty==0) {
                intent.putExtra("userCaretakerArray", user_CaretakerArray);
            }
            intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
            intent.putExtra("StatusIsActivated",StatusIsActivated);
            startActivity(intent);
        }
        else if(sqrtTotal >11 && StatusIsActivated ==1 && statusTimerValue >0)
        {
                statusTimer.stopTimer();
                statusTimer.startTimer();

        }

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://becarefall-83c57-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("accelerometer");
        String key = user_User.getUserID();
        user_Dashboard = new c_Dashboard(user_User.getUserID(),String.format("%.3f", sqrtTotal) ,"2","2");
        myRef.child(key).setValue(user_Dashboard);

        if (sqrtTotal >22 && startIntentFall)
        {
            soundManager.playSound(this,R.raw.alarm);
            startIntentFall = false;
            startIntentStatus=false;
            sendFallDetectNotification();
            sensorManager.unregisterListener(this,accelerometer);

            Intent intent = new Intent(this,ConfirmStatus.class);
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void startPublish()
    {
        String serverURI ="tcp://broker.hivemq.com:1883";
        String clientID= MqttClient.generateClientId();

        client = new MqttAndroidClient(this, serverURI,clientID);

        try {

            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(Home.this,"Connected",Toast.LENGTH_LONG).show();
                    publish();


                    mRunnableTask = new Runnable()
                    {
                        @Override
                        public void run() {
                            publish();
                            // this will repeat this task again at specified time interval
                            mHandler.postDelayed(this, 5000);
                        }
                    };

                    // Call this to start the task first time
                    mHandler.postDelayed(mRunnableTask, 5000);
                }


                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    System.out.print("Connection Failed");
                    Toast.makeText(Home.this,"Connection Failed",Toast.LENGTH_LONG).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void publish(){
        String mqttTopic = "";
        Log.d("Topic",mqttTopic);
        String message =xVal+","+yVal+","+zVal;
        try{
            client.publish(mqttTopic,message.getBytes(),0,false);
        }catch (MqttException e){
            e.printStackTrace();
        }


    }

    public void enableCallAndSMSPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS}, REQUEST_CALL_AND_SMS);
        }
    }

    public void enableCallPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
    }

    public void enableSMSPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);
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

    public void resetRequest(String key,String name,String phone)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://becarefall-83c57-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("fallRequest");
        user_FallRequest = new c_FallRequest("false",user_User.getUserID() ,name,phone);
        myRef.child(key).setValue(user_FallRequest);
    }


    public void sendNotification(String id,String name, String phone)
    {
        String channelId = "channel_01";
        CharSequence channelName = "My Channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

        NotificationManager notificationManager =
                getSystemService(NotificationManager.class);

        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Fall Status Request")
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Someone has Request for fall status \n(Caretaker Information) \nID:" + id + "\nName:" + name + "\nPhone:" + phone))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        // NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(notificationID, builder.build());
        notificationID +=1;

    }



    private void sendFallDetectNotification() {

        Intent intent = new Intent(this, ConfirmStatus.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("userAccount",user_Account);
        intent.putExtra("userUser",user_User);
        intent.putExtra("cCaretakerArray",c_CaretakerArray);
        if(userCaretakerArrayEmpty==0) {
            intent.putExtra("userCaretakerArray", user_CaretakerArray);
        }
        intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
        intent.putExtra("SensorIsActivated",SensorIsActivated);
        intent.putExtra("closeActivity",1);

        PendingIntent pendingIntent;


        if(Build.VERSION.SDK_INT >= 11)
        {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else
        {
            pendingIntent = PendingIntent.getActivity(this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_01")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Fall Detected")
                .setContentText("Prompt Confirm Status")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationID, builder.build());
    }

    private void sendCheckStatusNotification() {

        Intent intent = new Intent(this, CheckStatus.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("userAccount",user_Account);
        intent.putExtra("userUser",user_User);
        intent.putExtra("cCaretakerArray",c_CaretakerArray);
        if(userCaretakerArrayEmpty==0) {
            intent.putExtra("userCaretakerArray", user_CaretakerArray);
        }
        intent.putExtra("userCaretakerArrayEmpty",userCaretakerArrayEmpty);
        intent.putExtra("StatusIsActivated",StatusIsActivated);
        intent.putExtra("closeActivity",1);

        PendingIntent pendingIntent;


        if(Build.VERSION.SDK_INT >= 11)
        {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else
        {
            pendingIntent = PendingIntent.getActivity(this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_01")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Status Checking")
                .setContentText("Prompt Check Status")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationID, builder.build());
    }


}