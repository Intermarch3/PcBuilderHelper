package com.example.pcbuilderhelper.ui.dashboard

import BuildAdapter
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pcbuilderhelper.databinding.FragmentDashboardBinding
import com.example.pcbuilderhelper.models.BuildComponent

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        val recyclerView: RecyclerView = binding.recyclerViewBuilds
        val builds = mutableListOf<BuildComponent>() // Créez une liste mutable pour pouvoir ajouter des builds
        lateinit var adapter: BuildAdapter
        adapter = BuildAdapter(builds) {
            // Lorsque l'utilisateur clique sur le bouton "Nouveau Build", affichez une boîte de dialogue pour saisir le nom du build
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Nouveau Build")

            val input = EditText(requireContext())
            builder.setView(input)

            builder.setPositiveButton("OK") { dialog, _ ->
                val buildName = input.text.toString()
                val newBuild = BuildComponent(buildName, "", "", 0.0) // Créez un nouvel objet BuildComponent avec le nom saisi
                builds.add(newBuild) // Ajoutez le nouveau build à la liste
                adapter.updateBuilds(builds) // Mettez à jour l'adaptateur avec la nouvelle liste de builds
                dialog.dismiss()
            }

            builder.setNegativeButton("Annuler") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}