package com.example.carsapp.data.dto

import com.example.carsapp.domain.model.Car
import com.google.gson.annotations.SerializedName

data class CarDto(
    @SerializedName("model_name") val model: String,
    @SerializedName("model_make_id") val brand: String
)

fun CarDto.toCar(): Car {
    return Car(
        model = model,
        brand = brand
    )
}