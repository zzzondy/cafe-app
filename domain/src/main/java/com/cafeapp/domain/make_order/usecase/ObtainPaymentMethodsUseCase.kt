package com.cafeapp.domain.make_order.usecase

import com.cafeapp.domain.make_order.repository.MakeOrderRepository
import com.cafeapp.domain.make_order.states.ObtainingPaymentMethodsResult

class ObtainPaymentMethodsUseCase(private val makeOrderRepository: MakeOrderRepository) {

    suspend operator fun invoke(): ObtainingPaymentMethodsResult =
        makeOrderRepository.getPaymentMethods()
}