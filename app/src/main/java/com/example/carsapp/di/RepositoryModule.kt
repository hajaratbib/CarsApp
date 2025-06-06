package com.example.carsapp.di

import com.example.carsapp.data.repository.CarRepoImpl
import com.example.carsapp.domain.repository.CarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCarRepository(
        impl: CarRepoImpl
    ): CarRepository
}