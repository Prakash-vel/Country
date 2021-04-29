package com.example.country.title

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.country.databasehander.CountryDatabaseDao

class TitleViewModelFactory(private val dataSource: CountryDatabaseDao) :ViewModelProvider.Factory{
    @Suppress("unchecked_cast")

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TitleViewModel::class.java)) {
            return TitleViewModel( dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

}