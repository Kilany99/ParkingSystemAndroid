package com.example.parkingsystemandroid.data.model

import java.time.LocalDateTime

data class Payment (
    val id: Int,
    val reservationId: Int,
    val amount: Double,
    val status: Enums.PaymentStatus,
    val method: Enums.PaymentMethod,
    val createdAt: LocalDateTime,
    //Navigation properties:
    val completedAt: LocalDateTime?,
    val reservation: Reservation?
)