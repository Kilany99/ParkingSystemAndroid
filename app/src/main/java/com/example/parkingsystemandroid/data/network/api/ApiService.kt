package com.example.parkingsystemandroid.data.network.api

import com.example.parkingsystemandroid.data.model.*
import com.example.parkingsystemandroid.data.model.dto.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ----- AuthController -----

    @POST("Auth/register")
    suspend fun register(@Body registerDto: RegisterDto): Response<AuthResponseDto>

    @POST("Auth/login")
    suspend fun login(@Body loginDto: LoginDto): Response<AuthResponseDto>

    @POST("Auth/forgot-password")
    suspend fun forgotPassword(@Body forgotPasswordDto: ForgotPasswordDto): Response<ApiResponse>

    @POST("Auth/reset-password")
    suspend fun resetPassword(@Body resetPasswordDto: ResetPasswordDto): Response<ApiResponse>

    // ----- UserController -----

    @GET("User/me")
    suspend fun getCurrentUser(): Response<User>
    @PUT("User/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body updateUserDto: UpdateUserDto): Response<UserDto>

    // ----- CarController -----

    @GET("Car")
    suspend fun getMyCars(): Response<List<CarDto>>

    @POST("Car")
    suspend fun addCar(@Body createCarDto: CreateCarDto): Response<CarDto>

    @PUT("Car/{id}")
    suspend fun updateCar(@Path("id") id: Int, @Body updateCarDto: UpdateCarDto): Response<CarDto>

    @DELETE("Car/{id}")
    suspend fun deleteCar(@Path("id") id: Int): Response<Void>

    // ----- ParkingZoneController -----

    @GET("ParkingZone")
    suspend fun getAllZones(): Response<List<ParkingZoneDto>>
    @GET("ParkingZone/{id}/status")
    suspend fun getZoneStatus(): Response<ParkingZoneDto>
    @GET("ParkingZone/{id}/available-spots")
    suspend fun getAvailableSpots(@Path("id") zoneId: Int): Response<List<ParkingSpotDto>>

    // ----- ReservationController -----

    @POST("Reservation")
    suspend fun createReservation(@Body createReservationDto: CreateReservationDto): Response<ReservationDto>

    @GET("Reservation/me")
    suspend fun getMyReservations(): Response<List<ReservationDto>>

    @POST("Reservation/{id}/cancel")
    suspend fun cancelReservation(@Path("id") reservationId: Int): Response<Void>

    @GET("Reservation/{id}/fee")
    suspend fun getReservationFee(@Path("id") reservationId: Int): Response<Double>

    // ----- PaymentController -----

    @POST("Payment")
    suspend fun processPayment(@Body createPaymentDto: ProcessPaymentDto): Response<PaymentDto>

    @GET("Payment/{id}")
    suspend fun getPaymentDetails(@Path("id") paymentId: Int): Response<PaymentDto>

    @GET("Payment/my-payments")
    suspend fun getMyPayments(): Response<List<PaymentDto>>



}