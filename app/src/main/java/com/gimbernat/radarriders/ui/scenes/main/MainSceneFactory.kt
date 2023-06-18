package com.gimbernat.radarriders.ui.scenes.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
// import com.gimbernat.radarriders.datasources.CapsulesDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory
import com.gimbernat.radarriders.ui.scenes.login.LoginScene
import com.gimbernat.radarriders.ui.scenes.login.LoginViewModel

class MainSceneFactory (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource,
//     private val capsulesDataSource: CapsulesDataSource
) :
    ComposableFactory<Any> {
    @Composable
    override fun create(id: String?): Any {
//        val viewModel = MainSceneViewModel(navController, sessionDataSource, capsulesDataSource)
        val viewModel = MainSceneViewModel(navController, sessionDataSource)
        return MainScene(viewModel)
    }
}