package com.gimbernat.radarriders.ui.scenes.welcome

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gimbernat.radarriders.R
// import com.gimbernat.radarriders.ui.theme.MyApplicationTheme
import com.gimbernat.radarriders.ui.theme.ThemeSize
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScene(viewModel: WelcomeSceneViewModel) {
    // Composable code for the first screen


    Scaffold() { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = ThemeSize.two.value)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

/*                Text(
                    text = stringResource(R.string.welcome_text_intro),
                    style = MaterialTheme.typography.bodyLarge,

                    )
*/
                Button(
                    onClick = {
                        viewModel.navigateToLogin()
                    },
                    modifier = Modifier.padding(top = ThemeSize.two.value)
                ) {
                    Text(
                        text = "Ok",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun WelcomeScenePreview() {
/*
    MyApplicationTheme {
        WelcomeSceneFactory(
            navController = rememberAnimatedNavController()
        ).create(null)
    }
 */
}
