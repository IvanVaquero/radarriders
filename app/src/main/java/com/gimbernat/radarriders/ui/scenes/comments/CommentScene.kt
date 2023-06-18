package com.gimbernat.radarriders.ui.scenes.comments

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
import androidx.compose.material3.Checkbox
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
import com.gimbernat.radarriders.ui.scenes.edituser.EditUserSceneFactory
import com.gimbernat.radarriders.ui.scenes.edituser.EditUserViewModel

//import com.gimbernat.radarriders.ui.theme.MyApplicationTheme
import com.gimbernat.radarriders.ui.theme.RadarRidersTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentScene(viewModel: CommentViewModel){
    val context = LocalContext.current
    val commentState = remember { mutableStateOf(TextFieldValue("Haz tu comentario")) }
    fun validateInputs(callback: (comment: String) -> Unit) {
        val comment = commentState.value.text
        if (comment.isNotEmpty()) {
            callback(comment)
        } else {
            Toast.makeText(
                context,
                "Introduce un comentario",
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
                text = "Comentarios",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            //    if (viewModel.errorMessage.value.isNotEmpty()) {
            //        Text(text = viewModel.errorMessage.value, color = Color.Red)
            //    }
            OutlinedTextField(
                value = commentState.value,
                onValueChange = { commentState.value = it },
                label = { Text("Comentario") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text

                )
            )
            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(

                    onClick = {
                        validateInputs(){comment ->
                            // viewModel.signUp(email, password)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    // enabled = !viewModel.isLoading.value

                ) {
                    Text(text = "Añade comentario")
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
                    Text(text = "Volver")
                }
            }
        }
    }
}
@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun CommentScenePreview() {
    RadarRidersTheme() {
        CommentSceneFactory(
            navController = rememberAnimatedNavController(),
            sessionDataSource = SessionDataSource()
        )
    }
}