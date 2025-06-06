package com.example.carsapp.data.dto

import com.google.gson.annotations.SerializedName

data class CarResponse(
    @SerializedName("Models")
    val models: List<CarDto>
)