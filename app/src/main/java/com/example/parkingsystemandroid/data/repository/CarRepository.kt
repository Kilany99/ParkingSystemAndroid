import android.os.Build
import androidx.annotation.RequiresApi
import com.example.parkingsystemandroid.data.model.Car
import com.example.parkingsystemandroid.data.model.dto.CarDto
import com.example.parkingsystemandroid.data.model.dto.CreateCarDto
import com.example.parkingsystemandroid.data.model.dto.UpdateCarDto
import com.example.parkingsystemandroid.data.network.api.ApiService
import com.example.parkingsystemandroid.data.repository.Result
import com.example.parkingsystemandroid.utils.TokenManager
import java.time.LocalDateTime
class CarRepository(private val api: ApiService) {

    @RequiresApi(Build.VERSION_CODES.O)
    private fun CarDto.toCar(): Car = Car(
        id = id,
        userId = TokenManager.getUserId(),
        plateNumber = plateNumber,
        model = model,
        color = color,
        createdAt = LocalDateTime.now() // Get from API if available
    )

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getMyCars(): Result<List<Car>> {
        return try {
            val response = api.getMyCars()
            if (response.isSuccessful) {
                Result.Success(response.body()?.map { it.toCar() } ?: emptyList())
            } else {
                Result.Error("Failed to load cars: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")
        }
    }

    suspend fun addCar(car: CreateCarDto): Result<CarDto> {
        return try {
            val response = api.addCar(car)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to add car: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")
        }
    }

    suspend fun updateCar(id: Int, updateCar: UpdateCarDto): Result<CarDto> {
        return try {
            val response = api.updateCar(id, updateCar)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Failed to update car: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")
        }
    }

    suspend fun deleteCar(id: Int): Result<Boolean> {
        return try {
            val response = api.deleteCar(id)
            if (response.isSuccessful) {
                Result.Success(true)
            } else {
                Result.Error("Failed to delete car: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}")
        }
    }
}