package com.cafeapp.data.orders.utils

import com.cafeapp.data.cart.util.toDomain
import com.cafeapp.data.orders.remote.models.RemotePaymentMethod
import com.cafeapp.data.orders.remote.models.RemoteDeliveryMethod
import com.cafeapp.data.orders.remote.make_order.states.RemoteMakeOrderResult
import com.cafeapp.data.orders.remote.models.RemoteOrderDetails
import com.cafeapp.data.orders.remote.make_order.states.RemoteObtainingDeliveryMethodsResult
import com.cafeapp.data.orders.remote.make_order.states.RemoteObtainingMethodPaymentsResult
import com.cafeapp.data.orders.remote.models.RemoteOrderWithFood
import com.cafeapp.data.orders.remote.orders_list.states.RemoteObtainingOrdersResult
import com.cafeapp.domain.orders.models.PaymentMethod
import com.cafeapp.domain.orders.models.DeliveryMethod
import com.cafeapp.domain.orders.models.OrderDetails
import com.cafeapp.domain.orders.make_order.states.MakeOrderResult
import com.cafeapp.domain.orders.make_order.states.ObtainingDeliveryMethodsResult
import com.cafeapp.domain.orders.make_order.states.ObtainingPaymentMethodsResult
import com.cafeapp.domain.orders.orders_list.models.Order
import com.cafeapp.domain.orders.orders_list.states.ObtainingOrdersListResult


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

fun RemoteObtainingOrdersResult.toDomain() =
    when (this) {
        is RemoteObtainingOrdersResult.Success -> ObtainingOrdersListResult.Success(this.data.map { it.toDomain() })

        is RemoteObtainingOrdersResult.NetworkError -> ObtainingOrdersListResult.NetworkError

        is RemoteObtainingOrdersResult.OtherError -> ObtainingOrdersListResult.OtherError
    }

fun RemoteOrderWithFood.toDomain() = Order(
    foods = this.foods.map { it.first.toDomain() to it.second },
    total = this.total,
    paymentMethod = this.paymentMethod.toDomain(),
    deliveryMethod = this.deliveryMethod.toDomain(),
    deliveryAddress = this.deliveryAddress
)