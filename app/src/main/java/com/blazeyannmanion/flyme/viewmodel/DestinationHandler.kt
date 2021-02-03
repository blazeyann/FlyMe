package com.blazeyannmanion.flyme.viewmodel

import androidx.lifecycle.MutableLiveData
import com.blazeyannmanion.flyme.model.Destination
import com.blazeyannmanion.flyme.networking.RetrofitInstance
import com.blazeyannmanion.flyme.networking.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationHandler {
    var destinationListData: MutableLiveData<ArrayList<Destination>> = MutableLiveData()
    var newDestinationData: MutableLiveData<Destination> = MutableLiveData()


    fun getDestinations() {
        // perform API request to retrieve destination list
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.getDestinations()

        call.enqueue(object : Callback<ArrayList<Destination>> {

            override fun onResponse(
                call: Call<ArrayList<Destination>>,
                response: Response<ArrayList<Destination>>
            ) {
                if (response.isSuccessful) {
                    // update the MutableLiveData with the response body
                    destinationListData.postValue(response.body())
                } else {
                    // return null as response was unsuccessful
                    destinationListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ArrayList<Destination>>, t: Throwable) {
                // catch exception and update UI accordingly
                destinationListData.postValue(null)
            }
        })
    }

    fun addNewDestination(newDestination: Destination) {
        // perform API request to add a new destination
        val retrofitInstance =
            RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofitInstance.addNewDestination(newDestination)

        call.enqueue(object : Callback<Destination> {

            override fun onResponse(
                call: Call<Destination>,
                response: Response<Destination>
            ) {
                if (response.isSuccessful) {
                    // update UI as new destination was successfully added
                    newDestinationData.postValue(response.body())
                } else {
                    // update UI as there was an error adding new destination
                    newDestinationData.postValue(null)
                }
            }

            override fun onFailure(call: Call<Destination>, t: Throwable) {
                // catch exception and update UI accordingly
                newDestinationData.postValue(null)
            }
        })
    }
}