package com.example.carsapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsapp.common.Resource
import com.example.carsapp.domain.useCase.GetCarsUseCase
import com.example.carsapp.ui.CarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val getCarsUseCase: GetCarsUseCase
) : ViewModel() {

    private var _modelValue = MutableStateFlow("")
    val modelValue: StateFlow<String>
        get() = _modelValue

    private var _isRefreshLoading = MutableStateFlow(false)
    val isRefreshLoading: StateFlow<Boolean>
        get() = _isRefreshLoading

    private var _state = MutableStateFlow(CarState())
    val state: StateFlow<CarState>
        get() = _state


    fun fetchCars() {
        viewModelScope.launch {
            getCarsUseCase(_modelValue.value).collect { result ->
                _state.value = when (result) {
                    is Resource.Success -> CarState(cars = result.data ?: emptyList())
                    is Resource.Error -> CarState(error = result.message ?: "")
                    is Resource.Loading -> CarState(isLoading = true)
                }
            }
        }
    }

    fun loadCars() {
        viewModelScope.launch {
            getCarsUseCase(_modelValue.value).collect { result ->
                _isRefreshLoading.value = result is Resource.Loading
                result.data?.let { _state.value = CarState(cars = result.data) }
            }
        }
    }

    fun setModelValue(model: String) {
        _modelValue.value = model
    }

}