package com.example.mreminder3.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MedicineDao {

    @Insert
    suspend fun insert(medicine: Medicine)

    @Delete
    suspend fun delete(medicine: Medicine)

    @Query("SELECT * FROM medicine_table ORDER BY time ASC")
    fun getAllMedicines(): LiveData<List<Medicine>>
}
