package com.example.parkingsystemandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingsystemandroid.data.model.dto.CreateReservationDto
import com.example.parkingsystemandroid.data.model.dto.ReservationDto
import com.example.parkingsystemandroid.data.repository.ReservationRepository
import com.example.parkingsystemandroid.data.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val repository: ReservationRepository
) : ViewModel() {

    private val _reservations = MutableLiveData<Result<List<ReservationDto>>>()
    val reservations: LiveData<Result<List<ReservationDto>>> = _reservations

    private val _createResult = MutableLiveData<Result<ReservationDto>>()
    val createResult: LiveData<Result<ReservationDto>> = _createResult

    private val _cancelResult = MutableLiveData<Result<Boolean>>()
    val cancelResult: LiveData<Result<Boolean>> = _cancelResult

    private val _feeResult = MutableLiveData<Result<Double>>()
    val feeResult: LiveData<Result<Double>> = _feeResult

    fun loadReservations() {
        viewModelScope.launch {
            _reservations.value = Result.Loading
            _reservations.value = repository.getMyReservations()
        }
    }

    fun createReservation(dto: CreateReservationDto) {
        viewModelScope.launch {
            _createResult.value = Result.Loading
            _createResult.value = repository.createReservation(dto)
        }
    }

    fun cancelReservation(reservationId: Int) {
        viewModelScope.launch {
            _cancelResult.value = Result.Loading
            _cancelResult.value = repository.cancelReservation(reservationId)
        }
    }
    fun getReservationFee(reservationId: Int) {
        viewModelScope.launch {
            _feeResult.value = Result.Loading
            _feeResult.value = repository.getReservationFee(reservationId)
        }

    }
}