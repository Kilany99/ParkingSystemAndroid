package com.example.parkingsystemandroid.data.model.dto

import com.example.parkingsystemandroid.data.model.Enums

data class ParkingSpotDto (
    val id:Int,
    val spotNumber:Int,
    val floor:Int,
    val spotStatus: Enums.SpotStatus,
    val spotType: Enums.SpotType,
    val reservation:ReservationDto,
    val parkingZone: ParkingZoneDto,
)