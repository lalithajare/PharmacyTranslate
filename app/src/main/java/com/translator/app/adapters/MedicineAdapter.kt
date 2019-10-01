package com.translator.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.translator.app.R
import com.translator.app.models.Medicine
import com.translator.app.viewholders.MedicineViewHolder
import com.translator.app.viewholders.ViewHolder

/**
 * 1. This class acts as the Adapter to RecyclerView in 'MedicineListActivity'
 * 2. It does the working of initializing the View for Medicine List.
 *
 * @property callBack : The callback to 'MedicineListActivity' that the item in list is clicked.
 *                      This callback is further passed to ViewHolder.
 * @property medicineList : The actual model list of 'Medicine' fetched from Local database.
 */
class MedicineAdapter(
    private val medicineList: MutableList<Medicine>,
    private var callBack: (Medicine) -> Unit
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