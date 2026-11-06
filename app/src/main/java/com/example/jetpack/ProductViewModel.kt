package com.example.jetpack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProductUIState {
    data object Loading: ProductUIState
    data class Success(val products: List<Product>): ProductUIState
    data class Error(val message: String): ProductUIState
}

class ProductViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<ProductUIState>(ProductUIState.Loading)
    private val repository = (application as AppApplication).productRepository
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
                // Kalau belum pernah sukses dapat data (layar masih kosong), baru tampilkan error:
                if (_uiState.value !is ProductUIState.Success) {
                    _uiState.value = ProductUIState.Error(e.localizedMessage ?: "Failed to fetch data")
                }
            }
        }
    }
}