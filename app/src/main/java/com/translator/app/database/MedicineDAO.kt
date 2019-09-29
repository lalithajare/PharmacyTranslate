package com.translator.app.database

import androidx.room.*
import com.translator.app.models.Medicine

@Dao
interface MedicineDAO {

    @Insert
    suspend fun insertMedicine(medicine: Medicine): Long

    @Delete
    suspend fun deleteMedicine(medicine: Medicine): Int

    @Update
    suspend fun updateMedicine(medicine: Medicine): Int

    @Query("SELECT * FROM medicines")
    suspend fun getMedicineList(): List<Medicine>

    @Query("SELECT * FROM medicines WHERE medicine_id = :id")
    suspend fun getMedicine(id: Long): Medicine

}