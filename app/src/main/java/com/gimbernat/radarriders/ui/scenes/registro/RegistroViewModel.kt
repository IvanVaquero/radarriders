package com.gimbernat.radarriders.ui.scenes.registro

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

class RegistroViewModel (
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource
    ) : ViewModel() {

    var isLoading = mutableStateOf(false)
    private val _loggedIn = MutableStateFlow(false)
    val errorMessage = mutableStateOf("")

    fun isLoggedIn() {
        _loggedIn.value = sessionDataSource.isLoggedIn()
    }
    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            val success = sessionDataSource.signUpUser(email, password)
            _loggedIn.value = success
            if(!success){
                isLoading.value = false
                errorMessage.value = "Email already in use"
            } else{
//                navigateToMain()
            }
        }
    }

    fun navigateToLogin() {
        viewModelScope.launch {
            navController.navigate(AppRoutes.LOGIN.value) {
                popUpTo(AppRoutes.REGISTRO.value) {
                    inclusive = true
                }
            }
        }    }
}
