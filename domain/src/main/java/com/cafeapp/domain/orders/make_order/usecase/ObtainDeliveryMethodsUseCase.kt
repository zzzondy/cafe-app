package com.cafeapp.domain.orders.make_order.usecase

import com.cafeapp.domain.orders.make_order.repository.MakeOrderRepository
import com.cafeapp.domain.orders.make_order.states.ObtainingDeliveryMethodsResult

class ObtainDeliveryMethodsUseCase(private val makeOrderRepository: MakeOrderRepository) {

    suspend operator fun invoke(): ObtainingDeliveryMethodsResult =
        makeOrderRepository.getDeliveryMethods()
}