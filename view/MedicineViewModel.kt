package com.example.mreminder3.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mreminder3.data.Medicine
import com.example.mreminder3.data.MedicineDatabase
import com.example.mreminder3.data.MedicineRepository
import kotlinx.coroutines.launch

class MedicineViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MedicineRepository
    val allMedicines: LiveData<List<Medicine>>

    init {
        val medicineDao = MedicineDatabase.getDatabase(application).medicineDao()
        repository = MedicineRepository(medicineDao)
        allMedicines = repository.allMedicines
    }

    fun insert(medicine: Medicine) = viewModelScope.launch {
        repository.insert(medicine)
    }

    fun delete(medicine: Medicine) = viewModelScope.launch {
        repository.delete(medicine)
    }
}

