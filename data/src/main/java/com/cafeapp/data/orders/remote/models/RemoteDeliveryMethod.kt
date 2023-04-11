package com.cafeapp.data.orders.remote.models

import com.google.firebase.firestore.PropertyName

data class RemoteDeliveryMethod(

    @PropertyName("id")
    val id: Long = 0,

    @PropertyName("name")
    val name: String = "",

    @PropertyName("fullAddress")
    val fullAddress: String = "",

    @PropertyName("isDelivery")
    @field:JvmField
    val isDelivery: Boolean = true
)
