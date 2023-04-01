package com.cafeapp.data.make_order.utils

import com.cafeapp.data.make_order.remote.models.RemotePaymentMethod
import com.cafeapp.data.make_order.remote.models.RemoteDeliveryMethod
import com.cafeapp.data.make_order.remote.models.RemoteMakeOrderResult
import com.cafeapp.data.make_order.remote.models.RemoteOrderDetails
import com.cafeapp.data.make_order.remote.states.RemoteObtainingDeliveryMethodsResult
import com.cafeapp.data.make_order.remote.states.RemoteObtainingMethodPaymentsResult
import com.cafeapp.domain.make_order.models.PaymentMethod
import com.cafeapp.domain.make_order.models.DeliveryMethod
import com.cafeapp.domain.make_order.models.OrderDetails
import com.cafeapp.domain.make_order.states.MakeOrderResult
import com.cafeapp.domain.make_order.states.ObtainingDeliveryMethodsResult
import com.cafeapp.domain.make_order.states.ObtainingPaymentMethodsResult


fun RemoteObtainingMethodPaymentsResult.toDomain(): ObtainingPaymentMethodsResult =
    when (this) {
        is RemoteObtainingMethodPaymentsResult.Success -> ObtainingPaymentMethodsResult.Success(data.map { it.toDomain() })

        is RemoteObtainingMethodPaymentsResult.NetworkError -> ObtainingPaymentMethodsResult.NetworkError

        is RemoteObtainingMethodPaymentsResult.OtherError -> ObtainingPaymentMethodsResult.OtherError
    }


fun RemoteObtainingDeliveryMethodsResult.toDomain(): ObtainingDeliveryMethodsResult =
    when (this) {
        is RemoteObtainingDeliveryMethodsResult.Success -> ObtainingDeliveryMethodsResult.Success(
            data.map { it.toDomain() })

        is RemoteObtainingDeliveryMethodsResult.NetworkError -> ObtainingDeliveryMethodsResult.NetworkError

        is RemoteObtainingDeliveryMethodsResult.OtherError -> ObtainingDeliveryMethodsResult.OtherError
    }


private fun RemotePaymentMethod.toDomain(): PaymentMethod = PaymentMethod(id, name)

private fun RemoteDeliveryMethod.toDomain(): DeliveryMethod =
    DeliveryMethod(id, name, fullAddress, isDelivery)

private fun DeliveryMethod.toRemote(): RemoteDeliveryMethod =
    RemoteDeliveryMethod(id, name, fullAddress, isDelivery)

private fun PaymentMethod.toRemote(): RemotePaymentMethod = RemotePaymentMethod(id, name)



fun RemoteMakeOrderResult.toDomain(): MakeOrderResult =
    when (this) {
        RemoteMakeOrderResult.Success -> MakeOrderResult.Success

        RemoteMakeOrderResult.NetworkError -> MakeOrderResult.NetworkError

        RemoteMakeOrderResult.OtherError -> MakeOrderResult.OtherError
    }

fun OrderDetails.toRemote(): RemoteOrderDetails =
    RemoteOrderDetails(
        userId = userId,
        food = food,
        total = total,
        deliveryMethod = deliveryMethod.toRemote(),
        paymentMethod = paymentMethod.toRemote(),
        deliveryAddress = deliveryAddress,
        phoneNumber = phoneNumber
    )