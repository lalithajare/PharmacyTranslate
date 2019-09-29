package com.translator.app.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import com.translator.app.R
import com.translator.app.models.Medicine

class MedicineDetailsActivity : AppCompatActivity() {

    companion object {
        val MEDICINE_OBJ = "medicine_obj"

        fun beginActivity(activity: AppCompatActivity, medicine: Medicine) {
            val intent = Intent(activity, MedicineDetailsActivity::class.java)
            intent.putExtra(MEDICINE_OBJ, medicine)
            activity.startActivity(intent)
        }
    }

    private lateinit var mMedicine: Medicine
    private lateinit var rgLangTabs: RadioGroup
    private lateinit var txtMedicineName: TextView
    private lateinit var txtMedicineDescription: TextView
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_details)
        mMedicine = intent.getSerializableExtra(MEDICINE_OBJ) as Medicine
        initViews()
        setViews()
    }

    private fun initViews() {
        rgLangTabs = findViewById(R.id.rg_lang_tabs)
        txtMedicineName = findViewById(R.id.txt_medicine_name)
        txtMedicineDescription = findViewById(R.id.txt_medicine_description)
        btnEdit = findViewById(R.id.btn_edit)
        btnDelete = findViewById(R.id.btn_delete)
    }

    private fun setViews() {
        when (mMedicine.medicineLangCode) {
            TranslateActivity.LangCode.ENGLISH.code -> {
                rgLangTabs.check(R.id.rb_english)
            }
            TranslateActivity.LangCode.VIETNAMESE.code -> {
                rgLangTabs.check(R.id.rb_vietnamese)
            }
            TranslateActivity.LangCode.KOREAN.code -> {
                rgLangTabs.check(R.id.rb_korean)
            }
        }
        txtMedicineName.text = mMedicine.medicineName
        txtMedicineDescription.text = mMedicine.medicineDescription

        btnEdit.setOnClickListener {

        }

        btnDelete.setOnClickListener {

        }

    }
}
