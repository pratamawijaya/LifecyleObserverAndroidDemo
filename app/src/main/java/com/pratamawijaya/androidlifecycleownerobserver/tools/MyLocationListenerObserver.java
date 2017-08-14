package com.pratamawijaya.androidlifecycleownerobserver.tools;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by pratama on 8/14/17.
 */

public class MyLocationListenerObserver implements LifecycleObserver {

    private static final String TAG = MyLocationListenerObserver.class.getSimpleName();
    private static final long MIN_TIME_UPDATE = 0;
    private static final float MIN_DISTANCE_UPDATE = 0;

    private Context context;
    private LifecycleOwner lifecycle;
    private LocationManager locationManager;
    private final LocationListener mListener;

    public MyLocationListenerObserver(Context context, LifecycleOwner lifecycle, LocationListener listener) {
        this.context = context;
        this.lifecycle = lifecycle;
        this.mListener = listener;
        // observe to lifecycle
        this.lifecycle.getLifecycle().addObserver(this);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void start() {
        Log.d(TAG, "start: ");

        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATE, MIN_DISTANCE_UPDATE, mListener);
        // Force an update with the last location, if available.
        Location lastLocation = locationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER);
        if (lastLocation != null) {
            mListener.onLocationChanged(lastLocation);
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void stop() {
        Log.d(TAG, "stop: ");
        if (locationManager != null) {
            locationManager.removeUpdates(mListener);
            locationManager = null;
        }
    }


}
