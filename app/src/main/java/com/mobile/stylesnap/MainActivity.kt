package com.mobile.stylesnap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.stylesnap.ui.screens.HomeScreen
import com.mobile.stylesnap.ui.theme.StyleSnapTheme
import com.mobile.stylesnap.viewmodels.StyleViewModel
import com.mobile.stylesnap.viewmodels.StyleViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StyleSnapTheme {
                AppNavigation()
            }
        }
    }

    @Composable
    fun AppNavigation(){

        val navController = rememberNavController()
        val context = LocalContext.current
        val styleViewModel: StyleViewModel = viewModel(
            factory = StyleViewModelFactory(context)
        )

        NavHost(navController, startDestination = "main"){
            composable("main") {
              HomeScreen(viewModel = styleViewModel)
            }
        }


    }
}
