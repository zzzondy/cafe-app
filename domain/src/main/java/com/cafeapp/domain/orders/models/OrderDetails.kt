package com.cafeapp.domain.orders.models

data class OrderDetails(
    val userId: String = "",
    val food: List<Pair<Long, Int>> = listOf(),
    val total: Int = 0,
    val deliveryMethod: DeliveryMethod = DeliveryMethod(0, "", "", false),
    val paymentMethod: PaymentMethod = PaymentMethod(0, ""),
    val deliveryAddress: String? = null,
    val phoneNumber: String = ""
)
