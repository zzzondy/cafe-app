package com.cafeapp.data.orders.remote.make_order.repository

import com.cafeapp.data.orders.remote.make_order.states.RemoteMakeOrderResult
import com.cafeapp.data.orders.remote.models.RemoteOrderDetails
import com.cafeapp.data.orders.remote.make_order.states.RemoteObtainingDeliveryMethodsResult
import com.cafeapp.data.orders.remote.make_order.states.RemoteObtainingMethodPaymentsResult

interface RemoteMakeOrderRepository {

    suspend fun getPaymentMethods(): RemoteObtainingMethodPaymentsResult

    suspend fun getDeliveryMethods(): RemoteObtainingDeliveryMethodsResult

    suspend fun makeOrder(orderDetails: RemoteOrderDetails): RemoteMakeOrderResult
}