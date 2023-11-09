package com.sample.weatherinfo

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.weatherinfo.presentation.theme.WeatherAppTheme
import com.sample.weatherinfo.presentation.weather_home.WeatherHome
import com.sample.weatherinfo.presentation.weather_home.WeatherHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    //private val viewModel: WeatherHomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {

        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))

        setContent {
            WeatherAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "WeatherHome") {
                    composable("WeatherHome") {
                        WeatherHome(navigation = navController, hiltViewModel())
                    }
                    composable("WeatherForecast") {
                        WeatherHome(navigation = navController, hiltViewModel())
                    }

                }
            }
        }
    }
}