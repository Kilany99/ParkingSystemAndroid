package com.example.parkingsystemandroid.data.model

import java.time.LocalDateTime

data class Reservation (
    val id: Int,
    val userId: Int,
    val parkingSpotId: Int,
    val carId: Int,
    val createdAt: LocalDateTime,
    val entryTime: LocalDateTime?,
    val exitTime: LocalDateTime?,
    val qrCode: String,
    val status: Enums.SessionStatus,
    val user: User?,
    val car: Car?,
    val parkingSpot: ParkingSpot?,
    val payment: Payment?

)

