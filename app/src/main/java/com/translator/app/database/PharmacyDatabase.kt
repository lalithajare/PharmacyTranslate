package com.translator.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.translator.app.models.Medicine

@Database(entities = arrayOf(Medicine::class), version = 1, exportSchema = false)
abstract class PharmacyDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDAO

    companion object {
        private var INSTANCE: PharmacyDatabase? = null

        fun getDatabase(context: Context): PharmacyDatabase? {
            if (INSTANCE == null) {
                synchronized(PharmacyDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        PharmacyDatabase::class.java, "pharma.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}