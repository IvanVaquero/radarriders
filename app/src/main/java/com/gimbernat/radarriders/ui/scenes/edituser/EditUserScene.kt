package com.gimbernat.radarriders.ui.scenes.edituser

import android.annotation.SuppressLint
import android.widget.Toast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gimbernat.radarriders.R
import com.gimbernat.radarriders.datasources.SessionDataSource

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScene(viewModel: EditUserViewModel, sessionDataSource: SessionDataSource) {
    val context = LocalContext.current

    val message by viewModel.message.observeAsState()
    message?.let {
        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
    }

    val emailState = remember { mutableStateOf(TextFieldValue("")) } //LLamar usuario Base de datos
    val oldEmailState = remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        val currentUser = sessionDataSource.getCurrentUser()
        currentUser?.let {
            oldEmailState.value = TextFieldValue(it.email ?: "")
        }
    }

    fun validateInputs(callback: (email: String) -> Unit) {
        val email = emailState.value.text
        if (email.isNotEmpty()) {
            callback(email)
        } else {
            Toast.makeText(
                context,
                "Email no puede ser vacio.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) }
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "User Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = oldEmailState.value,
                onValueChange = { },
                label = { Text("Old Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                enabled = false
            )

            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = { Text("New Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = {
                        viewModel.navigateToMain()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !viewModel.isLoading.value
                ) {
                    Text(text = "Go back")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        validateInputs { email ->
                            viewModel.updateEmail(email)                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !viewModel.isLoading.value
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}
