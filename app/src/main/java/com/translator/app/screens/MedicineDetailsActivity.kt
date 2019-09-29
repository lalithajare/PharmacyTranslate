package com.translator.app.screens

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.translator.app.R
import com.translator.app.models.Medicine
import com.translator.app.utils.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MedicineDetailsActivity : AppCompatActivity() {

    companion object {
        val MEDICINE_ID = "medicine_id"
        val REQ_MED_DETAILS = 4243

        fun beginActivity(activity: AppCompatActivity, medicineId: String) {
            val intent = Intent(activity, MedicineDetailsActivity::class.java)
            intent.putExtra(MEDICINE_ID, medicineId)
            activity.startActivity(intent)
        }

        fun beginActivityForResult(activity: AppCompatActivity, medicineId: Long) {
            val intent = Intent(activity, MedicineDetailsActivity::class.java)
            intent.putExtra(MEDICINE_ID, medicineId)
            activity.startActivityForResult(intent, REQ_MED_DETAILS)
        }
    }

    private var mMedicine: Medicine? = null
    private lateinit var rgLangTabs: RadioGroup
    private lateinit var txtMedicineName: TextView
    private lateinit var txtMedicineDescription: TextView
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button

    private var medicineId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_details)
        medicineId = intent.getLongExtra(MEDICINE_ID, 0L)
        initViews()
        setViews()
    }

    override fun onResume() {
        super.onResume()
        getMedicineDetails(medicineId)
    }

    private fun getMedicineDetails(medicineId: Long) {
        GlobalScope.launch(Dispatchers.Main) {
            mMedicine = MyApplication.getPharmacyDB().medicineDao().getMedicine(medicineId)
            if (mMedicine != null) {
                setData()
            }
        }
    }

    private fun initViews() {
        rgLangTabs = findViewById(R.id.rg_lang_tabs)
        txtMedicineName = findViewById(R.id.txt_medicine_name)
        txtMedicineDescription = findViewById(R.id.txt_medicine_description)
        btnEdit = findViewById(R.id.btn_edit)
        btnDelete = findViewById(R.id.btn_delete)
    }

    private fun setViews() {
        btnEdit.setOnClickListener {
            EditMedicineActivity.beginActivityForResult(this, mMedicine!!)
        }
        btnDelete.setOnClickListener {
            showDeleteDialogue()
        }
    }

    private fun showDeleteDialogue() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.are_you_sure_you_want_to_delete)
            .setPositiveButton(R.string.yes) { dialog, id ->
                //Delete
                GlobalScope.launch(Dispatchers.Main) {
                    val rowsDeleted =
                        MyApplication.getPharmacyDB().medicineDao().deleteMedicine(mMedicine!!)
                    if (rowsDeleted != 0) {
                        dialog.dismiss()
                        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }
            }
            .setNegativeButton(R.string.no,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
        // Create the AlertDialog object and return it
        builder.create().show()
    }

    private fun setData() {
        when (mMedicine?.medicineLangCode) {
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
        txtMedicineName.text = mMedicine?.medicineName
        txtMedicineDescription.text = mMedicine?.medicineDescription
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK)
        }
    }


}