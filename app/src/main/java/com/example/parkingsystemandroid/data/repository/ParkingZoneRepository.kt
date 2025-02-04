package com.example.parkingsystemandroid.data.repository

import android.util.Log
import com.example.parkingsystemandroid.data.model.Enums
import com.example.parkingsystemandroid.data.model.ParkingZone
import com.example.parkingsystemandroid.data.model.dto.ParkingSpotDto
import com.example.parkingsystemandroid.data.model.dto.ParkingZoneDto
import com.example.parkingsystemandroid.data.model.dto.ParkingZoneStatusDto
import com.example.parkingsystemandroid.data.network.api.ApiService
import com.example.parkingsystemandroid.utils.TokenManager
import java.time.LocalDateTime
import javax.inject.Inject

class ParkingZoneRepository@Inject constructor( private val api: ApiService) {


    suspend fun getAllZones(): Result<List<ParkingZoneDto>> {
        return try {
            val response = api.getAllZones()
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error("Failed to load Parking zones: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")
        }
    }

    suspend fun getZoneStatus(zoneId:Int): Result<ParkingZoneStatusDto>{
        return try {
            val response = api.getZoneStatus(zoneId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to load Parking zone status: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")
        }
    }
    suspend fun getAvailableSpots(zoneId: Int): Result<List<ParkingSpotDto>> {
        return try {
            val response = api.getAvailableSpots(zoneId, Enums.SpotStatus.AVAILABLE)
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error("Failed to load available spots: ${response.code()}")
            }
        }catch (e:Exception){
            Result.Error("Network error: ${e.message}")
        }
    }

    }