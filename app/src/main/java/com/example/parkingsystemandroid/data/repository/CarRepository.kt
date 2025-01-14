package com.example.parkingsystemandroid.data.repository

import com.example.parkingsystemandroid.data.model.Car
import com.example.parkingsystemandroid.data.model.dto.CarDto
import com.example.parkingsystemandroid.data.model.dto.CreateCarDto
import com.example.parkingsystemandroid.data.model.dto.UpdateCarDto
import com.example.parkingsystemandroid.data.network.RetrofitInstance


class CarRepository {
    private val api = RetrofitInstance.api

    suspend fun getMyCars(): Result<List<CarDto>> = try {
        val response = api.getMyCars()
        if (response.isSuccessful) {
            Result.success(response.body() ?: emptyList())
        } else {
            Result.failure(Exception("Failed to fetch cars"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun addCar(createCarDto: CreateCarDto): Result<CarDto> = try {
        val response = api.addCar(createCarDto)
        if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception("Failed to add car"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun updateCar(id: Int, updateCarDto: UpdateCarDto): Result<CarDto> = try {
        val response = api.updateCar(id, updateCarDto)
        if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception("Failed to update car"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun deleteCar(id: Int): Result<Unit> = try {
        val response = api.deleteCar(id)
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Failed to delete car"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}