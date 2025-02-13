package com.example.parkingsystemandroid.data.network.interceptor

import com.example.parkingsystemandroid.utils.TokenManager
import okhttp3.Interceptor

private val authInterceptor = Interceptor { chain ->
    val originalRequest = chain.request()
    val builder = originalRequest.newBuilder()

    // Add Authorization header with the token
    TokenManager.token?.let { token ->
        builder.header("Authorization", "Bearer $token")
    }



    val newRequest = builder.build()
    chain.proceed(newRequest)
}
