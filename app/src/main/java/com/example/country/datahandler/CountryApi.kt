package com.example.country.datahandler

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private val moshi= Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


private val baseUrl="https://restcountries.eu/rest/v2/"

private val retrofit= Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(baseUrl)
        .build()


interface CountryApi {
    @GET("all")
    fun getProperties():
            Deferred<List<CountryProperty>>


}


object CountryApiObj{
    val retrofitService: CountryApi by lazy {
        retrofit.create(CountryApi::class.java)
    }
}