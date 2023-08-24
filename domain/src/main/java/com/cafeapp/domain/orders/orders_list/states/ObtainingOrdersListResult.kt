package com.cafeapp.domain.orders.orders_list.states

import com.cafeapp.domain.orders.orders_list.models.Order

sealed class ObtainingOrdersListResult {

    data class Success(val data: List<Order>) : ObtainingOrdersListResult()

    object UserIsNotAuthenticatedError : ObtainingOrdersListResult()

    object NetworkError : ObtainingOrdersListResult()

    object OtherError : ObtainingOrdersListResult()
}
