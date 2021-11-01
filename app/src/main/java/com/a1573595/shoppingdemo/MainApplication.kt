package com.a1573595.shoppingdemo

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class MainApplication : Application() {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}