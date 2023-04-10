package com.cafeapp.data.orders.remote.models

data class RemoteDeliveryMethod(
    val id: Long = 0,

    val name: String = "",

    val fullAddress: String = "",

    @field:JvmField
    val isDelivery: Boolean = true
)
