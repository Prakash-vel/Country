package com.example.country.databasehander

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "CountryData")
data class CountryData(

    @PrimaryKey(autoGenerate = true)
    var CountryId: Long=0L,

    @ColumnInfo(name= "name")
    var name: String="",

    @ColumnInfo(name= "capital")
    var capital: String="",

    @ColumnInfo(name= "region")
    var region: String="",

    @ColumnInfo(name= "population")
    var population: Long=0L,

    @ColumnInfo(name= "flag")
    var flag: String=""
)
