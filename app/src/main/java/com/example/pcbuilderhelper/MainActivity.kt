package com.example.pcbuilderhelper

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pcbuilderhelper.api.ApiClient
import com.example.pcbuilderhelper.api.ApiResponse
import com.example.pcbuilderhelper.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val call = ApiClient.service.getComponents()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val components = apiResponse?.result?.product_list?.document
                    for (component in components.orEmpty()) {
                        println("Nom: ${component.label}")
                        println("Type: ${component.sublabel}")
                        println("Prix: ${component.offer?.price?.price_final}")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API Error", "Error body: $errorBody")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                println("erreur lors de la récupération des composants 2")
            }
        })

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}