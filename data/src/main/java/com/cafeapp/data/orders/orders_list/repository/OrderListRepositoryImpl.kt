package com.cafeapp.data.orders.orders_list.repository

import com.cafeapp.data.orders.remote.orders_list.repository.RemoteOrdersListRepository
import com.cafeapp.data.orders.utils.toDomain
import com.cafeapp.domain.orders.orders_list.repository.OrdersListRepository
import com.cafeapp.domain.orders.orders_list.states.ObtainingOrdersListResult

class OrderListRepositoryImpl(private val remoteOrdersListRepository: RemoteOrdersListRepository) :
    OrdersListRepository {

    override suspend fun getOrdersForUser(userId: String): ObtainingOrdersListResult =
        remoteOrdersListRepository.getOrdersForUser(userId).toDomain()
}