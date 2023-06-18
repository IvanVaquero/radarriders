package com.gimbernat.radarriders.ui.scenes.registro
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory

class RegistroSceneFactory (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource
    ): ComposableFactory<Any> {
    @Composable
    override fun create(id: String?): Any {
        val viewModel = RegistroViewModel(navController, sessionDataSource)
        return RegistroScene(viewModel = viewModel)
    }
}

