package com.example.parkingsystemandroid.viewmodel

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parkingsystemandroid.data.model.dto.ParkingSpotDto
import com.example.parkingsystemandroid.data.model.dto.ParkingZoneDto
import com.example.parkingsystemandroid.data.model.dto.ParkingZoneStatusDto
import com.example.parkingsystemandroid.data.repository.ParkingZoneRepository
import com.example.parkingsystemandroid.data.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkingZoneViewModel @Inject constructor(
    private val repository: ParkingZoneRepository,
):ViewModel() {

    private val _zones = MutableLiveData<Result<List<ParkingZoneDto>>>()
    val zones: LiveData<Result<List<ParkingZoneDto>>> = _zones



    private val _zoneStatus = MutableLiveData<Result<ParkingZoneStatusDto>>()
    val zoneStatus: LiveData<Result<ParkingZoneStatusDto>> = _zoneStatus

    private val _spots = MutableLiveData<Result<List<ParkingSpotDto>>> ()
    val spots :LiveData<Result<List<ParkingSpotDto>>> = _spots

    init {
            loadZones()
    }

    fun loadZones() {
        viewModelScope.launch {
            _zones.value = Result.Loading
            _zones.value = repository.getAllZones()
        }
    }


    fun getZoneStatus(zoneId: Int) {
        viewModelScope.launch {
            _zoneStatus.value = Result.Loading
            _zoneStatus.value = repository.getZoneStatus(zoneId)
        }
    }


    fun getAvailableSpots(zoneId: Int){
        viewModelScope.launch {
            _spots.value = Result.Loading
            _spots.value = repository.getAvailableSpots(zoneId)
        }
    }
}
