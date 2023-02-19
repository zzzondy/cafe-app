package com.cafeapp.data.cart.local.repository

import com.cafeapp.data.cart.local.database.daos.LocalCartDao
import com.cafeapp.data.cart.local.models.LocalCartFood

class LocalCartRepositoryImpl(private val localCartDao: LocalCartDao) : LocalCartRepository {

    override suspend fun getLocalCart(): List<LocalCartFood> = localCartDao.getCart()

    override suspend fun addFoodToLocalCart(food: LocalCartFood): Long =
        localCartDao.addFoodToCart(food)

    override suspend fun deleteFoodFromCart(foodId: Long) {
        localCartDao.deleteFoodFromCart(foodId)
    }
}