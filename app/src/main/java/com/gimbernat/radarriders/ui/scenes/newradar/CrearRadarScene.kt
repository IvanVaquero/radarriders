package com.gimbernat.radarriders.ui.scenes.newradar

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gimbernat.radarriders.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CrearRadarScene(viewModel: CrearRadarViewModel){
    val context = LocalContext.current

    val limitState = remember { mutableStateOf(80) }//Llama el limite de velocidad.
    val nameState = remember { mutableStateOf(TextFieldValue("Nombre Radar")) } //LLamar nombre radar
    val latitudeState = remember { mutableStateOf(1234.0) }
    val longitudeState = remember { mutableStateOf(4321.0) }

    fun validateInputs(callback: (limit: Int, name: String,latitude: Double, longitude: Double) -> Unit) {
        val limit = limitState.value
        val name = nameState.value.text
        val latitude = latitudeState.value
        val longitude = longitudeState.value

        if (limit > 0 && name.isNotEmpty()) {
            callback(limit, name, latitude, longitude)
        } else {
            Toast.makeText(
                context,
                "Porfavor entre el límite y el nombre",
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
                text = "Adding a new radar",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (viewModel.errorMessage.value.isNotEmpty()) {
                Text(text = viewModel.errorMessage.value, color = Color.Red)
            }
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
                value = limitState.value.toString(),
                onValueChange = { limitState.value = it.toIntOrNull() ?: 0 },
                label = { Text("Limit") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            OutlinedTextField(
                value = latitudeState.value.toString(),
                onValueChange = { latitudeState.value = (it.toIntOrNull() ?: 0).toDouble() },
                label = { Text("Latitud") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            OutlinedTextField(
                value = longitudeState.value.toString(),
                onValueChange = { longitudeState.value = (it.toIntOrNull() ?: 0).toDouble() },
                label = { Text("Longitud") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(

                    onClick = {
                        validateInputs { limit, name, latitude, longitude ->
                             viewModel.createRadar(limit, name, latitude, longitude)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !viewModel.isLoading.value

                ) {
                    Text(text = "Add Radar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        viewModel.navigateToMain()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !viewModel.isLoading.value
                ) {
                    Text(text = "Go back")
                }
            }
        }
    }
}