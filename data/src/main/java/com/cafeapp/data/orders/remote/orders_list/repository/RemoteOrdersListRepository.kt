package com.cafeapp.data.orders.remote.orders_list.repository

import com.cafeapp.data.orders.remote.orders_list.states.RemoteObtainingOrdersResult

interface RemoteOrdersListRepository {

    suspend fun getOrdersForUser(userId: String): RemoteObtainingOrdersResult
}