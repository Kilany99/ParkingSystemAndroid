package com.example.parkingsystemandroid

import android.app.Application
import android.content.Context

class ParkingSystemApplication : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}
