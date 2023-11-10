package com.sample.weatherinfo.presentation.weather_hourly

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.sample.weather.R
import com.sample.weatherinfo.common.LocationTracker
import com.sample.weatherinfo.presentation.theme.DeepGray
import com.sample.weatherinfo.presentation.theme.LightGray
import com.sample.weatherinfo.presentation.weather_home.WeatherCard
import com.sample.weatherinfo.presentation.weather_home.WeatherHomeViewModel
import com.sample.weatherinfo.presentation.weather_hourly.WeatherHourlyViewModel

@Composable
fun WeatherForecastCompose(
    navigation: NavController,
    viewModel: WeatherHourlyViewModel
) {

    LaunchedEffect("weatherHome") {
        viewModel.getWeatherInfoHourly("30041")
    }

    Box(modifier = Modifier.background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            viewModel.stateForecast.data?.let { wList ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(10.dp)
                ) {
                    items(wList) { model ->
                        Text(text = model.description)
                    }
                }
            }
        }
    }

}