package com.gimbernat.radarriders.ui.scenes.eliminarusuario

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gimbernat.radarriders.AppRoutes
import com.gimbernat.radarriders.datasources.SessionDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeleteUserViewModel (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource
) : ViewModel() {

    fun navigateToMain() {
        viewModelScope.launch {
            navController.navigate(AppRoutes.MAP.value) {
                popUpTo(AppRoutes.DELETEUSER.value) {
                    inclusive = true
                }
            }
        }
    }
}