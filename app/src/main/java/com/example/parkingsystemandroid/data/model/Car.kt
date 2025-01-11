package com.example.parkingsystemandroid.data.model

import java.time.LocalDateTime

data class Car (
    val id: Int,
    val userId: Int,
    val plateNumber: String,
    val model: String,
    val color: String,
    val createdAt: LocalDateTime

)
