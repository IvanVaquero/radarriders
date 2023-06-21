package com.gimbernat.radarriders.ui.scenes.newalerta

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gimbernat.radarriders.datasources.AlertDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory


class CrearAlertSceneFactory (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource,
    private val alertDataSource: AlertDataSource
) : ComposableFactory<Any> {
        @Composable
        override fun create(id: String?): Any {
            val viewModel = CrearAlertViewModel(navController, sessionDataSource, alertDataSource)
            return CrearAlertScene(viewModel = viewModel)
        }
    }
