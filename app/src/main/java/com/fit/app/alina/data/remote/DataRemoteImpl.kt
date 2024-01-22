package com.fit.app.alina.data.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object DataRemoteImpl {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://smsc.ru/sys/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val service = retrofit.create(ApiService::class.java)
}