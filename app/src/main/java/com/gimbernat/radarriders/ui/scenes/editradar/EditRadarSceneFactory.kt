package com.gimbernat.radarriders.ui.scenes.editradar

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory

class EditRadarSceneFactory(
        private val navController: NavController,
        private val sessionDataSource: SessionDataSource
    ) : ComposableFactory<Any> {
        @Composable
        override fun create(id: String?): Any {
            val viewModel = EditRadarViewModel(navController, sessionDataSource)
            return EditRadarScene(viewModel = viewModel)
        }
    }
