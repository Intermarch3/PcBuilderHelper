package com.example.pcbuilderhelper.ui.home

import ComponentAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilderhelper.R
import com.example.pcbuilderhelper.api.ApiClient
import com.example.pcbuilderhelper.api.ApiResponse
import com.example.pcbuilderhelper.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize the Spinner
        val spinner: Spinner = binding.spinnerComponentType
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.component_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val recyclerView: RecyclerView = binding.recyclerViewComponents
        val adapter = ComponentAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Ajouter une ligne de séparation entre chaque élément de la liste
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        // Set an onItemSelectedListener to the Spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedComponentType = parent.getItemAtPosition(position).toString()
                recyclerView.scrollToPosition(0)

                // Determine the URL to use based on the selected component type
                val url = when (selectedComponentType) {
                    "CPU" -> "product.list.php?catalog=micro&category=wpr"
                    "GPU" -> "product.list.php?catalog=micro&category=wgfx_pcie"
                    "PSU" -> "product.list.php?catalog=micro&category=w_ali"
                    "Case" -> "product.list.php?catalog=micro&category=w_boi_sa"
                    "RAM" -> "product.list.php?catalog=micro&category=wme_ddr4"
                    "Motherboard" -> "product.list.php?catalog=micro&category=w_cm_1700"
                    "Storage" -> "product.list.php?catalog=micro&category=w_ssd"
                    "Cooling" -> "product.list.php?catalog=micro&category=w_ven"
                    else -> throw IllegalArgumentException("Unknown component type: $selectedComponentType")
                }

                // Call the API to get the components of the selected type
                val call = ApiClient.service.getComponents(url)

                call.enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            val apiResponse = response.body()
                            val components = apiResponse?.result?.product_list?.document.orEmpty()
                            adapter.updateComponents(components)
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
                        println("erreur lors de la récupération des composants")
                    }
                })
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle the case where no component type is selected
                // select the first item by default
                parent.setSelection(0)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}