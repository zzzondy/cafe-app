package com.cafeapp.data.orders.remote.models

data class RemoteOrderDetails(
    val userId: String,
    val food: List<Pair<Long, Int>>,
    val total: Int,
    val deliveryMethod: RemoteDeliveryMethod,
    val paymentMethod: RemotePaymentMethod,
    val phoneNumber: String,
    val deliveryAddress: String? = null,
)