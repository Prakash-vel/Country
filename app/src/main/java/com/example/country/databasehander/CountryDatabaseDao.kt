package com.example.country.databasehander

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CountryDatabaseDao {
    @Query("SELECT * FROM CountryData")
    suspend fun get():List<CountryData>

    @Query("SELECT * FROM CountryData WHERE name LIKE :hint GROUP BY name ORDER BY CountryId")
    suspend fun getCountries(hint: String):List<CountryData>?

    @Insert
    fun insert(data: CountryData)

    @Query("SELECT * FROM CountryData WHERE CountryId = :key")
    suspend fun getById(key: Long):CountryData
}