package com.example.parkingsystemandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingsystemandroid.data.model.dto.CarDto
import com.example.parkingsystemandroid.data.model.dto.CreateCarDto
import com.example.parkingsystemandroid.data.model.dto.UpdateCarDto
import com.example.parkingsystemandroid.data.repository.CarRepository
import kotlinx.coroutines.launch

class CarViewModel : ViewModel() {
    private val carRepository = CarRepository()

    private val _cars = MutableLiveData<List<CarDto>>()
    val cars: LiveData<List<CarDto>> = _cars

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _operationSuccess = MutableLiveData<Boolean>()
    val operationSuccess: LiveData<Boolean> = _operationSuccess

    fun loadCars() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            carRepository.getMyCars()
                .onSuccess {
                    _cars.value = it
                }
                .onFailure {
                    _error.value = it.message
                }

            _loading.value = false
        }
    }

    fun addCar(plateNumber: String, model: String, color: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val createCarDto = CreateCarDto(plateNumber, model, color)
            carRepository.addCar(createCarDto)
                .onSuccess {
                    _operationSuccess.value = true
                    loadCars() // Refresh the car list
                }
                .onFailure {
                    _error.value = it.message
                    _operationSuccess.value = false
                }

            _loading.value = false
        }
    }

    fun updateCar(id: Int, plateNumber: String, model: String, color: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val updateCarDto = UpdateCarDto(model, color)
            carRepository.updateCar(id, updateCarDto)
                .onSuccess {
                    _operationSuccess.value = true
                    loadCars() // Refresh the car list
                }
                .onFailure {
                    _error.value = it.message
                    _operationSuccess.value = false
                }

            _loading.value = false
        }
    }

    fun deleteCar(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            carRepository.deleteCar(id)
                .onSuccess {
                    _operationSuccess.value = true
                    loadCars() // Refresh the car list
                }
                .onFailure {
                    _error.value = it.message
                    _operationSuccess.value = false
                }

            _loading.value = false
        }
    }
}