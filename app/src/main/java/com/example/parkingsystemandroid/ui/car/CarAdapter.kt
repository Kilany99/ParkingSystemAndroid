package com.example.parkingsystemandroid.ui.car

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingsystemandroid.data.model.Car
import com.example.parkingsystemandroid.databinding.ItemCarBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class CarAdapter(
    private val onEdit: (Car) -> Unit,
    private val onDelete: (Car) -> Unit
) : ListAdapter<Car, CarAdapter.CarViewHolder>(DIFF_CALLBACK) {

    inner class CarViewHolder(private val binding: ItemCarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(car: Car) {
            binding.apply {
                tvPlateNumber.text = car.plateNumber
                tvModel.text = car.model
                tvColor.text = car.color
                tvCreatedAt.text = formatDate(car.createdAt)

                btnEdit.setOnClickListener { onEdit(car) }
                btnDelete.setOnClickListener { onDelete(car) }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        private fun formatDate(date: LocalDateTime): String {
            return DateTimeFormatter
                .ofPattern("dd MMM yyyy", Locale.getDefault())
                .format(date)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Car>() {
            override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.bind(getItem(position))
        }
    }
}