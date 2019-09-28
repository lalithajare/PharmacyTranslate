package com.translator.app.screens

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.translator.app.R
import com.translator.app.models.Medicine
import com.translator.app.utils.MyApplication

class AddMedicineActivity : AppCompatActivity() {

    private val TAG = AddMedicineActivity::class.java.simpleName

    companion object {
        val MEDICINE_OBJ = "medicine_obj"

        fun beginActivity(activity: AppCompatActivity, medicine: Medicine) {
            val intent = Intent(activity, AddMedicineActivity::class.java)
            intent.putExtra(MEDICINE_OBJ, medicine)
            activity.startActivity(intent)
        }
    }

    private lateinit var edtMedicineName: EditText
    private lateinit var edtMedicineDescription: EditText
    private lateinit var btnSave: Button
    private lateinit var medicine: Medicine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine)
        medicine = intent.getSerializableExtra(MEDICINE_OBJ)!! as Medicine
        initViews()
        setViews()
    }

    private fun initViews() {
        edtMedicineName = findViewById(R.id.edt_medicine_name)
        edtMedicineDescription = findViewById(R.id.edt_medicine_description)
        btnSave = findViewById(R.id.btn_save)
    }

    private fun setViews() {
        if (!medicine.medicineName.isBlank())
            edtMedicineName.setText(medicine.medicineName)

        btnSave.setOnClickListener {
            //Save Medicine to Local Database
            medicine.medicineDate = System.currentTimeMillis()
            medicine.medicineDescription = edtMedicineDescription.text.toString().trim()

            Log.d(
                TAG,
                "Medicine model inserted in DB -- \n Medicine Name : ${medicine.medicineName}" +
                        ", Medicine Date : ${medicine.medicineDate}, Medicine Description : ${medicine.medicineDescription}," +
                        " Medicine Language Code : ${medicine.medicineLangCode}"
            )

            InsertTask(medicine, this).execute()
        }
    }

    private class InsertTask(
        var medicine: Medicine,
        var addMedicineActivity: AddMedicineActivity
    ) : AsyncTask<Void, Void, Void?>() {
        override fun doInBackground(vararg params: Void?): Void? {
            MyApplication.getPharmacyDB().medicineDao().insertMedicine(medicine)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            MedicineListActivity.beginActivity(addMedicineActivity)
        }
    }
}
