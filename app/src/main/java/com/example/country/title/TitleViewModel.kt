package com.example.country.title

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.country.databasehander.CountryData
import com.example.country.databasehander.CountryDatabaseDao
import com.example.country.datahandler.CountryApiObj
import com.example.country.datahandler.CountryProperty
import kotlinx.coroutines.*

class TitleViewModel(private val dataSource: CountryDatabaseDao) :ViewModel() {

    private val viewModelJob= Job()
    private val coroutineScope= CoroutineScope(Dispatchers.Main+viewModelJob)
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _apiResult=MutableLiveData<List<CountryProperty>>()
    val apiResult:LiveData<List<CountryProperty>>
       get()=_apiResult

    private val _dbResult=MutableLiveData<List<CountryData>>()
    val dbResult:LiveData<List<CountryData>>
        get()=_dbResult

    init{
        getProperties(null)
        Log.i("hello","init called")
    }

    fun insert(){
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                Log.i("hello ","insert called")
                _apiResult.value?.forEach { i ->
                    val countryData= CountryData()
                    countryData.capital=i.capital
                    countryData.flag=i.flag
                    countryData.name=i.name
                    countryData.population=i.population
                    countryData.region=i.region
                    dataSource.insert(countryData)

                }
            }
            getProperties(null)

        }
    }
//    fun get(){
//        coroutineScope.launch {
//            withContext(Dispatchers.IO){
//                _dbResult.value=dataSource.get()
//            }
//            if(_dbResult.value?.size == 0){
//                getProperties()
//            }
//        }
//    }
//

    fun getProperties(key: String?){

        Log.i("hello ","getproperties called")
        coroutineScope.launch {
            if(key != null){
                val string="%${key}%"
                _dbResult.value=dataSource.getCountries(string)
            }else{
                _dbResult.value=dataSource.get()
            }



            Log.i("hello ","db result${dbResult.value}")
            if(_dbResult.value?.size == 0 && key == null){
                  val property = CountryApiObj.retrofitService.getProperties()
                try {
                    val listResult = property.await()
                    if (listResult.size > 1) {

                        _apiResult.value=listResult
                        Log.i("hello","result $listResult")
                        insert()
                    }
                }catch (e: Exception){
                    Log.i("hello","error $e")
                }
            }

        }
    }
    private val _selectedCountry=MutableLiveData<CountryData>()
    val selectedCountry: LiveData<CountryData>
      get()=_selectedCountry

    fun showDetailView(it: CountryData) {
        _selectedCountry.value=it
    }

    fun showDetailViewComplete() {
        _selectedCountry.value=null
    }
}