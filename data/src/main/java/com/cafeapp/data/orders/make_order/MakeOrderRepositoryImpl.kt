package com.cafeapp.data.orders.make_order

import com.cafeapp.data.orders.remote.make_order.repository.RemoteMakeOrderRepository
import com.cafeapp.data.orders.utils.toDomain
import com.cafeapp.data.orders.utils.toRemote
import com.cafeapp.domain.orders.models.OrderDetails
import com.cafeapp.domain.orders.make_order.repository.MakeOrderRepository
import com.cafeapp.domain.orders.make_order.states.MakeOrderResult
import com.cafeapp.domain.orders.make_order.states.ObtainingDeliveryMethodsResult
import com.cafeapp.domain.orders.make_order.states.ObtainingPaymentMethodsResult

class MakeOrderRepositoryImpl(private val remoteMakeOrderRepository: RemoteMakeOrderRepository) :
    MakeOrderRepository {

    override suspend fun getPaymentMethods(): ObtainingPaymentMethodsResult =
        remoteMakeOrderRepository.getPaymentMethods().toDomain()

    override suspend fun getDeliveryMethods(): ObtainingDeliveryMethodsResult =
        remoteMakeOrderRepository.getDeliveryMethods().toDomain()

    override suspend fun makeOrder(orderDetails: OrderDetails): MakeOrderResult =
        remoteMakeOrderRepository.makeOrder(orderDetails.toRemote()).toDomain()
}