package com.cafeapp.domain.make_order.models

data class DeliveryMethod(
    val id: Long,
    val name: String,
    val fullAddress: String,
    val isDelivery: Boolean
)
