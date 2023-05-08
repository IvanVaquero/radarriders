package com.gimbernat.radarriders.ui.scenes.welcome

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory
import com.gimbernat.radarriders.ui.scenes.main.MainScene
import com.gimbernat.radarriders.ui.scenes.main.MainSceneViewModel

class WelcomeSceneFactory (private val navController: NavController) :
    ComposableFactory<Any> {
    @Composable
    override fun create(id: String?): Any {
        val viewModel = WelcomeSceneViewModel(navController)
        return WelcomeScene(viewModel)
    }
}