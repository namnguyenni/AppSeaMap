package com.example.getlocationbyservice;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

public class GPS_service extends Service {
    public GPS_service() {
    }

    private LocationManager mLocationManager;
    public static  final int TIME_MIN = 5000;
    public static  final float DISTANCE_MIN = 10F;
    private LocationListener mLocationListener;

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent intent = new Intent("update service");
                intent.putExtra(" coordinate ","location : "+location.getLongitude() + "+" + location.getLatitude());
                sendBroadcast(intent);


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }
//IF PROVIDER DONT ACCEPT , DIRECT USER TO SETTING.
            @Override
            public void onProviderDisabled(String s) {
                Intent in = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        };
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


        //CHECK PERMISSION IS MISSING
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,TIME_MIN,DISTANCE_MIN,mLocationListener);

    }

    @Override
    public void onDestroy() {
        if (mLocationManager!=null)
            mLocationManager.removeUpdates(mLocationListener);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
     return null;
    }
}
