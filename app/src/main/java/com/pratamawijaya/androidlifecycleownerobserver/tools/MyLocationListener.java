package com.pratamawijaya.androidlifecycleownerobserver.tools;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;


/**
 * Created by pratama on 8/14/17.
 */

public class MyLocationListener {

    private static final String TAG = MyLocationListener.class.getSimpleName();
    private static final long MIN_TIME_UPDATE = 0;
    private static final float MIN_DISTANCE_UPDATE = 0;

    private Context context;
    private LocationManager locationManager;
    private LocationListener listener;


    public MyLocationListener(Context context, LocationListener listener) {
        this.context = context;
        this.listener = listener;
    }


    public void start() {
        Log.d(TAG, "start: ");
        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                MIN_TIME_UPDATE, MIN_DISTANCE_UPDATE, listener);

        // Force an update with the last location, if available.
        Location lastLocation = locationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER);
        if (lastLocation != null) {
            listener.onLocationChanged(lastLocation);
        }
    }

    public void stop() {
        Log.d(TAG, "stop: ");
        if (locationManager != null) {
            locationManager.removeUpdates(listener);
        }
    }

}