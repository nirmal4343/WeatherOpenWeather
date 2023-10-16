package com.sample.weatherinfo.presentation.weather_hourly

/**
 * WeatherHourlyListFragment, display weather hourly list of via RecyclerView
 * NOTE: PROVIDE SOME ADDITIONAL TIME, THIS SCREEN CAN BE BUILD USING JET-PACK COMPOSE WITH LESSER CODE AND XML LAYOUT FREE
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import com.sample.weather.databinding.FragmentWeatherHourlyListBinding
import com.sample.weatherinfo.domain.model.WeatherDtl
import com.sample.weatherinfo.domain.model.WeatherHourlyDtl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WeatherHourlyListFragment : Fragment() {

    private val searchAdapter = WeatherHourlyListAdapter()
    private val viewModel: WeatherHourlyViewModel by viewModels()
    private var _binding: FragmentWeatherHourlyListBinding? = null
    private val binding: FragmentWeatherHourlyListBinding
        get() = _binding!!
    private var weatherHourlyListArrayList: MutableList<WeatherHourlyDtl>? = null

    private val args: WeatherHourlyListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherHourlyListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.weatherHourlyRecycler.apply {
            adapter = searchAdapter
        }
        // Initializing and invoking coroutine in the Lifecycle scope, all resources will be released when Activity/Fragment destroyed

        lifecycle.coroutineScope.launchWhenCreated {

            viewModel.weatherHourlyList.collect { it ->
                if (it.isLoading) {
                    binding.nothingFound.visibility = View.GONE
                    binding.weatherLoading.visibility = View.VISIBLE
                }
                if (it.error.isNotBlank()) {
                    // No Data found, handle UI status accordingly to show empty
                    binding.nothingFound.visibility = View.GONE
                    binding.weatherLoading.visibility = View.GONE
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }

                it.data?.let {
                    if (it.isEmpty()) {
                        binding.nothingFound.visibility = View.VISIBLE
                    }
                    binding.weatherLoading.visibility = View.GONE
                    // Weather list response received
                    weatherHourlyListArrayList = it.toMutableList()
                    //Setting response list array to the Adapter to further display in the RecyclerView
                    searchAdapter.setContentList(it.toMutableList())
                }
            }
        }
        args.location?.let {
            viewModel.getWeatherInfoHourly(it)
        }

    }
}