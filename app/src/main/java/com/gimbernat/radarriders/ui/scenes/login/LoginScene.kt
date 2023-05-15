package com.gimbernat.radarriders.ui.scenes.login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gimbernat.radarriders.R
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.ui.theme.RadarRidersTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScene(viewModel: LoginViewModel) {
    val context = LocalContext.current

    val emailState = remember { mutableStateOf(TextFieldValue("albert.leal@eug.es")) }
    val passwordState = remember { mutableStateOf(TextFieldValue("P455w0rd#")) }

    fun validateInputs(callback: (email: String, password: String) -> Unit) {
        val email = emailState.value.text
        val password = passwordState.value.text
        if (email.isNotEmpty()  && password.isNotEmpty()) {
            callback(email, password)
        } else {
            Toast.makeText(
                context,
                "Please enter email and password.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    //The Scaffold composable is used to create the top-level structure of the application.
    //It includes a TopAppBar with the application name as the title.
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
                text = "Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (viewModel.errorMessage.value.isNotEmpty()) {
                Text(text = viewModel.errorMessage.value, color = Color.Red)
            }

            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            OutlinedTextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        validateInputs(){ email, password ->
                            viewModel.signUp(email, password)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !viewModel.isLoading.value
                ) {
                    Text(text = "Sign Up")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        validateInputs(){ email, password ->
                            viewModel.login(email, password)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !viewModel.isLoading.value
                ) {
                    Text(text = "Login")
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun LoginScenePreview() {
    RadarRidersTheme() {
        LoginSceneFactory(
            navController = rememberAnimatedNavController(),
            sessionDataSource = SessionDataSource()
        )
    }
}
