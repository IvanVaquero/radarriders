package com.gimbernat.radarriders.ui.scenes.editalert

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory
import com.gimbernat.radarriders.ui.scenes.editradar.EditRadarViewModel

    class EditAlertSceneFactory(
        private val navController: NavController,
        private val sessionDataSource: SessionDataSource
    ) : ComposableFactory<Any> {
        @Composable
        override fun create(id: String?): Any {
            val viewModel = EditAlertViewModel(navController, sessionDataSource)
            return EditAlertScene(viewModel = viewModel)
        }
    }
