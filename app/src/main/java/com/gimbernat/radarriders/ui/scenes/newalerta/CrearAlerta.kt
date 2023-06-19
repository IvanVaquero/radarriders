package com.gimbernat.radarriders.ui.scenes.newalerta

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gimbernat.radarriders.R
import com.gimbernat.radarriders.datasources.SessionDataSource
import com.gimbernat.radarriders.ui.scenes.editalert.EditAlertSceneFactory
import com.gimbernat.radarriders.ui.scenes.editalert.EditAlertViewModel
import com.gimbernat.radarriders.ui.scenes.editradar.EditRadarViewModel

//import com.gimbernat.radarriders.ui.theme.MyApplicationTheme
import com.gimbernat.radarriders.ui.theme.RadarRidersTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CrearAlertScene(viewModel: CrearAlertViewModel){
    val context = LocalContext.current
    val titleState = remember { mutableStateOf(TextFieldValue("Titulo Alerta")) }
    val descState = remember { mutableStateOf(TextFieldValue("descripción")) }
    fun validateInputs(callback: (title: String, desc: String) -> Unit) {
        val title = titleState.value.text
        val desc = descState.value.text
        if (title.isNotEmpty() && desc.isNotEmpty()) {
            callback(title, desc)
        } else {
            Toast.makeText(
                context,
                "Introduzca titulo y descripción de la alerta",
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
                text = "Add a new Alert",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            //    if (viewModel.errorMessage.value.isNotEmpty()) {
            //        Text(text = viewModel.errorMessage.value, color = Color.Red)
            //    }
            OutlinedTextField(
                value = titleState.value,
                onValueChange = { titleState.value = it },
                label = { Text("Tittle") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text

                )
            )

            OutlinedTextField(
                value = descState.value,
                onValueChange = { descState.value = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 34.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(

                    onClick = {
                        validateInputs(){title, desc ->
                            // viewModel.signUp(email, password)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    // enabled = !viewModel.isLoading.value

                ) {
                    Text(text = "Add Alert")
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
                    Text(text = "Go back")
                }
            }
        }
    }
}
@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun CrearAlertaScenePreview() {
    RadarRidersTheme() {
        CrearAlertSceneFactory(
            navController = rememberAnimatedNavController(),
            sessionDataSource = SessionDataSource()
        )
    }
}