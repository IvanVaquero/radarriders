package com.gimbernat.radarriders.ui.scenes.edituser

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory

class EditUserSceneFactory(
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource
) : ComposableFactory<Any> {
    @Composable
    override fun create(id: String?): Any {
        val viewModel = EditUserViewModel(navController, sessionDataSource)
        return EdituserScene(viewModel = viewModel)
    }
}