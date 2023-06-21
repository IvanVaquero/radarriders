package com.gimbernat.radarriders.ui.scenes.newradar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gimbernat.radarriders.datasources.RadarDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory

class CrearRadarSceneFactory (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource,
    private val radarDataSource: RadarDataSource
    ): ComposableFactory<Any> {
    @Composable
    override fun create(id: String?): Any {
        val viewModel = CrearRadarViewModel(navController, sessionDataSource, radarDataSource)
        return CrearRadarScene(viewModel = viewModel)
    }
}