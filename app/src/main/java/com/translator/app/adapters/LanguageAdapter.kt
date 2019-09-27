package com.translator.app.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.R
import android.widget.BaseAdapter
import android.widget.TextView
import com.translator.app.models.Language


class LanguageAdapter(var mContext: Context, var list: List<Language>) : BaseAdapter() {
    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = View.inflate(mContext, R.layout.simple_list_item_1, null) as TextView
        textView.text = list[position].language
        return textView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = View.inflate(mContext, R.layout.simple_list_item_1, null) as TextView
        textView.text = list[position].language
        return textView
    }

}