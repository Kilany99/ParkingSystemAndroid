package com.example.parkingsystemandroid.data.model.dto

import java.util.Dictionary

data class ParkingSpotDistributionDto (
    val available:Int,
    val occupied:Int,
    val reserved:Int,
    val maintenance:Int,
    val availableByFloor:Map<Int,Int>
)
