package com.sample.weatherinfo.presentation.weather_home

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
fun WeatherHome(
    navigation: NavController,
    viewModel: WeatherHomeViewModel
) {

    LaunchedEffect("weatherHome") {
        viewModel.getWeatherInfoByLatLong()
    }

    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGray)
        ) {
            WeatherCard(
                state = viewModel.state,
                backgroundColor = DeepGray
            )
            Card(
                backgroundColor = LightGray,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Text(
                        text = "Next 5 Days",
                        color = Color.Black,
                        modifier = Modifier
                            .weight(1f)
                    )

                    Image(
                        painterResource(R.drawable.right_arrow),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        if(viewModel.state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        viewModel.state.error?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}