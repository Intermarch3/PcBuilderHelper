package com.example.pcbuilderhelper.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ComponentApi {
    @GET
    fun getComponents(@Url url: String): Call<ApiResponse>
}