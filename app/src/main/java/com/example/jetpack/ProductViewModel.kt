package com.example.jetpack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProductUIState {
    data object Loading: ProductUIState
    data class Success(val products: List<Product>): ProductUIState
    data class Error(val message: String): ProductUIState
}

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<ProductUIState>(ProductUIState.Loading)
    val uiState: StateFlow<ProductUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.products.collect {
                if (it.isNotEmpty()) {
                    _uiState.value = ProductUIState.Success(it)
                }
            }
        }

        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                repository.refreshProducts()
            } catch (e: Exception) {
                if (_uiState.value !is ProductUIState.Success) {
                    _uiState.value = ProductUIState.Error(e.localizedMessage ?: "Failed to fetch data")
                }
            }
        }
    }
}