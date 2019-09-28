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
    private lateinit var linMyMedicines: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setViews()
    }

    private fun initViews() {
        linTranslate = findViewById(R.id.lin_translate)
        linMyMedicines = findViewById(R.id.lin_my_medicines)
    }


    private fun setViews() {
        linTranslate.setOnClickListener {
            TranslateActivity.beginActivity(this)
        }
        linMyMedicines.setOnClickListener {
            MedicineListActivity.beginActivity(this)
        }
    }

}
