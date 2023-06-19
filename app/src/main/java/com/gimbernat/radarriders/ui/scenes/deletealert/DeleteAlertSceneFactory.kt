package com.gimbernat.radarriders.ui.scenes.deletealert

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory


class DeleteAlertSceneFactory (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource
    ) : ComposableFactory<Any> {
        @Composable
        override fun create(id: String?): Any {
            val viewModel = DeleteAlertViewModel(navController, sessionDataSource)
            return DeleteAlertScene(viewModel = viewModel)
        }
    }
