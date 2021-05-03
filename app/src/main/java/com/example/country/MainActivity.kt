package com.example.country

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.*
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.country.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    private var hasGps: Boolean = false
    private var hasNetwork: Boolean =false
    private lateinit var locationManager: LocationManager
    private  var networkLocation: Location?=null
    private  var gpsLocation: Location?=null
    private  var lat: Double?=null
    private var long: Double?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Country)

        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions,0)



        val binding=DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        val toolbar: Toolbar=binding.toolbar
        val mainViewModel= ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.lifecycleOwner=this
        binding.mainViewModel=mainViewModel
        setSupportActionBar(toolbar)
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val city=getLocation()
        Log.i("hello","lat $lat long$long")
        mainViewModel.getWeather(lat!!,long!!)
    }
    @SuppressLint("MissingPermission")
    private fun getLocation(): String? {
        Log.i("hello","getLocation called")
        locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if(hasGps || hasNetwork){
            if(hasGps){
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 0F, object : LocationListener {
                        override fun onLocationChanged(location: Location) {

                            if (location != null) {
                                gpsLocation = location
                            }
                        }
                    })
                val localGbsLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if(localGbsLocation!=null){
                    gpsLocation=localGbsLocation
                }
            }
            if(hasNetwork){
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 5000, 0F, object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            if (location != null) {
                                networkLocation = location
                            }
                        }
                    })
                val localNetworkLocation=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if(localNetworkLocation!=null){
                    gpsLocation=localNetworkLocation
                }
            }
            if(gpsLocation != null ) {
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses =
                    geocoder.getFromLocation(gpsLocation!!.latitude, gpsLocation!!.longitude, 1)
                val cityName = addresses[0].getAddressLine(0)
                val stateName = addresses[0].getAddressLine(1)
                val countryName = addresses[0].getAddressLine(2)
                lat=gpsLocation!!.latitude
                long=gpsLocation!!.longitude
                return cityName
            }
            if(networkLocation != null){
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(networkLocation!!.latitude, networkLocation!!.longitude, 1)
                    val cityName = addresses[0].getAddressLine(0)
                    val stateName = addresses[0].getAddressLine(1)
                    val countryName = addresses[0].getAddressLine(2)
                    lat=networkLocation!!.latitude
                    long=networkLocation!!.longitude
                    return cityName
            }
        }else{
            Log.i("hello","no permission")
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        return ""
    }
}