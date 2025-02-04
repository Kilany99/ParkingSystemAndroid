package com.example.parkingsystemandroid.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.parkingsystemandroid.data.model.dto.CreateReservationDto
import com.example.parkingsystemandroid.viewmodel.ReservationViewModel
import com.example.parkingsystemandroid.data.repository.Result
import com.example.parkingsystemandroid.databinding.FragmentCreateReservationBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateReservationFragment : Fragment() {

    private var _binding: FragmentCreateReservationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReservationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupForm()
        setupObservers()
    }

    private fun setupForm() {
        binding.btnSubmit.setOnClickListener {
            val dto = CreateReservationDto(
                carId = binding.etCarId.text.toString().toIntOrNull() ?: 0,
                parkingSpotId = binding.etSpotId.text.toString().toIntOrNull() ?: 0,
                parkingZoneId = binding.etZoneId.text.toString().toIntOrNull() ?: 0
            )

            if (validateInput(dto)) {
                viewModel.createReservation(dto)
            }
        }
    }

    private fun validateInput(dto: CreateReservationDto): Boolean {
        return dto.carId > 0 && dto.parkingSpotId > 0 && dto.parkingZoneId > 0
    }

    private fun setupObservers() {
        viewModel.createResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoading()
                is Result.Success -> {
                    hideLoading()
                    findNavController().popBackStack()
                    Snackbar.make(binding.root, "Reservation created!", Snackbar.LENGTH_SHORT).show()
                }
                is Result.Error -> {
                    hideLoading()
                    showError(result.message)
                }
            }
        }
    }
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}