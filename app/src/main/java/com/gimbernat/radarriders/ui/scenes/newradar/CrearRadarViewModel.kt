package com.gimbernat.radarriders.ui.scenes.newradar
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gimbernat.radarriders.AppRoutes
import com.gimbernat.radarriders.datasources.RadarDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.models.Radar
import kotlinx.coroutines.launch
class CrearRadarViewModel (

    private val navController: NavController,
    private val sessionDataSource: SessionDataSource,
    private val radarDataSource: RadarDataSource

    ) : ViewModel() {

        var isLoading = mutableStateOf(false)
        val errorMessage = mutableStateOf("")

        fun createRadar(limit: Int, name: String,latitude: Double, longitude: Double) {
            viewModelScope.launch {
                isLoading.value = true
                val newRadar = Radar(
                    name,
                    latitude,
                    longitude,
                    limit,
                null,
                    sessionDataSource.getCurrentUser()?.email
                )
                try {
                    isLoading.value = false
                    if (!radarDataSource.createRadar(newRadar)) {
                        errorMessage.value = "Error al crear radar"
                    } else {
                        navigateToMain()
                    }
                } catch (e: Exception) {
                    isLoading.value = false
                    errorMessage.value = "Error creando radar: ${e.message}"
                }
                isLoading.value = false
            }
        }

        fun navigateToMain() {
            viewModelScope.launch {
                navController.navigate(AppRoutes.MAP.value) {
                    popUpTo(AppRoutes.NEWRADAR.value) {
                        inclusive = true
                    }
                }
            }
        }
    }

