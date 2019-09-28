package com.translator.app.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.translator.app.R
import com.translator.app.models.User
import com.translator.app.utils.Prefs
import com.translator.app.utils.Utils

class ProfileActivity : AppCompatActivity() {

    companion object {
        fun beginActivity(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, ProfileActivity::class.java))
        }
    }

    private lateinit var edtName: EditText
    private lateinit var edtHeight: EditText
    private lateinit var edtWeight: EditText
    private lateinit var edtMedConds: EditText
    private lateinit var btnSave: Button

    private val user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews()
        btnSave.setOnClickListener {
            if (validInput()) {
                setValues()
                Prefs.user = user
                MainActivity.beginActivity(this)
                finish()
            }
        }
    }

    private fun setValues() {
        user.name = edtName.text.toString().trim()
        user.height = edtHeight.text.toString().trim().toDouble()
        user.weight = edtWeight.text.toString().trim().toDouble()
        user.medicalConditions = edtMedConds.text.toString().trim()
    }

    private fun validInput(): Boolean {
        if (!TextUtils.isEmpty(edtName.text.toString().trim()))
            if (!TextUtils.isEmpty(edtHeight.text.toString().trim()))
                if (!TextUtils.isEmpty(edtWeight.text.toString().trim()))
                    if (!TextUtils.isEmpty(edtMedConds.text.toString().trim()))
                        return true
                    else
                        Utils.showToast(getString(R.string.plz_enter_med_conds))
                else
                    Utils.showToast(getString(R.string.plz_enter_weight))
            else
                Utils.showToast(getString(R.string.plz_enter_height))
        else
            Utils.showToast(getString(R.string.plz_enter_name))
        return false
    }

    private fun initViews() {
        edtName = findViewById(R.id.edt_name)
        edtHeight = findViewById(R.id.edt_height)
        edtWeight = findViewById(R.id.edt_weight)
        edtMedConds = findViewById(R.id.edt_med_conds)
        btnSave = findViewById(R.id.btn_save)
    }
}
