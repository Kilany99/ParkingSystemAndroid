package com.example.parkingsystemandroid.data.model.dto

data class ResetPasswordDto(
    val resetToken: String,
    val newPassword: String
)