package com.translator.app.utils

import android.widget.Toast

object Utils {
    fun showToast(msg: String) {
        Toast.makeText(MyApplication.getApplicationInstance(), msg, Toast.LENGTH_SHORT).show()
    }
}