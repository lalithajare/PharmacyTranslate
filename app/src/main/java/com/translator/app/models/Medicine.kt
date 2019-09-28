package com.translator.app.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "medicines")
class Medicine : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "medicine_id")
    var medicineId: Long = 0L

    @ColumnInfo(name = "medicine_name")
    var medicineName: String = ""

    @ColumnInfo(name = "medicine_description")
    var medicineDescription: String = ""

    @ColumnInfo(name = "medicine_date")
    var medicineDate: Long = 0L

    @ColumnInfo(name = "medicine_lang_code")
    var medicineLangCode: String = ""
}