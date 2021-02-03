package com.blazeyannmanion.flyme.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blazeyannmanion.flyme.model.Destination
import javax.inject.Inject

class DestinationViewModel @Inject constructor() : ViewModel() {

    private val destinationHandler = DestinationHandler()

    fun getDestinations() {
        destinationHandler.getDestinations()
    }

    fun addNewDestination(newDestination: Destination) {
        destinationHandler.addNewDestination(newDestination)
    }

    fun observeDestinations() : MutableLiveData<ArrayList<Destination>> {
        return destinationHandler.destinationListData
    }

    fun observeOnNewDestinationCreated() : MutableLiveData<Destination> {
        return destinationHandler.newDestinationData
    }
}