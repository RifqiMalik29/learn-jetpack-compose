package com.example.jetpack

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CounterUIState(
    val count: Int = 0,
    val stepText: String = "1"
) {
    val isIncrementEnabled: Boolean get() = count < 10
    val isResetEnabled: Boolean get() = count > 0
}

class CounterViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CounterUIState())
    val uiState: StateFlow<CounterUIState> = _uiState.asStateFlow()

    fun onStepChange(newStep: String) {
        _uiState.update {
            it.copy(stepText = newStep)
        }
    }

    fun onIncrement() {
       _uiState.update {
           val step = it.stepText.toIntOrNull() ?: 1
           it.copy(count = it.count + step )
       }
    }

    fun onReset() {
        _uiState.update {
            it.copy(count = 0)
        }
    }
}