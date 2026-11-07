package com.example.jetpack

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor (
    private val productDao: ProductDao,
    private val apiService: ProductApiService = ApiClient.productService
) {
    val products: Flow<List<Product>> = productDao.getAllProduct().map { entities ->
        entities.map { it.toProduct() }
    }

    suspend fun refreshProducts() {
        val response = apiService.getProducts()
        val entities = response.products.map { it.toEntity() }
        productDao.insertProducts(entities)
    }
}