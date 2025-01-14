package com.example.parkingsystemandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
@Parcelize
data class Car (
    val id: Int,
    val userId: Int,
    val plateNumber: String,
    val model: String,
    val color: String,
    val createdAt: LocalDateTime

):Parcelable
