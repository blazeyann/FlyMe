package com.blazeyannmanion.flyme.ui

import java.io.Serializable

data class AddDestinationFragmentResponse(
    val loadDestinations: Boolean
) : Serializable {
    companion object {
        const val KEY = "Add Destination"
    }
}