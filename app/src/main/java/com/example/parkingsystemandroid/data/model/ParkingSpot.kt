package com.example.parkingsystemandroid.data.model

data class ParkingSpot (
    val id: Int,
    val spotNumber: String,
    val status: Enums.SpotStatus,
    val type: Enums.SpotType,
    val floor: Int,
    val reservationId: Int,
    val parkingZoneId: Int,
    // Navigation properties:
    val parkingZone :ParkingZone,
    val currentReservation: Reservation,
    val reservations: List<Reservation>
)