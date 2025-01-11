package com.example.parkingsystemandroid.data.model.dto

data class ParkingZoneDto (
    val id:Int,
    val name:String,
    val totalFloors:Int,
    val spotsPerFloor: Int,
    val isFull:Boolean,
    val hourlyRate: Double


)