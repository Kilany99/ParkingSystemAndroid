package com.example.parkingsystemandroid.ui.car

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkingsystemandroid.data.model.Car
import com.example.parkingsystemandroid.data.model.dto.CarDto
import com.example.parkingsystemandroid.databinding.FragmentCarListBinding
import com.example.parkingsystemandroid.ui.car.adapter.CarAdapter
import com.example.parkingsystemandroid.viewmodel.CarViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CarListFragment : Fragment() {

    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CarViewModel
    private lateinit var carAdapter: CarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()

        viewModel.loadCars()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(CarViewModel::class.java)
    }

    private fun setupRecyclerView() {
        carAdapter = CarAdapter(
            onItemClick = { car ->
                // Navigate to edit car
                findNavController().navigate(
                    CarListFragmentDirections.actionCarListToCarForm(car)
                )
            },
            onDeleteClick = { car: CarDto ->
                showDeleteConfirmationDialog(car)
            }
        )

        binding.recyclerViewCars.apply {
            adapter = carAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupClickListeners() {
        binding.fabAddCar.setOnClickListener {
            findNavController().navigate(
                CarListFragmentDirections.actionCarListToCarForm(null)
            )
        }
    }

    private fun observeViewModel() {
        viewModel.cars.observe(viewLifecycleOwner) { cars ->
            carAdapter.submitList(cars)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showDeleteConfirmationDialog(car: CarDto) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Car")
            .setMessage("Are you sure you want to delete this car?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteCar(car.id)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}