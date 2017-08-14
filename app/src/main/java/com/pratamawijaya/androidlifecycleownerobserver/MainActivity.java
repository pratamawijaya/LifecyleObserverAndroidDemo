package com.pratamawijaya.androidlifecycleownerobserver;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pratamawijaya.androidlifecycleownerobserver.tools.MyLocationListener;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, LocationListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_LOCATION = 1;

    private MyLocationListener myLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLocationListener = new MyLocationListener(this, this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        requestPermissionLocation();
    }

    @AfterPermissionGranted(RC_LOCATION)
    private void requestPermissionLocation() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            myLocationListener.start();
        } else {
            EasyPermissions.requestPermissions(this, "Need permission location",
                    RC_LOCATION, perms);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        myLocationListener.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == RC_LOCATION) {
            myLocationListener.start();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: " + location.getLatitude() + " "
                + location.getLongitude() + " acc :" + location.getAccuracy());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
