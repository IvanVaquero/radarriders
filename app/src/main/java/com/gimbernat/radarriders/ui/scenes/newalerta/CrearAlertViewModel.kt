package com.gimbernat.radarriders.ui.scenes.newalerta

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gimbernat.radarriders.AppRoutes
import com.gimbernat.radarriders.datasources.AlertDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.models.Alert
import com.gimbernat.radarriders.models.Radar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CrearAlertViewModel (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource,
    private val alertDataSource: AlertDataSource
    ) : ViewModel() {
    var isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    fun createAlert(Desc: String, Title: String) {
        viewModelScope.launch {
            isLoading.value = true
            val newAlert = Alert(
                Desc,
                Title,
                sessionDataSource.getCurrentUser()?.email
            )
            try {
                isLoading.value = false
                if (!alertDataSource.createAlert(newAlert)) {
                    errorMessage.value = "Error creating alert"
                } else {
                    goBack()
                }
            } catch (e: Exception) {
                isLoading.value = false
                errorMessage.value = "Error creating alert: ${e.message}"
            }
            isLoading.value = false
        }
    }

    fun goBack() {
        navController.popBackStack()
    }
        fun navigateToMain() {
            viewModelScope.launch {
                navController.navigate(AppRoutes.MAP.value) {
                    popUpTo(AppRoutes.NEWALERT.value) {
                        inclusive = true
                    }
                }
            }
        }
    }

