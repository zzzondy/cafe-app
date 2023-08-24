package com.cafeapp.data.orders.remote.models

import com.google.firebase.firestore.PropertyName

data class RemoteOrder(

    @PropertyName("foods")
    val foods: List<HashMap<String, Int>> = listOf(),

    @PropertyName("total")
    val total: Int = 0,

    @PropertyName("deliveryMethod")
    val deliveryMethod: RemoteDeliveryMethod = RemoteDeliveryMethod(),

    @PropertyName("paymentMethod")
    val paymentMethod: RemotePaymentMethod = RemotePaymentMethod(),

    @PropertyName("phoneNumber")
    val phoneNumber: String = "",

    @PropertyName("deliveryAddress")
    val deliveryAddress: String? = null
)
