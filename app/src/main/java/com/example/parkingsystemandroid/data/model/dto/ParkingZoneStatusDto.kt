package com.example.parkingsystemandroid.data.model.dto

data class ParkingZoneStatusDto (
    val zoneId:Int,
    val zoneName:String,
    val totalSpots:Int,
    val availableSpots:Int,
    val hourlyRate:Double,
    val isFull: Boolean,
    val distribution: ParkingSpotDistributionDto
)