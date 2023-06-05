package com.gimbernat.radarriders.ui.scenes.map


import android.annotation.SuppressLint
import android.os.Bundle

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.gimbernat.radarriders.R
import com.gimbernat.radarriders.datasources.SessionDataSource
//import com.gimbernat.radarriders.ui.scenes.login.LoginSceneFactory
import com.gimbernat.radarriders.ui.scenes.welcome.WelcomeSceneFactory
//import com.gimbernat.radarriders.ui.theme.MyApplicationTheme
import com.gimbernat.radarriders.ui.theme.RadarRidersTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScene(viewModel: MapSceneViewModel) {
    //Loads
//    viewModel.fetch()

    //The Scaffold composable is used to create the top-level structure of the application.
    //It includes a TopAppBar with the application name as the title.
    //It includes also a button to sign out

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = {
                    /*IconButton(onClick = { viewModel.signOut() }) {
                        Box(
                            Modifier
                                .size(37.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Green) // Fondo verde
                        ) {
                            Icon(
                                Icons.Filled.ExitToApp,
                                contentDescription = null,
                                tint = Color.White, // Ícono en color blanco
                                modifier = Modifier
                                    .align(Alignment.Center) // Centrar el ícono dentro del botón
                                    .padding(4.dp)
                            )
                        }
                    }*/
                }
            )
        }
    )
    { innerPadding ->
       // val categories by viewModel.categories.observeAsState(emptyList())

        /* LazyColumn(Modifier.padding(innerPadding)) {
            items(categories) {
                CategoryItem(category = it, onItemClick = {
                    viewModel.navigateToDetail(it)
                })
            }
        }*/
        AndroidView(
            factory = { context ->
                val mapView = MapView(context)
                mapView.apply {
                    onCreate(Bundle()) // Se crea el mapa
                }
                mapView
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) { mapView ->
            mapView.getMapAsync { googleMap ->
                // Configurar el mapa aquí
                val sydney = LatLng(-34.0, 151.0) // Ubicación de Sydney
                googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            }
        }
    }
}




@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun MainScenePreview() {
    RadarRidersTheme {
        MapSceneFactory(
            navController = rememberAnimatedNavController(),
            sessionDataSource = SessionDataSource()
        )
    }
}