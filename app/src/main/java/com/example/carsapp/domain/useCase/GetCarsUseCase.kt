package com.example.carsapp.domain.useCase

import com.example.carsapp.common.Resource
import com.example.carsapp.data.dto.toCar
import com.example.carsapp.domain.model.Car
import com.example.carsapp.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCarsUseCase @Inject constructor(
    private val repository: CarRepository
) {
    operator fun invoke(make: String): Flow<Resource<List<Car>>> = flow {
        try {
            emit(Resource.Loading())
            val cars = repository.getCars(make).map { it.toCar() }
            emit(Resource.Success(data = cars))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}