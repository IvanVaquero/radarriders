@file:Suppress("unused")

package com.gimbernat.radarriders.ui.scenes.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gimbernat.radarriders.AppRoutes
import com.gimbernat.radarriders.datasources.SessionDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource
) : ViewModel() {

    private val _loggedIn = MutableStateFlow(false)

    var isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    fun isLoggedIn() {
        _loggedIn.value = sessionDataSource.isLoggedIn()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            val success = sessionDataSource.loginUser(email, password)
            _loggedIn.value = success
            if(!success){
                isLoading.value = false
                errorMessage.value = "Incorrect Credentials"
            } else{
                navigateToMain()
            }

        }
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
                navigateToMain()
            }
        }
    }

    fun navigateToMain() {
        viewModelScope.launch {
            navController.navigate(AppRoutes.MAP.value) {
                popUpTo(AppRoutes.LOGIN.value) {
                    inclusive = true
                }
            }
        }
    }

    fun navigateToRegistro() {
        viewModelScope.launch {
            isLoading.value = false
            navController.navigate(AppRoutes.REGISTRO.value) {
                popUpTo(AppRoutes.LOGIN.value) {
                    inclusive = true
                }
            }
        }
    }

}