package com.example.carsapp.data.repository

import com.example.carsapp.data.CarService
import com.example.carsapp.data.dto.CarDto
import com.example.carsapp.domain.repository.CarRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarRepoImpl @Inject constructor(
    private val carService: CarService
) : CarRepository {

    override suspend fun getCars(make: String): List<CarDto> {
        return carService.getCars(make = make).models
    }
}