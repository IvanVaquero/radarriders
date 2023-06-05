package com.gimbernat.radarriders.ui.scenes.map

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gimbernat.radarriders.datasources.SessionDataSource
//import com.gimbernat.radarriders.datasources.CategoriesDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory
import com.gimbernat.radarriders.ui.scenes.map.MapScene
import com.gimbernat.radarriders.ui.scenes.map.MapSceneViewModel

class MapSceneFactory (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource,
) :
    ComposableFactory<Any> {
    @Composable
    override fun create(id: String?): Any {
//        val viewModel = MapSceneViewModel(navController, sessionDataSource, categoriesDataSource)
        val viewModel = MapSceneViewModel(navController, sessionDataSource)
        return MapScene(viewModel)
    }
}