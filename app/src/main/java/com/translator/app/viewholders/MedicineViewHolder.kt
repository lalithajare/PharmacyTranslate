package com.translator.app.viewholders

import android.view.View
import android.widget.TextView
import com.translator.app.R
import com.translator.app.models.Medicine
import com.translator.app.screens.TranslateActivity

class MedicineViewHolder(var mView: View, var callBack: (Medicine) -> Unit) : ViewHolder(mView) {

    private var mMedicine: Medicine? = null

    private val txtMedicineName: TextView = mView.findViewById(R.id.txt_medicine_name)
    private val txtTranslated: TextView = mView.findViewById(R.id.txt_translated)

    override fun onBindView(model: Any) {
        mMedicine = model as Medicine?

        if (!mMedicine?.medicineName.isNullOrEmpty())
            txtMedicineName.text = mMedicine?.medicineName

        if (mMedicine?.medicineLangCode !== TranslateActivity.LangCode.ENGLISH.code) {
            txtTranslated.visibility = View.VISIBLE
        }

        mView.setOnClickListener {
            callBack(mMedicine!!)
        }
    }
}