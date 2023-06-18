package com.gimbernat.radarriders.ui.scenes.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gimbernat.radarriders.AppRoutes
//import com.gimbernat.radarriders.datasources.CategoriesDataSource
import com.gimbernat.radarriders.datasources.SessionDataSource
//import com.gimbernat.radarriders.models.Category
import kotlinx.coroutines.launch

class MapSceneViewModel(
    private val navController: NavController,
    private val sessionDataSource: SessionDataSource,
//    private val categoriesDataSource: CategoriesDataSource
) : ViewModel() {
//    private val _categories = MutableLiveData<List<Category>>()
//    val categories: LiveData<List<Category>> = _categories
//
//    fun fetch() {
//        viewModelScope.launch {
//            val categoriesList = categoriesDataSource.fetch()
//            _categories.value = categoriesList
//            subscribe()
//        }
//    }
//
//    fun subscribe() {
//        viewModelScope.launch {
//            categoriesDataSource.subscribe {
//                _categories.value = it
//            }
//        }
//
//    }
fun navigateToMain() {
    viewModelScope.launch {
        navController.navigate(AppRoutes.EDITUSER.value) {
            popUpTo(AppRoutes.MAP.value) {
                inclusive = true
            }
        }
    }
}
}