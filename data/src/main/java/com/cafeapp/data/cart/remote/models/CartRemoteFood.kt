package com.cafeapp.data.cart.remote.models

data class CartRemoteFood(
    val id: Long,
    val name: String,
    val description: String,
    val price: Int,
    val imageUrl: String
)
