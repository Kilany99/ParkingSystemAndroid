package com.example.parkingsystemandroid.data.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarDto (
    val id: Int,
    val plateNumber: String,
    val model: String,
    val color: String,
):Parcelable

