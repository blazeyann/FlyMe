package com.blazeyannmanion.flyme.networking

import com.blazeyannmanion.flyme.model.Destination
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {

    @GET("destination")
    fun getDestinations(): Call<ArrayList<Destination>>

    @POST("destination")
    fun addNewDestination(@Body newDestination: Destination): Call<Destination>
}