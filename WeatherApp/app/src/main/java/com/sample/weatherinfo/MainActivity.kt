package com.sample.weatherinfo

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sample.weatherinfo.presentation.theme.WeatherAppTheme
import com.sample.weatherinfo.presentation.weather_hourly.WeatherForecastCompose
import com.sample.weatherinfo.presentation.weather_home.WeatherHome
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
                        WeatherHome(navigation = navController, hiltViewModel(), LocalContext.current)
                    }
                    composable("WeatherForecast/{city_name}",
                        arguments = listOf(navArgument("city_name") {
                            type = NavType.StringType
                        })
                    ) {
                        val cityName = it.arguments?.getString("city_name") ?: "30022"
                        WeatherForecastCompose(
                            navigation = navController,
                            hiltViewModel(),
                            cityName
                        )
                    }
                }
            }
        }
    }
}