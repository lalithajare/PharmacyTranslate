package com.translator.app.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.translator.app.R
import com.translator.app.adapters.MedicineAdapter
import com.translator.app.models.Medicine
import com.translator.app.utils.MyApplication

class MedicineListActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = MedicineListActivity::class.java.simpleName

    override fun onClick(v: View?) {

    }

    companion object {
        fun beginActivity(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, MedicineListActivity::class.java))
        }
    }

    private lateinit var rvMedicine: RecyclerView
    private lateinit var mAdapter: MedicineAdapter
    private val medicineList = mutableListOf<Medicine>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_list)
        initViews()
        setAdapter()
        loadMedicinesFromDB()
    }

    private fun initViews() {
        rvMedicine = findViewById(R.id.rv_medicine)
    }

    private fun setAdapter() {
        mAdapter = MedicineAdapter(medicineList) { medicine ->
            Log.d(TAG, "Medicine clicked : ${medicine.medicineName}")
        }
        rvMedicine.adapter = mAdapter
        rvMedicine.layoutManager = LinearLayoutManager(this)
        rvMedicine.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
    }


    private fun loadMedicinesFromDB() {
        //Get all medicines from database
        medicineList.addAll(MyApplication.getPharmacyDB().medicineDao().getMedicines())
        mAdapter.notifyDataSetChanged()
    }
}
