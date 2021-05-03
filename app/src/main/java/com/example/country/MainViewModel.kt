package com.example.country

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.country.datahandler.CountryApiObj
import com.example.country.datahandler.WeatherApi
import com.example.country.datahandler.WeatherPropertyX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val viewModelJob= Job()
    private val coroutineScope= CoroutineScope(Dispatchers.Main+viewModelJob)
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    private val _apiResult=MutableLiveData<WeatherPropertyX>()
    val apiresult:LiveData<WeatherPropertyX>
      get()=_apiResult


    fun getWeather(lat: Double,long: Double) {
        coroutineScope.launch {


            val weather=WeatherApi.retrofitService.getProperties(lat,long,"54db2f5f9abaa843d3e887adcd05907f")

            try {
                val weatherResults=weather.await()
                Log.i("hello","$weatherResults")
                if (weatherResults != null) {

                    _apiResult.value=weatherResults
                    Log.i("hello","result $weatherResults")

                }
            }catch (e: Exception){
                Log.i("hello","error $e")
            }
        }


    }
    val weatherIcon= Transformations.map(apiresult){
        "http://openweathermap.org/img/wn/${it.weather[0].icon}@2x.png"
    }
    val weatherTemp= Transformations.map(apiresult) {
//        Celsius = (Kelvin – 273.15)
        "${Math.round((it.main.temp-273.15)*10.0)/10.0}*C"
    }
    val weatherCity= Transformations.map(apiresult){
        it.name
    }
    val weatherDesc= Transformations.map(apiresult){
        it.weather[0].description
    }

}