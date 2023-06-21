package com.gimbernat.radarriders.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
@Suppress("unused")

// Use to show information for the user
fun InfoDialog(
    title: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        confirmButton = {  },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Close")
            }
        },
    )
}