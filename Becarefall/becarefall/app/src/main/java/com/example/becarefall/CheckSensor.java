package com.example.becarefall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class CheckSensor extends AppCompatActivity implements SensorEventListener {
    static c_Account user_Account;
    static c_User user_User;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_Caretaker> user_CaretakerArray;
    ImageButton homeButton_Image;
    static int userCaretakerArrayEmpty ;
    static int SensorIsActivated;


    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView xValue, yValue, zValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sensor);

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

        if(SensorIsActivated==1) {
            checkSensor();
        }

        homeButton_Image=(ImageButton) findViewById(R.id.cs_HomeButton_Image);

        homeButton_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckSensor.this,Home.class);
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

    public void checkSensor()
    {
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        //Accelerometer Sensor
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        xValue=(TextView) findViewById(R.id.cs_XValue);
        yValue=(TextView) findViewById(R.id.cs_YValue);
        zValue=(TextView) findViewById(R.id.cs_ZValue);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        xValue.setText("X: "+event.values[0]);
        yValue.setText("Y: "+event.values[1]);
        zValue.setText("Z: "+event.values[2]);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}