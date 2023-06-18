package com.gimbernat.radarriders.ui.scenes.edituser

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast

import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.gimbernat.radarriders.R
import com.gimbernat.radarriders.datasources.SessionDataSource
//import com.gimbernat.radarriders.ui.scenes.login.LoginSceneFactory
import com.gimbernat.radarriders.ui.scenes.welcome.WelcomeSceneFactory
//import com.gimbernat.radarriders.ui.theme.MyApplicationTheme
import com.gimbernat.radarriders.ui.theme.RadarRidersTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EdituserScene(viewModel: EditUserViewModel){
    val context = LocalContext.current
    val nameState = remember { mutableStateOf(TextFieldValue("Marc")) }//Llamar usuario Base de Datos.
    val emailState = remember { mutableStateOf(TextFieldValue("MarcGes@mail.es")) } //LLamar usuario Base de datos
    val passwordState = remember { mutableStateOf(TextFieldValue("#HyperMegaPassword1234.")) } //LLamar usuario Base de

    fun validateInputs(callback: (name: String, email: String, password: String) -> Unit) {
        val name = nameState.value.text
        val email = emailState.value.text
        val password = passwordState.value.text
        if (name.isNotEmpty() && email.isNotEmpty()  && password.isNotEmpty()) {
            callback(name, email, password)
        } else {
            Toast.makeText(
                context,
                "Please enter name, email and password.",
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
                text = "Modificar datos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            //    if (viewModel.errorMessage.value.isNotEmpty()) {
            //        Text(text = viewModel.errorMessage.value, color = Color.Red)
            //    }
            OutlinedTextField(
                value = nameState.value,
                onValueChange = { nameState.value = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text

                )
            )

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
                        validateInputs(){name, email, password ->
                            // viewModel.signUp(email, password)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    // enabled = !viewModel.isLoading.value

                ) {
                    Text(text = "Sign Up")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        /*
                        validateInputs(){ email, password ->
                            viewModel.login(email, password)

                        }
                         */
                        viewModel.navigateToMain()
                    },
                    modifier = Modifier.weight(1f),
                    //            enabled = !viewModel.isLoading.value
                ) {
                    Text(text = "Mapa")
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun EditUserScenePreview() {
    RadarRidersTheme() {
        EditUserSceneFactory(
            navController = rememberAnimatedNavController(),
            sessionDataSource = SessionDataSource()
        )
    }
}
