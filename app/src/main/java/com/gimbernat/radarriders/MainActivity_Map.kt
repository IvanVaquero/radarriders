@file:OptIn(ExperimentalAnimationApi::class)

package com.gimbernat.radarriders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import com.albertleal.gibernat.myapplication.ui.components.*
import com.google.firebase.FirebaseApp


@ExperimentalMaterial3Api
class MainActivity_Map : ComponentActivity() {
    private val APIKEY = "ToZ0AohR9tLRNihTnVlD9DT54v9sFnKb"
    private lateinit var tomTomMap: TomTomMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            MyApp()
        }

        val mapOptions = MapOptions(mapKey = APIKEY)
        mapFragment = MapFragment.newInstance(mapOptions)
        supportFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()
        mapFragment.getMapAsync { map ->
            tomTomMap = map
            enableUserLocation()
            setUpMapListeners()
        }
    }

    private fun enableUserLocation(){
        locationEngine = AndroidLocationEngine(context = this)
        locationEngine.enable()

        tomTomMap.setLocationEngine(locationEngine)
        val locationMarker = LocationMarkerOptions(type=LocationMarkerType.POINTER)
        tomTomMap.enableLocationMarker(locationMarker)
    }

    private fun setUpMapListeners(){

    }
}
