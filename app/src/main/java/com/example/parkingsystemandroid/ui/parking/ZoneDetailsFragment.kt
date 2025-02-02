package com.example.parkingsystemandroid.ui.parking

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.parkingsystemandroid.R
import com.example.parkingsystemandroid.data.model.dto.ParkingZoneStatusDto
import com.example.parkingsystemandroid.databinding.FragmentZoneDetailsBinding
import com.example.parkingsystemandroid.viewmodel.ParkingZoneViewModel
import com.example.parkingsystemandroid.data.repository.Result
import com.google.android.material.snackbar.Snackbar

class ZoneDetailsFragment : Fragment() {
    private var _binding: FragmentZoneDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ZoneDetailsFragmentArgs by navArgs()
    private val viewModel: ParkingZoneViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val zoneId = args.zoneId

        setupObservers()
        viewModel.getZoneStatus(zoneId)
    }

    private fun setupObservers() {
        viewModel.zoneStatus.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoading()
                is Result.Success -> {
                    hideLoading()
                    displayZoneStatus(result.data)
                }
                is Result.Error -> {
                    hideLoading()
                    showError(result.message)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayZoneStatus(status: ParkingZoneStatusDto) {
        with(binding) {
            zoneNameTextView.text = status.zoneName
            totalSpotsTextView.text = "Total spots: ${status.totalSpots}"
            availableSpotsTextView.text = "Available: ${status.availableSpots}"
            rateTextView.text = "Hourly rate: $${status.hourlyRate}"

            // Update UI based on isFull status
            statusIndicator.setBackgroundColor(
                if (status.isFull) ContextCompat.getColor(requireContext(), R.color.red)
                else ContextCompat.getColor(requireContext(), R.color.green)
            )

            distributionTextView.text = buildString {
                append("Available by floor:\n")
                status.distribution.availableByFloor.forEach { (floor, spots) ->
                    append("Floor $floor: $spots spots\n")
                }
            }
        }
    }

    private fun showLoading() {
        // Show progress bar
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}