package com.example.carsapp.data

import com.example.carsapp.data.dto.CarResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CarService {
    @GET("api/0.3/")
    suspend fun getCars(
        @Query("cmd") cmd: String = "getModels",
        @Query("make") make: String
    ): CarResponse
}