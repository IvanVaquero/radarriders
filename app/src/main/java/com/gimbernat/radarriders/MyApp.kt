@file:OptIn(ExperimentalAnimationApi::class)

package com.gimbernat.radarriders

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gimbernat.radarriders.datasources.AlertDataSource
import com.gimbernat.radarriders.datasources.RadarDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.ui.scenes.edituser.EditUserSceneFactory
import com.gimbernat.radarriders.ui.scenes.map.MapSceneFactory
import com.gimbernat.radarriders.ui.scenes.login.*
import com.gimbernat.radarriders.ui.scenes.newalerta.CrearAlertSceneFactory
import com.gimbernat.radarriders.ui.scenes.newradar.CrearRadarSceneFactory
import com.gimbernat.radarriders.ui.scenes.registro.RegistroSceneFactory
import com.gimbernat.radarriders.ui.scenes.welcome.WelcomeSceneFactory
import com.gimbernat.radarriders.ui.theme.RadarRidersTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterial3Api
@Composable
fun MyApp() {
    val navController = rememberAnimatedNavController()

    // Conexiones BBDD
    val sessionDataSource = SessionDataSource()
    val radarDataSource = RadarDataSource(database = FirebaseDatabase.getInstance())
    val alertDataSource = AlertDataSource(database = FirebaseDatabase.getInstance())

    // WelcomeScene
    val welcomeSceneFactory = WelcomeSceneFactory(navController)

    // Map
    val mapSceneFactory = MapSceneFactory(navController, sessionDataSource, radarDataSource)

    // Login
    val loginSceneFactory =  LoginSceneFactory(navController, sessionDataSource)

    //Edit User
    val edituserSceneFactory = EditUserSceneFactory(navController, sessionDataSource)

    //Registro
    val registroSceneFactory = RegistroSceneFactory(navController, sessionDataSource)

    //Add Radar
    val crearRadarSceneFactory = CrearRadarSceneFactory(
        navController,
        sessionDataSource,
        radarDataSource
    )

    //Add Alert
    val crearAlertSceneFactory = CrearAlertSceneFactory(
        navController,
        sessionDataSource,
        alertDataSource
    )

    // Determine the start destination based on whether the user is logged in or not
     val startDestination =
         if (sessionDataSource.isLoggedIn() ) AppRoutes.MAP.value else AppRoutes.WELCOME.value

    //it uses the MyApplicationTheme to define the theme for the application.

    RadarRidersTheme {

        //The AnimatedNavHost is a navigation host that supports transitions between
        // different destinations. It is used to manage the app's navigation graph,
        // which is defined by a set of composable destinations.

        //There are three composable destinations defined in this code:

        //WelcomeScene: This is the starting destination with the route AppRoutes.BOOT.value.
        //MainScene: This is the main destination with the route AppRoutes.MAIN.value.
        //LoginScene: This is the login destination with the route AppRoutes.LOGIN.value.

        //Each destination is associated with a composable function that defines the UI for
        //that destination. The navController pass as paramater will be used to navigate
        //between destinations.

        AnimatedNavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable(
                AppRoutes.WELCOME.value
            ) {
                welcomeSceneFactory.create(null)
            }

            composable(
                AppRoutes.MAP.value
            ) {
                mapSceneFactory.create(null)
            }

            composable(
                AppRoutes.LOGIN.value
            ) {
                loginSceneFactory.create(null)
            }

            composable(
                AppRoutes.EDITUSER.value
            ) {
                edituserSceneFactory.create(null)
            }

            composable(
                AppRoutes.REGISTRO.value
            ) {
                registroSceneFactory.create(null)
            }

            composable(
                AppRoutes.NEWRADAR.value
            ) {
                crearRadarSceneFactory.create(null)
            }

            composable(
                AppRoutes.NEWALERT.value
            ) {
                crearAlertSceneFactory.create(null)
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MyAppPreview() {

    RadarRidersTheme {
        MyApp()
    }


}