package com.cafeapp.data.orders.remote.models

import com.cafeapp.data.cart.remote.models.CartRemoteFood

data class RemoteOrderWithFood(
    val foods: List<Pair<CartRemoteFood, Int>>,
    val total: Int,
    val paymentMethod: RemotePaymentMethod,
    val deliveryMethod: RemoteDeliveryMethod,
    val deliveryAddress: String? = null
)
