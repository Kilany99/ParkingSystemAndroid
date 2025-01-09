package com.example.parkingsystemandroid.data.model

data class Reservation (
    val id: Int,
    val userId: Int,
    val parkingSpotId: Int,
    val carId: Int,
    val createdAt: String,
    val entryTime: String?,
    val exitTime: String?,
    val qrCode: String,
    val status: Enums.SessionStatus,
    val user: User?,
    val car: Car?,
    val parkingSpot: ParkingSpot?,
    val payment: Payment?

)

