package com.gimbernat.radarriders.ui.scenes.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.gimbernat.radarriders.datasources.AlertDataSource
import com.gimbernat.radarriders.datasources.RadarDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory
import com.gimbernat.radarriders.ui.scenes.map.MapScene
import com.gimbernat.radarriders.ui.scenes.map.MapSceneViewModel

class MapSceneFactory (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource,
    private val radarDataSource: RadarDataSource,
) :
    ComposableFactory<Any> {
    @Composable
    override fun create(id: String?): Any {
        val context = LocalContext.current
        val viewModel = MapSceneViewModel(
            navController,
            sessionDataSource,
            radarDataSource,
            context
        )
        return MapScene(viewModel)
    }
}