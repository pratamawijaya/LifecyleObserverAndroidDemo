package com.pratamawijaya.androidlifecycleownerobserver.livedata;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.util.Log;

/**
 * Created by pratama on 8/14/17.
 */

public class LocationLiveData extends LiveData<Location> {

    private static final String TAG = LocationLiveData.class.getSimpleName();
    private LocationManager locationManager;
    private SimpleListener listener;
    private static LocationLiveData sLocationLiveData;

    @MainThread
    public static LocationLiveData get(Context context) {
        if (sLocationLiveData == null) {
            sLocationLiveData = new LocationLiveData(context);
        }
        return sLocationLiveData;
    }

    private LocationLiveData(Context context) {
        listener = new SimpleListener();
        locationManager = (LocationManager) context.getSystemService(
                Context.LOCATION_SERVICE);
    }

    @Override
    protected void onActive() {
        super.onActive();
        Log.d(TAG, "onActive: ");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.d(TAG, "onInactive: ");
        locationManager.removeUpdates(listener);
    }

    public class SimpleListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged: " + location.getLatitude());
            setValue(location);
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
}
