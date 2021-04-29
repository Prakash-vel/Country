package com.example.country.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.country.databasehander.CountryDatabaseDao

class DetailViewModelFactory(
    val dataSource: CountryDatabaseDao,
    val selectedId: Long,
    val application: Application
):ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel( dataSource,selectedId,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}