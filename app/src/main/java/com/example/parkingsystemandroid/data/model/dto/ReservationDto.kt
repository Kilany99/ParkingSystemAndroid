package com.example.parkingsystemandroid.data.model.dto

import com.example.parkingsystemandroid.data.model.Enums
import java.time.LocalDateTime

data class ReservationDto(
    val id: Int,
    val createdAt: LocalDateTime,
    val entryTime: LocalDateTime?,
    val exitTime: LocalDateTime?,
    val totalAmount: Double,
    val qrCode: String,
    val status: Enums.SessionStatus,
    val car: CarDto,
    val parkingSpotDto: ParkingSpotDto


)