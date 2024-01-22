package com.fit.app.alina.data.remote

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    //&phones={phones}&mes={message}
    @POST("send.php?login=petrova.ceo&psw=4b4b811d6797cefb06b9688bcdad639ebdd2f8dd")
    fun postSms(@Query("mes") message: String, @Query("phones") phone: String): Call<String>

}