package com.example.jetpack

import android.app.Application

class AppApplication: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }

    val productRepository by lazy { ProductRepository(database.productDao()) }
}