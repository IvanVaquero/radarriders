package com.gimbernat.radarriders.ui.scenes.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gimbernat.radarriders.AppRoutes
// import com.gimbernat.radarriders.datasources.CapsulesDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
// import com.gimbernat.radarriders.models.Capsule
import kotlinx.coroutines.launch

class MainSceneViewModel(
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource,
    // private val capsulesDataSource: CapsulesDataSource
) : ViewModel() {
    // private val _capsules = MutableLiveData<List<Capsule>>()
    // val capsules: LiveData<List<Capsule>> = _capsules
//    fun signOut() {
//        viewModelScope.launch {
//            sessionDataSource.signOutUser()
//            navController.navigate(AppRoutes.WELCOME.value) {
//                popUpTo(AppRoutes.MAIN.value) {
//                    inclusive = true
//                }
//            }
//        }
//    }
}