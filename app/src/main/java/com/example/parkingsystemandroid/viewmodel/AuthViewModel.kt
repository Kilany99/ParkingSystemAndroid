package com.example.parkingsystemandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingsystemandroid.data.model.dto.LoginDto
import com.example.parkingsystemandroid.data.model.dto.RegisterDto
import com.example.parkingsystemandroid.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    val authResponse = MutableLiveData<AuthResponseState>()

    fun register(registerDto: RegisterDto) {
        viewModelScope.launch {
            try {
                val response = authRepository.register(registerDto)
                if (response != null) {
                    authResponse.postValue(AuthResponseState.Success(response))
                } else {
                    authResponse.postValue(AuthResponseState.Error("Registration failed"))
                }
            } catch (e: Exception) {
                authResponse.postValue(AuthResponseState.Error(e.message ?: "An error occurred"))
            }
        }
    }

    fun login(loginDto: LoginDto) {
        viewModelScope.launch {
            try {
                val response = authRepository.login(loginDto)
                if (response != null) {
                    authResponse.postValue(AuthResponseState.Success(response))
                } else {
                    authResponse.postValue(AuthResponseState.Error("Login failed"))
                }
            } catch (e: Exception) {
                authResponse.postValue(AuthResponseState.Error(e.message ?: "An error occurred"))
            }
        }
    }
}

sealed class AuthResponseState {
    data class Success(val data: Any) : AuthResponseState()
    data class Error(val message: String) : AuthResponseState()
}