package com.example.parkingsystemandroid.data.model

data class Payment (
    val id: Int,
    val reservationId: Int,
    val amount: Double,
    val status: Enums.PaymentStatus,
    val method: Enums.PaymentMethod,
    val createdAt: String,
    //Navigation properties:
    val completedAt: String?,
    val reservation: Reservation?
)