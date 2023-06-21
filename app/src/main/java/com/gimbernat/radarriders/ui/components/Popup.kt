package com.gimbernat.radarriders.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Suppress("unused")

// Use to show information for the user
fun PopupDialog(title: String, message: String, onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            Button(onClick = onClose) {
                Text(text = "OK")
            }
        },
        dismissButton = null,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        modifier = Modifier.width(300.dp)
    )
}