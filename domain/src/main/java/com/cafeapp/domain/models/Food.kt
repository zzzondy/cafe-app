package com.cafeapp.domain.models

data class Food(
    val id: Long,
    val name: String,
    val price: Int,
    val description: String,
    val imageUrl: String,
)
