package com.example.country.datahandler
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

import retrofit2.http.Query

//"http://api.openweathermap.org/data/2.5/weather?lat=8.937705&lon=77.4501363&appid=54db2f5f9abaa843d3e887adcd05907f"
private val baseUrl="http://api.openweathermap.org/data/2.5/"

private val moshi= Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit=Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(baseUrl)
    .build()

interface WeatherService{
    @GET("weather")
    fun getProperties(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") appid: String
    ):
            Deferred<WeatherPropertyX>
    @GET("weather")
    fun getPropertiesByCity(
        @Query("q") q: String,
        @Query("appid") appid: String
    ):
            Deferred<WeatherPropertyX>
}

object WeatherApi{
    val retrofitService: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }
}