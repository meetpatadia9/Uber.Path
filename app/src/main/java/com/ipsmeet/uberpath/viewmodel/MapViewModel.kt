package com.ipsmeet.uberpath.viewmodel

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapViewModel : ViewModel() {

    private lateinit var mMap: GoogleMap
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private var marker: Marker? = null

    private lateinit var liveLatLng: LatLng     // user's current LatLng
    private lateinit var markerLatLng: LatLng   // marker's current LatLng

    //  CHECK APP-PERMISSIONS
    fun checkPermission(activity: Activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // system-default permission request permission dialog
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.ACCESS_FINE_LOCATION"
                ), 1
            )
            return
        }
    }

    //  COORDINATES OF USER'S CURRENT LOCATION
    fun getCoordinates(lat: Double, lng: Double) {
        latitude = lat
        longitude = lng
        Log.d("getCoordinates() ~ latitude", latitude.toString())
        Log.d("getCoordinates() ~ longitude", longitude.toString())
    }

    fun onMapReady(activity: Activity, googleMap: GoogleMap, mapFragment: Fragment) {
        mMap = googleMap
        checkPermission(activity)
        mMap.isMyLocationEnabled = true

        liveLatLng = LatLng(latitude, longitude)
        Log.i("Current LatLng", liveLatLng.toString())
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(liveLatLng, 16f))

        mMap.setOnMapClickListener {
            marker?.remove()
            marker = mMap.addMarker(MarkerOptions().position(it))
            markerLatLng = it
            Log.i("Marker LatLng", it.toString())
        }

        // gps-location button
        if (mapFragment.requireView().findViewById<View?>("1".toInt()) != null) {
            val locationButton: View = (mapFragment.requireView().findViewById<View>("1".toInt()).parent as View).findViewById("2".toInt())
            val layoutParams: RelativeLayout.LayoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
            layoutParams.apply {
                addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
                addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
                setMargins(0, 0, 0, 120)
            }
        }

        // compass button
        if (mapFragment.requireView().findViewById<View?>("2".toInt()) != null) {
            val locationButton: View = (mapFragment.requireView().findViewById<View>("2".toInt()).parent as View).findViewById("5".toInt())
            val layoutParams: RelativeLayout.LayoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
            layoutParams.apply {
                addRule(RelativeLayout.ALIGN_PARENT_START, 0)
                addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
                setMargins(0, 190, 50, 0)
            }
        }
    }

}