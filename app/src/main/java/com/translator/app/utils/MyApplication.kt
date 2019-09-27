package com.translator.app.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class MyApplication : Application() {

    lateinit var mSharedPreferences: SharedPreferences

    companion object {
        private lateinit var instance: MyApplication
        fun getApplicationInstance(): MyApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        mSharedPreferences = getSharedPreferences("TRANSLATOR", Context.MODE_PRIVATE)
    }


}