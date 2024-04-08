package com.example.pcbuilderhelper.api

import retrofit2.Call
import retrofit2.http.GET

interface ComponentApi {
    @GET("product.list.php?catalog=micro&category=wpr")
    fun getComponents(): Call<ApiResponse>
}