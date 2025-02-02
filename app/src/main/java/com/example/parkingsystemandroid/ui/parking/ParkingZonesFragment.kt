package com.example.parkingsystemandroid.ui.parking
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingsystemandroid.R
import com.example.parkingsystemandroid.data.model.dto.ParkingZoneDto
import com.example.parkingsystemandroid.databinding.FragmentParkingZonesBinding
import com.example.parkingsystemandroid.databinding.ItemParkingZoneBinding
import com.example.parkingsystemandroid.viewmodel.ParkingZoneViewModel
import com.example.parkingsystemandroid.data.repository.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParkingZonesFragment : Fragment() {

    private var _binding: FragmentParkingZonesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ParkingZoneViewModel by viewModels()
    private lateinit var adapter: ParkingZoneAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParkingZonesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupRefreshListener()
    }

    private fun setupRecyclerView() {
        adapter = ParkingZoneAdapter { zone ->
            // Create the navigation action with Safe Args
            val directions = ParkingZonesFragmentDirections
                .actionParkingZonesFragmentToZoneDetailsFragment(zoneId = zone.id)
            findNavController().navigate(directions)
        }
        binding.zonesRecyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.zones.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoading()
                is Result.Success -> {
                    hideLoading()
                    adapter.submitList(result.data)
                }
                is Result.Error -> showError(result.message)
            }
        }
    }

    private fun setupRefreshListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadZones()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.errorTextView.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.errorTextView.visibility = View.VISIBLE
        binding.errorTextView.text = message
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class ParkingZoneAdapter(
        private val onItemClick: (ParkingZoneDto) -> Unit
    ) : RecyclerView.Adapter<ParkingZoneAdapter.ViewHolder>() {

        private var zones = emptyList<ParkingZoneDto>()

        @SuppressLint("NotifyDataSetChanged")
        fun submitList(newList: List<ParkingZoneDto>) {
            zones = newList
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemParkingZoneBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(zones[position])
        }

        override fun getItemCount() = zones.size

        inner class ViewHolder(
            private val binding: ItemParkingZoneBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(zone: ParkingZoneDto) {
                binding.zoneNameTextView.text = zone.name
                binding.floorInfoTextView.text = "Floors: ${zone.totalFloors}"
                binding.spotsInfoTextView.text = "Spots/Floor: ${zone.spotsPerFloor}"
                binding.rateTextView.text = "Rate: $${zone.hourlyRate}/h"
                binding.statusTextView.text = if (zone.isFull) "FULL" else "AVAILABLE"
                binding.statusTextView.setTextColor(
                    if (zone.isFull) getColor(R.color.red) else getColor(R.color.green)
                )

                itemView.setOnClickListener { onItemClick(zone) }
            }

            private fun getColor(resId: Int): Int {
                return requireContext().getColor(resId)
            }
        }
    }
}