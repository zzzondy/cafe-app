package com.cafeapp.domain.orders.orders_list.repository

import com.cafeapp.domain.orders.orders_list.states.ObtainingOrdersListResult

interface OrdersListRepository {

    suspend fun getOrdersForUser(userId: String): ObtainingOrdersListResult
}