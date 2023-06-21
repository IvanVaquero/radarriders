package com.gimbernat.radarriders.ui.scenes.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gimbernat.radarriders.AppRoutes
import com.gimbernat.radarriders.datasources.RadarDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.models.Radar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class MapSceneViewModel(
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource,
    private val radarDataSource: RadarDataSource,
    private val context: Context,

) : ViewModel() {
    // Estado mutable que tiene el valor de la querry
    private val _searchQuery = mutableStateOf("")

    // Ensenar search query en estado immutable
    val searchQuery: State<String> = _searchQuery
    private var fetchedRadars = mutableListOf<Radar>()
    private val selectedMarkerRadar = mutableStateOf<Radar?>(null)



    private fun initMapObjects(){
        radarDataSource.getAll {
            fetchedRadars
        }
    }

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun navigateToMain() {
        viewModelScope.launch {
            navController.navigate(AppRoutes.EDITUSER.value) {
                popUpTo(AppRoutes.MAP.value) {
                    inclusive = true
                }
            }
        }
    }

    fun editUser() {
        viewModelScope.launch {
            navController.navigate(AppRoutes.EDITUSER.value) {
                popUpTo(AppRoutes.MAP.value) {
                    inclusive = true
                }
            }
        }
    }


    fun setupMap(mapView: MapView) {

        this.initMapObjects()
        mapView.getMapAsync { googleMap ->
            val yellowMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
            val blueMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)

            // Enable zoom controls
            googleMap.uiSettings.isZoomControlsEnabled = true

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request location permissions from the user
                val permission = Manifest.permission.ACCESS_FINE_LOCATION
                val requestCode = 1
                val activity = context as? Activity
                activity?.let {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(permission),
                        requestCode
                    )
                }
            } else {
                googleMap.isMyLocationEnabled = true
                googleMap.setOnMyLocationButtonClickListener{
                    getCurrentLocation(context) { location ->
                        // Handle the received location
                        location?.let {
                            // Perform actions with the obtained location
                            // For example, update the map with the new location
                            updateLocation(location, googleMap)
                        }
                    }
                    true // Return true to indicate that the event has been handled
                }
            }
            val markers: MutableList<Marker> = mutableListOf()
            for (radar in fetchedRadars) {
                val markerOptions =
                    MarkerOptions().position(
                        LatLng(radar.latitude, radar.longitude)).title(radar.radarName)
                val marker = googleMap.addMarker(markerOptions)
                marker?.let {
                    markers.add(it)
                }
            }

            val marker1 = MarkerOptions()
                .position(LatLng(41.3879, 2.1699))
                .title("Alert 1")
                .icon(yellowMarker)

            val marker2 = MarkerOptions()
                .position(LatLng(41.3926, 2.1461))
                .title("Alert 2")
                .icon(yellowMarker)

            val marker3 = MarkerOptions()
                .position(LatLng(41.3747, 2.1403))
                .title("Radar 1")
                .icon(blueMarker)

            val marker4 = MarkerOptions()
                .position(LatLng(41.3964, 2.1677))
                .title("Radar 2")
                .icon(blueMarker)

            val marker5 = MarkerOptions()
                .position(LatLng(41.3864, 2.1688))
                .title("Radar 3")
                .icon(blueMarker)

            googleMap.addMarker(marker1)
            googleMap.addMarker(marker2)
            googleMap.addMarker(marker3)
            googleMap.addMarker(marker4)
            googleMap.addMarker(marker5)


            googleMap.setOnMarkerClickListener { marker ->
                val selectedLocation = fetchedRadars.find { it.radarName == marker.title }
                selectedMarkerRadar.value = selectedLocation

                // Perform actions with the selected location, such as navigation or showing more information
                // For example:
                marker.showInfoWindow()

                true // Return true to indicate that you've handled the marker click event
            }
            googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
                override fun getInfoWindow(marker: Marker): View? {
                    // Return null to use the default InfoWindow
                    return null
                }

                override fun getInfoContents(marker: Marker): View? {
                   return null
                }
            })

        }
    }
    fun moveCamera(mapView: MapView, selectedLocation: MutableState<Radar?>) {
        mapView.getMapAsync { googleMap ->
            if (fetchedRadars.isNotEmpty() && selectedLocation.value != null) {
                val zoomMap = 17f
                val locationLatLng = LatLng(selectedLocation.value!!.latitude, selectedLocation.value!!.longitude)
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(locationLatLng, zoomMap)
                )
            }
        }
    }


    private fun getCurrentLocation(context: Context, onLocationReceived: (Location?) -> Unit) {
        val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        // Check location permissions
        val fineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        val coarseLocationPermission = Manifest.permission.ACCESS_COARSE_LOCATION
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(context, fineLocationPermission) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(context, coarseLocationPermission) == PackageManager.PERMISSION_GRANTED

        if (hasFineLocationPermission && hasCoarseLocationPermission) {
            // Create location request
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 5000 // 5 seconds
                fastestInterval = 2000 // 2 seconds
            }
            // Create location callback
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation.let { actualLocation ->
                        onLocationReceived(actualLocation)
                        Log.d("CategoryDetailSceneViewModel", "Received location: $actualLocation")
                        fusedLocationClient.removeLocationUpdates(this) // Detener las actualizaciones de ubicación

                    }
                }
            }
            // Request location updates
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
            // fusedLocationClient.removeLocationUpdates(locationCallback)
        } else {
            // Request location permissions from the user
            val permission = Manifest.permission.ACCESS_FINE_LOCATION
            val requestCode = 1
            val activity = context as? Activity
            activity?.let {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun updateLocation(actualLocation: Location, googleMap: GoogleMap?) {
        val currentLatLng = LatLng(actualLocation.latitude, actualLocation.longitude)
        val zoomMap = 17f

        // Move the camera to the current location
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoomMap ))

        // Create marker options for current location
        val markerOptions = MarkerOptions()
            .position(currentLatLng)
            .title("Mi ubicación")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        googleMap?.addMarker(markerOptions)

    }

    @Composable
    fun rememberMapViewWithLifecycle(): MapView {
        val context = LocalContext.current
        val mapView = remember {
            MapView(context).apply {
                onCreate(Bundle())
            }
        }
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(lifecycleOwner) {
            val lifecycleObserver = object : DefaultLifecycleObserver {
                override fun onResume(owner: LifecycleOwner) {
                    mapView.onResume()
                }
                override fun onPause(owner: LifecycleOwner) {
                    mapView.onPause()
                }
                override fun onDestroy(owner: LifecycleOwner) {
                    mapView.onDestroy()
                }
            }
            lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            }
        }
        return mapView
    }

    fun signOut() {
        viewModelScope.launch {
            sessionDataSource.signOutUser()
            navController.navigate(AppRoutes.WELCOME.value) {
                popUpTo(AppRoutes.MAP.value) {
                    inclusive = true
                }
            }
        }
    }

    fun addRadar() {
            viewModelScope.launch {
                navController.navigate(AppRoutes.NEWRADAR.value) {
                    popUpTo(AppRoutes.MAP.value) {
                        inclusive = true
                    }
                }
            }
    }

    fun createAlert() {
        viewModelScope.launch {
            navController.navigate(AppRoutes.NEWALERT.value) {
                popUpTo(AppRoutes.MAP.value) {
                    inclusive = true
                }
            }
        }    }
}