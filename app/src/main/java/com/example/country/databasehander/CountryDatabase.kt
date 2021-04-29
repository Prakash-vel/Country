package com.example.country.databasehander

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CountryData::class], version = 1, exportSchema = false)
abstract class CountryDatabase : RoomDatabase() {
    abstract val countryDatabaseDao: CountryDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: CountryDatabase? = null
        fun getInstance(context: Context): CountryDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CountryDatabase::class.java,
                        "country_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
