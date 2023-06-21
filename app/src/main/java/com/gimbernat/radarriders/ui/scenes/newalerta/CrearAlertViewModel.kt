package com.gimbernat.radarriders.ui.scenes.newalerta

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gimbernat.radarriders.AppRoutes
import com.gimbernat.radarriders.datasources.AlertDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.models.Alert
import kotlinx.coroutines.launch

class CrearAlertViewModel (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource,
    private val alertDataSource: AlertDataSource
    ) : ViewModel() {
    var isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    fun createAlert(desc: String, title: String) {
        viewModelScope.launch {
            isLoading.value = true
            val newAlert = Alert(
                desc,
                title,
                sessionDataSource.getCurrentUser()?.email
            )
            try {
                isLoading.value = false
                if (!alertDataSource.createAlert(newAlert)) {
                    errorMessage.value = "Error al crear alerta"
                } else {
                    goBack()
                }
            } catch (e: Exception) {
                isLoading.value = false
                errorMessage.value = "Error creando la alerta: ${e.message}"
            }
            isLoading.value = false
        }
    }

    private fun goBack() {
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

