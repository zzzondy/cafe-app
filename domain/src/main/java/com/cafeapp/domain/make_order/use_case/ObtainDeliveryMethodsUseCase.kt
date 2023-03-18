package com.cafeapp.domain.make_order.use_case

import com.cafeapp.domain.make_order.repository.MakeOrderRepository
import com.cafeapp.domain.make_order.states.ObtainingDeliveryMethodsResult

class ObtainDeliveryMethodsUseCase(private val makeOrderRepository: MakeOrderRepository) {

    suspend operator fun invoke(): ObtainingDeliveryMethodsResult =
        makeOrderRepository.getDeliveryMethods()
}