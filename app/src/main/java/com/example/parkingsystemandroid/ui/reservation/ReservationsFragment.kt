package com.example.parkingsystemandroid.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parkingsystemandroid.R
import com.example.parkingsystemandroid.data.repository.Result
import com.example.parkingsystemandroid.databinding.FragmentReservationsBinding
import com.example.parkingsystemandroid.viewmodel.ReservationViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationsFragment : Fragment() {

    private var _binding: FragmentReservationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReservationViewModel by viewModels()
    private lateinit var adapter: ReservationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupFab()
        // Trigger the initial load of reservations
        viewModel.loadReservations()
    }

    private fun setupRecyclerView() {
        adapter = ReservationAdapter(
            onCancelClick = { reservationId ->
                showCancelConfirmationDialog(reservationId)
            },
            onFeeClick = { reservationId ->
                viewModel.getReservationFee(reservationId)
            }
        )
        binding.rvReservations.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ReservationsFragment.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupObservers() {
        viewModel.reservations.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading()
                }
                is Result.Success -> {
                    hideLoading()
                    adapter.submitList(result.data)
                    handleEmptyState(result.data.isEmpty())
                }
                is Result.Error -> {
                    hideLoading()
                    showError(result.message)
                }
            }
        }

        viewModel.cancelResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    Snackbar.make(binding.root, "Reservation cancelled", Snackbar.LENGTH_SHORT).show()
                    // Reload the reservations after a successful cancel action
                    viewModel.loadReservations()
                }
                is Result.Error -> {
                    showError(result.message)
                }
                else -> {
                    // No-op for other cases (if any)
                }
            }
        }
    }

    private fun setupFab() {
        binding.fabNewReservation.setOnClickListener {
            findNavController().navigate(R.id.action_reservationsFragment_to_createReservationFragment)
        }
    }

    private fun showCancelConfirmationDialog(reservationId: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cancel Reservation")
            .setMessage("Are you sure you want to cancel this reservation?")
            .setPositiveButton("Confirm") { _, _ ->
                viewModel.cancelReservation(reservationId)
            }
            .setNegativeButton("Dismiss", null)
            .show()
    }

    // --- Helper Methods for Loading, Error, and Empty States ---

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvReservations.visibility = View.GONE
        binding.tvEmpty.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        // The RecyclerView visibility will be adjusted by the empty state helper below.
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun handleEmptyState(isEmpty: Boolean) {
        if (isEmpty) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvReservations.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.rvReservations.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
