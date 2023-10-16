package com.sample.weatherinfo.presentation.weather_home

/**
 * WeatherInfoFragment, display the current weather condition based on provided City/Zip
 *
 */

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.sample.weather.R
import com.sample.weather.databinding.FragmentWeatherHomesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


/**
 * WeatherHomeFragment, display weather information for the provided city name and/or Lat Long co-ordinates
 * NOTE: PROVIDE SOME ADDITIONAL TIME, THIS SCREEN CAN BE BUILD USING JET-PACK COMPOSE WITH LESSER CODE AND XML LAYOUT FREE
 */

@AndroidEntryPoint
class WeatherHomeFragment : Fragment(), LocationListener {

    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2

    private val viewModel: WeatherHomeViewModel by viewModels()
    private var _binding: FragmentWeatherHomesBinding? = null
    private val binding: FragmentWeatherHomesBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherHomesBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initializing and invoking coroutine in the Lifecycle scope, all resources will be released when Activity/Fragment destroyed
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.weatherInfo.collect { it ->
                if (it.isLoading) {
                    binding.progressWeatherDtlSearch.visibility = View.VISIBLE
                    binding.weatherConditionDesc.text = "--"
                    binding.weatherConditionDesc.visibility = View.VISIBLE
                }
                if (it.error.isNotBlank()) {
                    binding.progressWeatherDtlSearch.visibility = View.GONE
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }

                it.data?.let { weatherInfo ->
                    binding.weatherDtl = weatherInfo
                    binding.progressWeatherDtlSearch.visibility = View.GONE
                    viewModel.setLocalStoredLocation(context, weatherInfo.placeName )
                }
            }
        }

        binding.submit.setOnClickListener {
            viewModel.getWeatherInfo(binding.citySearchView.text.toString())
        }

        binding.hourlyClick.setOnClickListener {
            findNavController().navigate(
                // Important: The custom param Type can be configured in Navigation Controller xml layout
                WeatherHomeFragmentDirections.actionWeatherInfoFragmentToWeatherHourlyFragment (
                    viewModel.getLocalStoredLocation(context)
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        // Check if any previously visited place (stored locally)
        val localStoredLocation = viewModel.getLocalStoredLocation(context)
        localStoredLocation?.let { location ->
            // Fond the place stored in local shared preference
            viewModel.getWeatherInfo(location)
        } ?: run {
            // place not found in local shared preference, getting the lat long co-ordinate based on current device location
            getLatLongLocation()
        }
    }



    private fun getLatLongLocation() {
        try{
            locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if ((context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED)) {
                activity?.let {
                    ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
                }
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1f, this)
        } catch(_: Exception){
            println(getString(R.string.error_in_getting_location_permission))
        }
    }

    override fun onLocationChanged(location: Location) {
        locationManager.removeUpdates(this);
        viewModel.getWeatherInfoByLatLong(location.latitude, location.longitude)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }
}