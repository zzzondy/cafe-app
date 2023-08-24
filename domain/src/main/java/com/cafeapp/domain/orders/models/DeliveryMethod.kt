package com.cafeapp.domain.orders.models

data class DeliveryMethod(
    val id: Long,
    val name: String,
    val fullAddress: String,
    val isDelivery: Boolean
)
