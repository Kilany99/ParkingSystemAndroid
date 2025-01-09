package com.example.parkingsystemandroid.data.model

data class User(
    val id: Int,
    val email: String,
    val name: String,
    val phone: String,
    val role: String,
    val createdAt: String
)