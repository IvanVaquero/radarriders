package com.gimbernat.radarriders.ui.scenes.edituser

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gimbernat.radarriders.AppRoutes
import com.gimbernat.radarriders.datasources.SessionDataSource
import kotlinx.coroutines.launch

class EditUserViewModel(
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource
) : ViewModel() {
    val message = MutableLiveData<String>()

    var isLoading = mutableStateOf(false)
    fun navigateToMain() {
        viewModelScope.launch {
            navController.navigate(AppRoutes.MAP.value) {
                popUpTo(AppRoutes.EDITUSER.value) {
                    inclusive = true
                }
            }
        }
    }

    fun updateEmail(newEmail: String) {
        viewModelScope.launch {
            isLoading.value = true
            val result = sessionDataSource.updateEmail(newEmail)
            if (result) {
                message.value = "Email actualizado correctamente"
                isLoading.value = false
                navigateToMain()
            } else {
                isLoading.value = false
                message.value = "Error al actualizar el email"
            }
            isLoading.value = false
        }
    }

}