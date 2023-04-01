package com.cafeapp.data.make_order.remote.repository

import com.cafeapp.data.make_order.remote.models.RemoteMakeOrderResult
import com.cafeapp.data.make_order.remote.models.RemoteOrderDetails
import com.cafeapp.data.make_order.remote.states.RemoteObtainingDeliveryMethodsResult
import com.cafeapp.data.make_order.remote.states.RemoteObtainingMethodPaymentsResult

interface RemoteMakeOrderRepository {

    suspend fun getPaymentMethods(): RemoteObtainingMethodPaymentsResult

    suspend fun getDeliveryMethods(): RemoteObtainingDeliveryMethodsResult

    suspend fun makeOrder(orderDetails: RemoteOrderDetails): RemoteMakeOrderResult
}