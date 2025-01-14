package com.example.parkingsystemandroid.ui.car.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parkingsystemandroid.data.model.Car
import com.example.parkingsystemandroid.data.model.dto.CarDto
import com.example.parkingsystemandroid.databinding.ItemCarBinding


class CarAdapter(
    private val onItemClick: (CarDto) -> Unit,
    private val onDeleteClick: (CarDto) -> Unit
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    private var cars: List<CarDto> = emptyList()

    fun submitList(newList: List<CarDto>) {
        cars = newList
        notifyDataSetChanged()
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
        holder.bind(cars[position])
    }

    override fun getItemCount(): Int = cars.size

    inner class CarViewHolder(
        private val binding: ItemCarBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(car: CarDto) {
            binding.apply {
                textPlateNumber.text = car.plateNumber
                textModel.text = car.model
                textColor.text = car.color

                root.setOnClickListener { onItemClick(car) }
                buttonDelete.setOnClickListener { onDeleteClick(car) }
            }
        }
    }
}