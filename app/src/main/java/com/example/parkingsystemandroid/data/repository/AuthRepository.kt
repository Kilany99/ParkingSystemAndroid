package com.example.parkingsystemandroid.data.repository

import com.example.parkingsystemandroid.data.model.dto.AuthResponseDto
import com.example.parkingsystemandroid.data.model.dto.ForgotPasswordDto
import com.example.parkingsystemandroid.data.model.dto.LoginDto
import com.example.parkingsystemandroid.data.model.dto.RegisterDto
import com.example.parkingsystemandroid.data.model.dto.ResetPasswordDto
import com.example.parkingsystemandroid.data.network.RetrofitInstance
import com.example.parkingsystemandroid.utils.TokenManager


class AuthRepository {

    private val api = RetrofitInstance.api

    suspend fun register(registerDto: RegisterDto): AuthResponseDto? {
        val response = api.register(registerDto)
        return if (response.isSuccessful) {
            response.body()?.also {
                // Save token
                TokenManager.token = it.token
            }
        } else {
            throw (Exception("Registration failed with code: ${response.code()}"))
        }
    }

    suspend fun login(loginDto: LoginDto): AuthResponseDto? {
        val response = api.login(loginDto)
        return if (response.isSuccessful) {
            response.body()?.also {
                // Save token
                TokenManager.token = it.token
            }
        } else {
            throw (Exception("Login failed with code: ${response.code()}"))
        }


    }


    fun logout() {
        TokenManager.clearToken()
    }

    suspend fun forgotPassword(email: String): String? {
        val forgotPasswordDto = ForgotPasswordDto(email)
        val response = api.forgotPassword(forgotPasswordDto)
        return if (response.isSuccessful) {
            response.body()?.message
        } else {
            // Handle error (e.g., extract error message)
            response.errorBody()?.string()
        }
    }

    suspend fun resetPassword( resetToken: String, newPassword: String): String? {
        val resetPasswordDto = ResetPasswordDto( resetToken, newPassword)
        val response = api.resetPassword(resetPasswordDto)
        return if (response.isSuccessful) {
            response.body()?.message
        } else {
            response.errorBody()?.string()
        }
    }
}