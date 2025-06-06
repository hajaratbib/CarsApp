package com.example.carsapp.domain.repository

import com.example.carsapp.data.dto.CarDto

interface CarRepository {
    suspend fun getCars(make: String): List<CarDto>
}