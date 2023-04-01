package com.cafeapp.data.make_order.repository

import com.cafeapp.data.make_order.remote.repository.RemoteMakeOrderRepository
import com.cafeapp.data.make_order.utils.toDomain
import com.cafeapp.data.make_order.utils.toRemote
import com.cafeapp.domain.make_order.models.OrderDetails
import com.cafeapp.domain.make_order.repository.MakeOrderRepository
import com.cafeapp.domain.make_order.states.MakeOrderResult
import com.cafeapp.domain.make_order.states.ObtainingDeliveryMethodsResult
import com.cafeapp.domain.make_order.states.ObtainingPaymentMethodsResult

class MakeOrderRepositoryImpl(private val remoteMakeOrderRepository: RemoteMakeOrderRepository) :
    MakeOrderRepository {

    override suspend fun getPaymentMethods(): ObtainingPaymentMethodsResult =
        remoteMakeOrderRepository.getPaymentMethods().toDomain()

    override suspend fun getDeliveryMethods(): ObtainingDeliveryMethodsResult =
        remoteMakeOrderRepository.getDeliveryMethods().toDomain()

    override suspend fun makeOrder(orderDetails: OrderDetails): MakeOrderResult =
        remoteMakeOrderRepository.makeOrder(orderDetails.toRemote()).toDomain()
}