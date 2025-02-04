package com.example.parkingsystemandroid.data.model.dto

import com.example.parkingsystemandroid.data.model.Enums
import java.time.LocalDateTime

data class ReservationDto(
    val id: Int,
    val createdAt: String,
    val entryTime: String?,
    val exitTime: String?,
    val totalAmount: Double,
    val qrCode: String,
    val isPaid: Boolean,
    val status: Enums.SessionStatus? = Enums.SessionStatus.RESERVED,
    val car: CarDto,
    val parkingSpot: ParkingSpotDto,
    val parkingZone: ParkingZoneDto


)