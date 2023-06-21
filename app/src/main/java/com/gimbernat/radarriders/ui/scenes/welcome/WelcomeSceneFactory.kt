package com.gimbernat.radarriders.ui.scenes.welcome

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gimbernat.radarriders.helpers.ComposableFactory

class WelcomeSceneFactory (private val navController: NavController) :
    ComposableFactory<Any> {
    @Composable
    override fun create(id: String?): Any {
        val viewModel = WelcomeSceneViewModel(navController)
        return WelcomeScene(viewModel)
    }
}