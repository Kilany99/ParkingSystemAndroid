package com.example.parkingsystemandroid.data.model.dto

import com.example.parkingsystemandroid.data.model.Enums

data class ProcessPaymentDto(
    val reservationId: Int,
    val amount: Double,
    val paymentMethod: Enums.PaymentMethod

)