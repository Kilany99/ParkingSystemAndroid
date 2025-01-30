import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parkingsystemandroid.data.model.Car
import com.example.parkingsystemandroid.data.model.dto.CarDto
import com.example.parkingsystemandroid.data.model.dto.CreateCarDto
import com.example.parkingsystemandroid.data.model.dto.UpdateCarDto
import com.example.parkingsystemandroid.data.repository.Result
import kotlinx.coroutines.launch


class CarViewModel(private val repository: CarRepository) : ViewModel() {

    private val _cars = MutableLiveData<Result<List<Car>>>()
    val cars: LiveData<Result<List<Car>>> = _cars

    private val _operationResult = MutableLiveData<Result<*>>()
    val operationResult: LiveData<Result<*>> = _operationResult

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            loadCars()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadCars() {
        viewModelScope.launch {
            _cars.value = Result.Loading
            _cars.value = repository.getMyCars()
        }
    }

    fun addCar(plateNumber: String, model: String, color: String) {
        viewModelScope.launch {
            _operationResult.value = Result.Loading
            val result = repository.addCar(
                CreateCarDto(
                    plateNumber = plateNumber,
                    model = model,
                    color = color
                )
            )
            _operationResult.value = result
            if (result is Result.Success) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                loadCars()
            }
        }
    }

    fun updateCar(carId: Int, model: String, color: String) {
        viewModelScope.launch {
            _operationResult.value = Result.Loading
            val result = repository.updateCar(carId, UpdateCarDto(model, color))
            _operationResult.value = result
            if (result is Result.Success) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                loadCars()
            }
        }
    }

    fun deleteCar(carId: Int) {
        viewModelScope.launch {
            _operationResult.value = Result.Loading
            val result = repository.deleteCar(carId)
            _operationResult.value = result
            if (result is Result.Success) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                loadCars()
            }
        }
    }
}
class CarViewModelFactory(
    private val repository: CarRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

