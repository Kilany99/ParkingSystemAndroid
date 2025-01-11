package com.example.parkingsystemandroid.data.model.dto

import com.example.parkingsystemandroid.data.model.Enums
import java.time.LocalDateTime

class PaymentDto(
    val id: Int,
    val amount: Double,
    val status: Enums.PaymentStatus,
    val paymentMethod: Enums.PaymentMethod,
    val createdAt: LocalDateTime,
    val completedAt: LocalDateTime?
)