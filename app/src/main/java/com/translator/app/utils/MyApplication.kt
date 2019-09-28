package com.translator.app.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.translator.app.database.PharmacyDatabase

class MyApplication : Application() {

    lateinit var mSharedPreferences: SharedPreferences

    companion object {
        private lateinit var instance: MyApplication
        private lateinit var pharmaDB: PharmacyDatabase

        fun getApplicationInstance(): MyApplication {
            return instance
        }

        fun getPharmacyDB(): PharmacyDatabase {
            return pharmaDB
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        mSharedPreferences = getSharedPreferences("TRANSLATOR", Context.MODE_PRIVATE)
        pharmaDB = PharmacyDatabase.getDatabase(this)!!
    }


}