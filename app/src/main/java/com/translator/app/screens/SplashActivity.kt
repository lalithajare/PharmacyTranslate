package com.translator.app.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.translator.app.R
import com.translator.app.utils.Prefs

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if (Prefs.user == null) {
                ProfileActivity.beginActivity(this)
            } else {
                MainActivity.beginActivity(this)
            }
        }, 3000)

    }
}
