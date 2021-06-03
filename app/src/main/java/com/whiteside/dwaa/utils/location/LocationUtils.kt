package com.whiteside.dwaa.utils.location

import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.lang.Exception

class LocationUtils {


    companion object {
        fun getLocationAddressName(map: Map<String, Double>, geocoder: Geocoder): String? {
            try {
                val addresses = geocoder.getFromLocation(
                    map[LocationConstants.latitude]!!,
                    map[LocationConstants.longitude]!!,
                    1
                )
                val address = addresses[0]
                return "${address.subAdminArea},${address.adminArea},${address.countryName}"
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
            return null
        }

        fun getLocationAddressName(location: LatLng, geocoder: Geocoder): String? {
            try {
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val address = addresses[0]
                return "${address.subAdminArea},${address.adminArea},${address.countryName}"
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
            return null
        }
    }
}