package com.gimbernat.radarriders.ui.scenes.eliminarusuario

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory


class DeleteUserSceneFactory (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource
) : ComposableFactory<Any> {
    @Composable
    override fun create(id: String?): Any {
        val viewModel = DeleteUserViewModel(navController, sessionDataSource)
        return DeleteUserScene(viewModel = viewModel)
    }
}