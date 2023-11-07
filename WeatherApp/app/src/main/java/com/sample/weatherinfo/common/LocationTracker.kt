package com.sample.weatherinfo.common

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}