package com.translator.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.translator.app.R
import com.translator.app.models.Medicine
import com.translator.app.viewholders.MedicineViewHolder
import com.translator.app.viewholders.ViewHolder

class MedicineAdapter(
    private val medicineList: MutableList<Medicine>,
    var callBack: (Medicine) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return MedicineViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_medicine, parent, false)
            , callBack
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.onBindView(medicineList[pos])
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

}