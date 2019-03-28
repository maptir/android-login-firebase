package com.example.loginfirebase;

import android.Manifest;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ViewLocation extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;

    Handler h = new Handler();
    Thread task;
    private long startTime;
    private String timeString;
    private TextView timerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);

        timerView = findViewById(R.id.timerView);
        try {
            if(ActivityCompat.checkSelfPermission(this, mPermission) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callGetLocation(View view) {
        startTimer();
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
        task = new Thread() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                final long secs = millis / 1000 % 60;
                timeString = String.format("%02d", secs);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(secs % 5 == 0) {
                            gps = new GPSTracker(ViewLocation.this);
                            if(gps.canGetLocation()) {
                                double latitude = gps.getLatitude();
                                double longitude = gps.getLongitude();
                                Toast.makeText(getApplicationContext(),  secs +"secs: Current location is \n Lat: " + latitude + "\n Long: " + longitude, Toast.LENGTH_LONG).show();
                            } else {
                                gps.showSettingsAlert();
                            }
                        }
                        timerView.setText(timeString);
                        h.postDelayed(task, 1000);
                    }
                });
            }
        };
        h.postDelayed(task, 1000);

    }

    public void callStopTimer(View view) {
        stopHandlerTask();
        timerView.setText("Location Service is Stopped");
    }

    public void stopHandlerTask() {
        h.removeCallbacks(task);
    }
}
