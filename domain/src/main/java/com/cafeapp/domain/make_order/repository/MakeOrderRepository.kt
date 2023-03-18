package com.cafeapp.domain.make_order.repository

import com.cafeapp.domain.make_order.states.ObtainingDeliveryMethodsResult
import com.cafeapp.domain.make_order.states.ObtainingPaymentMethodsResult

interface MakeOrderRepository {

    suspend fun getPaymentMethods(): ObtainingPaymentMethodsResult

    suspend fun getDeliveryMethods(): ObtainingDeliveryMethodsResult
}