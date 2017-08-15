package com.pratamawijaya.androidlifecycleownerobserver

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.location.Location
import android.os.Bundle
import android.util.Log

import com.pratamawijaya.androidlifecycleownerobserver.livedata.LocationLiveData

class MainLiveDataActivity : LifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_live_data)

        LocationLiveData.get(this).observe(this,
                Observer<Location> {
                    location ->
                    Log.d(TAG, "onChanged: " + location?.latitude)
                })
    }

    companion object {
        private val TAG = MainLifecyleActivity::class.java.simpleName
    }
}
