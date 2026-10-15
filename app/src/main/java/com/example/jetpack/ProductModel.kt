package com.example.jetpack

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val products: List<Product>
)

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val rating: Double,
    val thumbnail: String
)
