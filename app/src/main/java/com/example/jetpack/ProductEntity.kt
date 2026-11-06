package com.example.jetpack

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("products")
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val rating: Double,
    val thumbnail: String
)

fun Product.toEntity() = ProductEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    category = category,
    rating = rating,
    thumbnail = thumbnail
)

fun ProductEntity.toProduct() = Product(
    id = id,
    title = title,
    description = description,
    price = price,
    category = category,
    rating = rating,
    thumbnail = thumbnail
)