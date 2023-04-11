package com.cafeapp.data.orders.remote.models

import com.google.firebase.firestore.PropertyName

data class RemotePaymentMethod(

    @PropertyName("id")
    val id: Long = 0,

    @PropertyName("name")
    val name: String = ""
)
