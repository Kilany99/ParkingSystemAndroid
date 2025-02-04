package com.example.parkingsystemandroid.ui.reservation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingsystemandroid.R
import com.example.parkingsystemandroid.data.model.dto.ReservationDto
import com.google.android.material.button.MaterialButton

class ReservationAdapter(
    private val onCancelClick: (Int) -> Unit,
    private val onFeeClick: (Int) -> Unit
) : ListAdapter<ReservationDto, ReservationAdapter.ReservationViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReservationDto>() {
            override fun areItemsTheSame(oldItem: ReservationDto, newItem: ReservationDto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ReservationDto, newItem: ReservationDto): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSpotInfo: TextView = itemView.findViewById(R.id.tvSpotInfo)
        private val tvExtraDetails: TextView = itemView.findViewById(R.id.tvExtraDetails)
        private val tvTimeRange: TextView = itemView.findViewById(R.id.tvTimeRange)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val btnCancel: MaterialButton = itemView.findViewById(R.id.btnCancel)
        private val btnViewFee: MaterialButton = itemView.findViewById(R.id.btnViewFee)

        @SuppressLint("SetTextI18n", "DefaultLocale")
        fun bind(reservation: ReservationDto) {

            // Format entry and exit time
            val entryTime = reservation.entryTime ?: "Not set"
            val exitTime = reservation.exitTime ?: "Not set"
            tvTimeRange.text = "Entry: $entryTime\nExit: $exitTime"

            // Display reservation status
            tvStatus.text = "Status: ${reservation.status?.name ?: "Unknown"}"

            // Show payment status
            val paymentStatus = if (reservation.isPaid) "Paid" else "Pending"

            // Display total amount with currency
            val formattedAmount = String.format("%.2f", reservation.totalAmount)

            // Car details
            val carInfo = "Car: ${reservation.car.plateNumber} (${reservation.car.model})"

            // Parking zone details
            val zoneInfo =
                "Zone: ${reservation.parkingZone.name} (ID: ${reservation.parkingZone.id})"

            // Display all details
            tvExtraDetails.text = """
        $carInfo
        $zoneInfo
        Payment: $paymentStatus ($$formattedAmount)
    """.trimIndent()

            // Button Click Listeners
            btnCancel.setOnClickListener { onCancelClick(reservation.id) }
            btnViewFee.setOnClickListener { onFeeClick(reservation.id) }
        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reservation_item, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
