@file:OptIn(ExperimentalAnimationApi::class)

package com.gimbernat.radarriders

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.navArgument
// import com.gimbernat.radarriders.datasources.CapsulesDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.ui.scenes.comments.CommentSceneFactory
import com.gimbernat.radarriders.ui.scenes.editalert.EditAlertSceneFactory
import com.gimbernat.radarriders.ui.scenes.editradar.EditRadarSceneFactory
import com.gimbernat.radarriders.ui.scenes.edituser.EditUserSceneFactory
import com.gimbernat.radarriders.ui.scenes.edituser.EdituserScene
import com.gimbernat.radarriders.ui.scenes.map.MapSceneFactory
// import com.gimbernat.radarriders.ui.scenes.capsuleDetail.CapsuleDetailSceneFactory
import com.gimbernat.radarriders.ui.scenes.login.*
import com.gimbernat.radarriders.ui.scenes.welcome.WelcomeScene
import com.gimbernat.radarriders.ui.scenes.main.MainSceneFactory
import com.gimbernat.radarriders.ui.scenes.newradar.CrearRadarSceneFactory
import com.gimbernat.radarriders.ui.scenes.registro.RegistroSceneFactory
import com.gimbernat.radarriders.ui.scenes.welcome.WelcomeSceneFactory
import com.gimbernat.radarriders.ui.theme.RadarRidersTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterial3Api
@Composable
fun MyApp() {
    val navController = rememberAnimatedNavController()
    val sessionDataSource = SessionDataSource()
//    val capsulesDataSource = CapsulesDataSource(database = FirebaseDatabase.getInstance())

    //WelcomeScene
    val welcomeSceneFactory = WelcomeSceneFactory(navController)
    val mapSceneFactory = MapSceneFactory(navController, sessionDataSource)
    //LoginScene
    val loginSceneFactory =  LoginSceneFactory(navController, sessionDataSource)
    //MainScene
    val mainSceneFactory = MainSceneFactory(navController, sessionDataSource)
    //Edit User
    val edituserSceneFactory = EditUserSceneFactory(navController, sessionDataSource)
    //Edit Radar
    val editRadarSceneFactory = EditRadarSceneFactory(navController, sessionDataSource)
    //Edit Alerts
    val editAlertSceneFactory = EditAlertSceneFactory(navController, sessionDataSource)
    //Registro
    val registroSceneFactory = RegistroSceneFactory(navController, sessionDataSource)
    //Add Radar
    val crearRadarSceneFactory = CrearRadarSceneFactory(navController, sessionDataSource)
    //Comments
    val commentSceneFactory = CommentSceneFactory(navController, sessionDataSource)


    //Capsule Detail
//    val capsuleDetailSceneFactory = CapsuleDetailSceneFactory(navController, capsulesDataSource)

    // Determine the start destination based on whether the user is logged in or not
    // val startDestination = if (sessionDataSource.isLoggedIn() ) AppRoutes.MAIN.value else AppRoutes.WELCOME.value
    val startDestination = AppRoutes.WELCOME.value


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
                AppRoutes.EDITRADAR.value
            ) {
                editRadarSceneFactory.create(null)
            }

            composable(
                AppRoutes.EDITALERT.value
            ) {
                editAlertSceneFactory.create(null)
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
                AppRoutes.COMMENT.value
            ) {
                commentSceneFactory.create(null)
            }

/*            composable(
                route = AppRoutes.CAPSULE_DETAIL.value+"/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
                enterTransition = {
                    slideInVertically(
                        initialOffsetY = { height -> height },
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    )
                },
                exitTransition = {
                    slideOutVertically(
                        targetOffsetY = { height -> height },
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    )
                }
            ) {
                //Forcing not be null, this is a bad practice
                val id: String = it.arguments?.getString("id")!!
                capsuleDetailSceneFactory.create(id = id)
            }*/
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