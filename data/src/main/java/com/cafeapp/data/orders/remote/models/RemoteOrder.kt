package com.cafeapp.data.orders.remote.models

data class RemoteOrder(
    val foods: List<HashMap<String, Int>> = listOf(),
    val total: Int = 0,
    val deliveryMethod: RemoteDeliveryMethod = RemoteDeliveryMethod(),
    val paymentMethod: RemotePaymentMethod = RemotePaymentMethod(),
    val phoneNumber: String = "",
    val deliveryAddress: String? = null
)
