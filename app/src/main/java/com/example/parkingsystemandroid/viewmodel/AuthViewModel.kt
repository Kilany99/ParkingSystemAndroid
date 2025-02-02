package com.example.parkingsystemandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingsystemandroid.data.model.dto.LoginDto
import com.example.parkingsystemandroid.data.model.dto.RegisterDto
import com.example.parkingsystemandroid.data.repository.AuthRepository
import com.example.parkingsystemandroid.utils.TokenManager
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    val forgotPasswordResponse = MutableLiveData<ResponseState>()
    val resetPasswordResponse = MutableLiveData<ResponseState>()

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
                    //save user name and email to be used across the app during login session
                    TokenManager.saveUserName(response.name)
                    TokenManager.saveUserEmail(response.email)
                } else {
                    authResponse.postValue(AuthResponseState.Error("Login failed"))
                }
            } catch (e: Exception) {
                authResponse.postValue(AuthResponseState.Error(e.message ?: "An error occurred"))
            }
        }
    }




    fun resetPassword(resetToken: String, newPassword: String) {
        viewModelScope.launch {
            try {
                val message = authRepository.resetPassword(resetToken, newPassword)
                if (message != null) {
                    resetPasswordResponse.postValue(ResponseState.Success(message))
                } else {
                    resetPasswordResponse.postValue(ResponseState.Error("An error occurred"))
                }
            } catch (e: Exception) {
                resetPasswordResponse.postValue(ResponseState.Error(e.message ?: "An error occurred"))
            }
        }
    }


    fun forgotPassword(email: String) {
        viewModelScope.launch {
            try {
                val message = authRepository.forgotPassword(email)
                if (message != null) {
                    forgotPasswordResponse.postValue(ResponseState.Success(message))
                } else {
                    forgotPasswordResponse.postValue(ResponseState.Error("An error occurred"))
                }
            } catch (e: Exception) {
                forgotPasswordResponse.postValue(ResponseState.Error(e.message ?: "An error occurred"))
            }
        }
    }



}

sealed class AuthResponseState {
    data class Success(val data: Any) : AuthResponseState()
    data class Error(val message: String) : AuthResponseState()
}
sealed class ResponseState {
    data class Success(val message: String) : ResponseState()
    data class Error(val message: String) : ResponseState()
}