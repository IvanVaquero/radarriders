package com.gimbernat.radarriders.ui.scenes.comments
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.helpers.ComposableFactory
import com.gimbernat.radarriders.ui.scenes.edituser.EditUserViewModel
import com.gimbernat.radarriders.ui.scenes.edituser.EdituserScene

class CommentSceneFactory (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource
    ) : ComposableFactory<Any> {
        @Composable
        override fun create(id: String?): Any {
            val viewModel = CommentViewModel(navController, sessionDataSource)
            return CommentScene(viewModel = viewModel)
        }
    }
