package com.example.jetpack

import androidx.lifecycle.ViewModel
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

class ProductViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<ProductUIState>(ProductUIState.Loading)
    val uiState: StateFlow<ProductUIState> = _uiState.asStateFlow()

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUIState.Loading
            try {
                val response = ApiClient.productService.getProducts()
                _uiState.value = ProductUIState.Success(response.products)
            } catch (e: Exception) {
                _uiState.value = ProductUIState.Error(e.localizedMessage ?: "Failed to fetch data")
            }
        }
    }
}