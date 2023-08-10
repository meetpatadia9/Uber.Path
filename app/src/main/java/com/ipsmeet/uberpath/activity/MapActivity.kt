package com.ipsmeet.uberpath.activity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.ipsmeet.uberpath.R
import com.ipsmeet.uberpath.databinding.ActivityMapBinding
import com.ipsmeet.uberpath.viewmodel.MapViewModel

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding

    private lateinit var mapView: Fragment
    private lateinit var viewModel: MapViewModel
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    lateinit var locationManager: LocationManager
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            statusBarColor = ContextCompat.getColor(this@MapActivity, R.color.blue)
            WindowCompat.getInsetsController(window, decorView).isAppearanceLightStatusBars = false
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        mapView = supportFragmentManager.findFragmentById(R.id.map)!!

        viewModel = ViewModelProvider(this)[MapViewModel::class.java]

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this@MapActivity)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0F, locationListener)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            viewModel.onMapReady(this, googleMap, mapView)
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            longitude = location.longitude
            latitude = location.latitude

            val runnable = object : Runnable {
                override fun run() {
                    viewModel.getCoordinates(latitude, longitude)
                    handler.postDelayed(this, 1000)
                    Log.d("Current Location", " $longitude + $latitude")
                }
            }
            handler.post(runnable)
            locationManager.removeUpdates(this)

            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this@MapActivity)
        }

        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onResume() {
        super.onResume()
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0F, locationListener)
        }
    }

}