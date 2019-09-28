package com.translator.app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.translator.app.models.Medicine

@Dao
interface MedicineDAO {

    @Insert
    fun insertMedicine(medicine: Medicine): Long

    @Update
    fun updateMedicine(medicine: Medicine): Int

    @Query("SELECT * FROM medicines")
    fun getMedicines(): List<Medicine>

    @Query("SELECT * FROM medicines WHERE medicine_id = :id")
    fun getMedicine(id: Long): Medicine

}