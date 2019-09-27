package com.translator.app.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.translator.app.models.User

object Prefs {

    private val USER_OBJ = "user_obj"
    private val prefs = MyApplication.getApplicationInstance().mSharedPreferences
    private val gson = Gson()


    var user: User?
        get() {
            return gson.fromJson(prefs.getString(USER_OBJ, ""), User::class.java)
        }
        set(value) {
            prefs.edit()?.putString(USER_OBJ, gson.toJson(value))?.apply()
        }

}

