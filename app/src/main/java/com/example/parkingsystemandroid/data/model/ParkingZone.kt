package com.example.parkingsystemandroid.data.model

data class ParkingZone(
    val id: Int,
    val name: String,
    val totalFloors: Int,
    val spotsPerFloor: Int,
    val hourlyRate: Double,
    val description: String,
    val isFull: Boolean,
    // Navigation properties:
    val parkingSpots :List<ParkingZone>,

)