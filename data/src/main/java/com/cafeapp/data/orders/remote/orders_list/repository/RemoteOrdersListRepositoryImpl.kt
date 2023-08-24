package com.cafeapp.data.orders.remote.orders_list.repository

import com.cafeapp.data.cart.remote.models.CartRemoteFood
import com.cafeapp.data.orders.remote.models.RemoteOrder
import com.cafeapp.data.orders.remote.models.RemoteOrderWithFood
import com.cafeapp.data.orders.remote.orders_list.states.RemoteObtainingOrdersResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class RemoteOrdersListRepositoryImpl(private val firestore: FirebaseFirestore) :
    RemoteOrdersListRepository {

    override suspend fun getOrdersForUser(userId: String): RemoteObtainingOrdersResult {
        return try {
            val orders = mutableListOf<Deferred<RemoteOrderWithFood>>()

            coroutineScope {
                getUserOrders(userId).forEach { orderId ->
                    orders.add(async { getOrderById(orderId) })
                }
            }

            RemoteObtainingOrdersResult.Success(orders.awaitAll())
        } catch (e: FirebaseNetworkException) {
            RemoteObtainingOrdersResult.NetworkError
        } catch (e: Exception) {
            RemoteObtainingOrdersResult.OtherError
        }
    }

    private suspend fun getOrderById(orderId: String): RemoteOrderWithFood {
        val order = firestore.collection(ORDERS_COLLECTION).document(orderId)
            .get()
            .await()
            .toObject(RemoteOrder::class.java)!!

        val foods = mutableListOf<Deferred<Pair<CartRemoteFood, Int>>>()

        coroutineScope {
            order.foods.forEach { pair ->
                val id = pair.keys.toList()[0].toLong()
                val count = pair[id.toString()]!!

                foods.add(
                    async { getFoodById(id) to count }
                )
            }
        }

        return RemoteOrderWithFood(
            foods = foods.awaitAll(),
            total = order.total,
            paymentMethod = order.paymentMethod,
            deliveryMethod = order.deliveryMethod,
            deliveryAddress = order.deliveryAddress
        )
    }

    private suspend fun getUserOrders(userId: String): List<String> {
        return firestore.collection(USER_COLLECTION).document(userId)
            .get()
            .await()[ORDERS_LIST] as List<String>
    }

    private suspend fun getFoodById(foodId: Long): CartRemoteFood {
        return firestore.collection(FOOD_COLLECTION).whereEqualTo(
            FOOD_ID, foodId
        )
            .get()
            .await()
            .documents.map { document ->
                toCartRemoteFood(
                    document.data!![ID],
                    document.data!![NAME],
                    document.data!![DESC],
                    document.data!![PRICE],
                    document.data!![IMAGE]
                )
            }[0]
    }

    private fun toCartRemoteFood(vararg data: Any?): CartRemoteFood {
        return CartRemoteFood(
            id = data[0].toString().toLong(),
            name = data[1].toString(),
            description = data[2].toString(),
            price = data[3].toString().toInt(),
            imageUrl = data[4].toString()
        )
    }

    companion object {
        private const val USER_COLLECTION = "user"
        private const val ORDERS_COLLECTION = "orders"
        private const val FOOD_COLLECTION = "food"

        private const val ORDERS_LIST = "orders_list"

        private const val FOOD_ID = "id"

        private const val ID = "id"
        private const val DESC = "description"
        private const val IMAGE = "image_url"
        private const val NAME = "name"
        private const val PRICE = "price"
    }
}