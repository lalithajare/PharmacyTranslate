package com.translator.app.screens

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.translator.app.R

class MainActivity : AppCompatActivity() {

    companion object {
        fun beginActivity(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    private lateinit var linTranslate: LinearLayout
    private lateinit var linMyMedicines: LinearLayout
    private lateinit var relProfile: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setViews()
        loadUserImage()
    }

    private fun initViews() {
        linTranslate = findViewById(R.id.lin_translate)
        linMyMedicines = findViewById(R.id.lin_my_medicines)
        relProfile = findViewById(R.id.rel_profile)
    }


    private fun setViews() {
        linTranslate.setOnClickListener {
            TranslateActivity.beginActivity(this)
        }
        linMyMedicines.setOnClickListener {
            MedicineListActivity.beginActivity(this)
        }
        relProfile.setOnClickListener {
            EditProfileActivity.beginActivityForResult(this)
        }
    }


    private fun loadUserImage() {
        //Load image from memory
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EditProfileActivity.REQ_EDIT_PROFILE && resultCode == Activity.RESULT_OK) {
            loadUserImage()
        }
    }

}
