package com.blazeyannmanion.flyme.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.0.13:3000/"

    private val retrofitBuilder = Retrofit.Builder()

    fun getRetrofitInstance(): Retrofit {
        return retrofitBuilder
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}