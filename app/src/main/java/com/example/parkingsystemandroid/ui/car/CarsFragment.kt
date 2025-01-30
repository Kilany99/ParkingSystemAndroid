package com.example.parkingsystemandroid.ui.car


import CarRepository
import CarViewModel
import CarViewModelFactory

import android.app.AlertDialog
import android.os.Build

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingsystemandroid.R
import com.example.parkingsystemandroid.data.model.Car
import com.example.parkingsystemandroid.data.model.dto.CarDto
import com.example.parkingsystemandroid.data.model.dto.UpdateCarDto
import com.example.parkingsystemandroid.data.network.RetrofitInstance

import com.google.android.material.snackbar.Snackbar
import com.example.parkingsystemandroid.data.repository.Result
import com.example.parkingsystemandroid.databinding.DialogAddCarBinding
import com.example.parkingsystemandroid.databinding.FragmentCarsBinding

class CarsFragment : Fragment() {
    private var _binding: FragmentCarsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CarViewModel
    private lateinit var adapter: CarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = CarRepository(RetrofitInstance.api)
        val factory = CarViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CarViewModel::class.java]
        setupRecyclerView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupObservers()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupClickListeners()
        }
    }

    private fun setupRecyclerView() {
        adapter = CarAdapter(
            onEdit = { showEditCarDialog(it) },
            onDelete = { showDeleteConfirmation(it) }
        )

        binding.carsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CarsFragment.adapter
        }
    }


    private fun setupObservers() {
        viewModel.cars.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(result.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showError(result.message)
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.fabAddCar.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showAddCarDialog()
            }
        }
    }


    private fun showAddCarDialog() {
        val dialogBinding = DialogAddCarBinding.inflate(layoutInflater)

        AlertDialog.Builder(requireContext())
            .setTitle("Add New Car")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                val plate = dialogBinding.etPlateNumber.editText?.text.toString()
                val model = dialogBinding.etModel.editText?.text.toString()
                val color = dialogBinding.etColor.editText?.text.toString()

                if (validateInput(plate, model, color)) {
                    viewModel.addCar(
                            plateNumber = plate,
                            model = model,
                            color = color
                        )

                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    private fun showEditCarDialog(car: Car) {
        val dialogBinding = DialogAddCarBinding.inflate(layoutInflater)

        with(dialogBinding) {
            etPlateNumber.editText?.setText(car.plateNumber)
            etModel.editText?.setText(car.model)
            etColor.editText?.setText(car.color)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Car")
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                val model = dialogBinding.etModel.editText?.text.toString()
                val color = dialogBinding.etColor.editText?.text.toString()

                viewModel.updateCar(
                        car.id,
                        model = model,
                        color = color
                )
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    private fun showDeleteConfirmation(car: Car) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Car")
            .setMessage("Delete ${car.plateNumber}?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteCar(car.id)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun validateInput(plate: String, model: String, color: String): Boolean {
        return when {
            plate.isBlank() -> {
                showError("License plate required")
                false
            }
            model.isBlank() -> {
                showError("Model required")
                false
            }
            color.isBlank() -> {
                showError("Color required")
                false
            }
            else -> true
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

}