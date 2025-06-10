package com.example.myweather.presentation.currentweather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.domain.models.Weather
import com.example.myweather.domain.usecase.GetWeatherUseCase
import com.example.myweather.presentation.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherUiState>(WeatherUiState())
    val state: StateFlow<WeatherUiState> = _state.asStateFlow()


    init {
        viewModelScope.launch(dispatcherProvider.IO) {
            try {
                _state.update {weatherToWeatherUiState (getWeatherUseCase())}
            } catch (exception: Exception) {
              Log.e("getWeatherUseCase",exception.message.toString())
            }

        }
    }
}