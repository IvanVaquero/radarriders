package com.gimbernat.radarriders.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Suppress("unused")
@Composable
fun BackButton(onItemClick: () -> Unit){
    IconButton(onClick = onItemClick) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
    }
}