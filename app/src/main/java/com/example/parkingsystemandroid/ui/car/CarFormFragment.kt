package com.example.parkingsystemandroid.ui.car

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.parkingsystemandroid.data.model.Car
import com.example.parkingsystemandroid.data.model.dto.CarDto
import com.example.parkingsystemandroid.databinding.FragmentCarFormBinding
import com.example.parkingsystemandroid.viewmodel.CarViewModel


class CarFormFragment : Fragment() {

    private var _binding: FragmentCarFormBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CarViewModel
    private val args: CarFormFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupViews()
        observeViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(CarViewModel::class.java)
    }

    private fun setupViews() {
        // If editing existing car, populate fields
        args.car?.let { car:CarDto ->
            binding.apply {
                editTextPlateNumber.setText(car.plateNumber)
                editTextModel.setText(car.model)
                editTextColor.setText(car.color)
            }
        }

        binding.buttonSave.setOnClickListener {
            val plateNumber = binding.editTextPlateNumber.text.toString()
            val model = binding.editTextModel.text.toString()
            val color = binding.editTextColor.text.toString()

            if (validateInputs()) {
                if (args.car != null) {
                    viewModel.updateCar(args.car!!.id, plateNumber, model, color)
                } else {
                    viewModel.addCar(plateNumber, model, color)
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        binding.apply {
            if (editTextPlateNumber.text.isNullOrBlank()) {
                editTextPlateNumber.error = "Please enter plate number"
                isValid = false
            }
            if (editTextModel.text.isNullOrBlank()) {
                editTextModel.error = "Please enter model"
                isValid = false
            }
            if (editTextColor.text.isNullOrBlank()) {
                editTextColor.error = "Please enter color"
                isValid = false
            }
        }

        return isValid
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.buttonSave.isEnabled = !isLoading
            // You might want to add a progress indicator
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.operationSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}