package com.example.parkingsystemandroid

import android.app.Application
import com.example.parkingsystemandroid.utils.TokenManager

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        TokenManager.init(this)
    }
}