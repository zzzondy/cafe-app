package com.cafeapp.domain.orders.orders_list.models

import com.cafeapp.domain.models.Food
import com.cafeapp.domain.orders.models.DeliveryMethod
import com.cafeapp.domain.orders.models.PaymentMethod

data class Order(
    val foods: List<Pair<Food, Int>>,
    val total: Int,
    val paymentMethod: PaymentMethod,
    val deliveryMethod: DeliveryMethod,
    val deliveryAddress: String? = null
)
