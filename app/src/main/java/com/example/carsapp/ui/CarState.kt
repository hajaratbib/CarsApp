package com.example.carsapp.ui

import com.example.carsapp.domain.model.Car

data class CarState(
    val isLoading: Boolean = false,
    val cars: List<Car> = emptyList(),
    val error: String? = null
)