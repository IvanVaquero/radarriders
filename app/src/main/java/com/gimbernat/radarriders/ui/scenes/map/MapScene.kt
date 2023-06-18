package com.gimbernat.radarriders.ui.scenes.map


import android.annotation.SuppressLint
import android.util.Log

import androidx.compose.animation.ExperimentalAnimationApi
<<<<<<< HEAD
import androidx.compose.foundation.layout.Box
=======
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
>>>>>>> 51e01194e374b74328d192879485c7ba9e85c2bc
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.gimbernat.radarriders.R
import com.gimbernat.radarriders.datasources.RadarDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.models.Radar
//import com.gimbernat.radarriders.ui.scenes.login.LoginSceneFactory
//import com.gimbernat.radarriders.ui.theme.MyApplicationTheme
import com.gimbernat.radarriders.ui.theme.RadarRidersTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScene(viewModel: MapSceneViewModel) {
    //Loads
//    viewModel.fetch()

    //The Scaffold composable is used to create the top-level structure of the application.
    //It includes a TopAppBar with the application name as the title.
    //It includes also a button to sign out
    val mapView = viewModel.rememberMapViewWithLifecycle()
    val isDropdownMenuItemClicked = remember { mutableStateOf(false) }
    val selectedLocation = remember { mutableStateOf<Radar?>(null) }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = { viewModel.editUser() }) {
                        Box(
                            Modifier
                                .size(37.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.DarkGray)
                        ) {
                            Icon(
                                Icons.Filled.AccountCircle,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(4.dp)
                            )
                        }
                    }

                    IconButton(onClick = { viewModel.signOut() }) {
                        Box(
                            Modifier
                                .size(37.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.DarkGray)
                        ) {
                            Icon(
                                Icons.Filled.ExitToApp,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(4.dp)
                            )
                        }
                    }

                }


            )
        }
    )
    { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = {
                    mapView
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                viewModel.setupMap(it)
                if (isDropdownMenuItemClicked.value) {
                    viewModel.moveCamera(it, selectedLocation)
                }
            }
            Button(
                onClick = {
                    viewModel.editUser()
                },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "Editar Usuario")
            }
        }
    }
<<<<<<< HEAD
=======
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
>>>>>>> 51e01194e374b74328d192879485c7ba9e85c2bc
}