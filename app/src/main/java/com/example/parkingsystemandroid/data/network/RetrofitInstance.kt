package com.example.parkingsystemandroid.data.network




import com.example.parkingsystemandroid.data.network.api.ApiService
import com.google.gson.GsonBuilder
import com.example.parkingsystemandroid.utils.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.Response

object RetrofitInstance {

    private const val BASE_URL = "https://192.168.43.144:45455/api/"

    // Logging interceptor for debugging purposes
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Authentication interceptor to add the token to every request
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

    // OkHttpClient with the interceptors
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    // Gson instance with date format handling
    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create()

    // Retrofit instance
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}