package com.translator.app.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.translator.app.R

class MainActivity : AppCompatActivity() {

    companion object {
        fun beginActivity(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    private lateinit var linTranslate: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setViews()
    }

    private fun initViews() {
        linTranslate = findViewById(R.id.lin_translate)
    }


    private fun setViews() {
        linTranslate.setOnClickListener {
            TranslateActivity.beginActivity(this)
        }
    }

}
