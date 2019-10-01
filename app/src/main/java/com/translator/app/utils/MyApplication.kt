package com.translator.app.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.translator.app.database.PharmacyDatabase

/**
 * 1. This is the 'Application' class, the main entry point to Application.
 * 2. All the objects that are used universally in App are initialized over here.
 * 3. It initializes :- reference to 'SharedPreferences', 'PharmacyDatabase', 'MyApplication'.
 */
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