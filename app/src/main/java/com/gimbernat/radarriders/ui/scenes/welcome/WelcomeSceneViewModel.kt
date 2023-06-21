package com.gimbernat.radarriders.ui.scenes.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gimbernat.radarriders.AppRoutes
import kotlinx.coroutines.launch

class WelcomeSceneViewModel(private val navController: NavController) : ViewModel() {
    fun navigateToLogin(){
        viewModelScope.launch {
            navController.navigate(AppRoutes.LOGIN.value) {
                popUpTo(AppRoutes.WELCOME.value) {
                    inclusive = true
                }
            }
        }
    }
}
