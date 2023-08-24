package com.cafeapp.domain.orders.make_order.usecase

import com.cafeapp.domain.orders.make_order.repository.MakeOrderRepository
import com.cafeapp.domain.orders.make_order.states.ObtainingPaymentMethodsResult

class ObtainPaymentMethodsUseCase(private val makeOrderRepository: MakeOrderRepository) {

    suspend operator fun invoke(): ObtainingPaymentMethodsResult =
        makeOrderRepository.getPaymentMethods()
}