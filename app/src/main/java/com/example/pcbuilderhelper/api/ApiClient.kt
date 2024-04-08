package com.example.pcbuilderhelper.api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://www.topachat.com/api/catalog/" // Remplace cette URL par l'URL de ton API

    val client = OkHttpClient.Builder()
        .addInterceptor(Header()) // Ajoute ton intercepteur ici
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: ComponentApi = retrofit.create(ComponentApi::class.java)
}