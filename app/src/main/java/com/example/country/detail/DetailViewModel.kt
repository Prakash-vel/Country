package com.example.country.detail

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.country.R

import com.example.country.databasehander.CountryData
import com.example.country.databasehander.CountryDatabaseDao
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel(
    private val dataSource: CountryDatabaseDao,
    private val selectedId: Long,
    private val application: Application
) :ViewModel() {

    private val viewModelJob= Job()
    private val coroutineScope= CoroutineScope(Dispatchers.Main+viewModelJob)
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _countryData=MutableLiveData<CountryData>()
    val countryData: LiveData<CountryData>
      get() = _countryData

    init{
        getById()
    }
    fun getById(){
        coroutineScope.launch {

            _countryData.value=dataSource.getById(selectedId)
            Log.i("hello","selected ${countryData.value}")
        }
    }
    fun showPopulation():String{
       return  countryData.value?.population.toString()
    }

    val countryName=Transformations.map(countryData){
        application.applicationContext.getString(R.string.name_format,it.name)
    }
    val countryCapital=Transformations.map(countryData){
        application.applicationContext.getString(R.string.capital_format,it.capital)
    }
    val countryRegion=Transformations.map(countryData){
        application.applicationContext.getString(R.string.region_format,it.region)
    }
    val countryPopulation=Transformations.map(countryData){
        application.applicationContext.getString(R.string.population_format,it.population)
    }


}