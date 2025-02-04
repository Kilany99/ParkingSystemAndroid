package com.example.parkingsystemandroid.data.repository

import android.util.Log
import com.example.parkingsystemandroid.data.model.dto.CreateReservationDto
import com.example.parkingsystemandroid.data.model.dto.ReservationDto
import com.example.parkingsystemandroid.data.network.api.ApiService
import com.example.parkingsystemandroid.utils.TokenManager
import java.util.logging.Logger
import javax.inject.Inject

class ReservationRepository @Inject constructor(
    private val api: ApiService,
) {
    suspend fun createReservation(dto: CreateReservationDto): Result<ReservationDto> {
        return try {
            val response = api.createReservation(dto)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Reservations Loading failed: ${response.body().toString()}")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")

        }
    }

    suspend fun getMyReservations(): Result<List<ReservationDto>> {
        return try {
            val response = api.getMyReservations()
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Log.e("ReservationRepository", "Failed to load reservations: $errorMessage")
                Result.Error("Failed to load reservations: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("ReservationRepository", "Network error: ${e.message}")
            e.printStackTrace()
            Result.Error("Network error: ${e.message}")
        }
    }

    suspend fun cancelReservation(reservationId: Int): Result<Boolean> {
        return try {
            val response = api.cancelReservation(reservationId)
            if (response.isSuccessful) {
                Result.Success(true)
            } else {
                Result.Error("Cancel failed: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")
        }
    }

    suspend fun getReservationFee(reservationId: Int): Result<Double> {
        return try {
            val response = api.getReservationFee(reservationId)
            if (response.isSuccessful) {
                Result.Success(response.body() ?: 0.0)
            } else {
                Result.Error("Fee check failed: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")
        }
    }
}