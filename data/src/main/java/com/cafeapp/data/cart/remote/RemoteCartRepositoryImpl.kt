package com.cafeapp.data.cart.remote

import com.cafeapp.data.cart.remote.models.CartRemoteFood
import com.cafeapp.data.cart.remote.states.RemoteCartTransactionsResult
import com.cafeapp.data.cart.remote.states.RemoteIncrementResult
import com.cafeapp.data.cart.remote.states.RemoteObtainingCartResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class RemoteCartRepositoryImpl(private val firestore: FirebaseFirestore) : RemoteCartRepository {

    override suspend fun addFoodToCart(
        foodId: Long,
        userId: String
    ): RemoteCartTransactionsResult {
        return try {
            firestore.collection(USER_COLLECTION).document(userId)
                .update(CART, FieldValue.arrayUnion(hashMapOf(foodId.toString() to 1)))
                .await()
            RemoteCartTransactionsResult.Success
        } catch (e: FirebaseNetworkException) {
            RemoteCartTransactionsResult.NetworkError
        } catch (e: Exception) {
            RemoteCartTransactionsResult.OtherError
        }
    }

    override suspend fun getUserCart(userId: String): RemoteObtainingCartResult {
        return try {
            val foodList = mutableListOf<Deferred<Pair<CartRemoteFood, Int>>>()
            coroutineScope {
                getUserCartIds(userId).forEach { pair ->
                    foodList.add(async {
                        getFoodById(pair.first) to pair.second
                    })
                }
            }
            RemoteObtainingCartResult.Success(foodList.awaitAll().reversed())
        } catch (e: FirebaseNetworkException) {
            RemoteObtainingCartResult.NetworkError
        } catch (e: Exception) {
            RemoteObtainingCartResult.OtherError
        }
    }

    override suspend fun incrementItemsCount(userId: String, foodId: Long): RemoteIncrementResult {
        return try {
            val cart = (firestore.collection(USER_COLLECTION).document(userId)
                .get()
                .await()
                .data?.get(CART) as List<HashMap<String, Int>>)
                .toMutableList()
            val index = cart.indexOfFirst { it.keys.contains(foodId.toString()) }
            val newValue = cart[index][foodId.toString()]!! + 1
            cart[index] = hashMapOf(foodId.toString() to newValue)
            firestore.collection(USER_COLLECTION).document(userId)
                .update(CART, cart)
                .await()
            RemoteIncrementResult.Success(newValue)
        } catch (e: FirebaseNetworkException) {
            RemoteIncrementResult.NetworkError
        } catch (e: Exception) {
            RemoteIncrementResult.OtherError
        }
    }

    override suspend fun decrementItemsCount(userId: String, foodId: Long): RemoteIncrementResult {
        return try {
            val cart = (firestore.collection(USER_COLLECTION).document(userId)
                .get()
                .await()
                .data?.get(CART) as List<HashMap<String, Int>>)
                .toMutableList()
            val index = cart.indexOfFirst { it.keys.contains(foodId.toString()) }
            val newValue = cart[index][foodId.toString()]!! - 1
            if (newValue < 1) {
                throw IllegalStateException()
            }
            cart[index] = hashMapOf(foodId.toString() to newValue)
            firestore.collection(USER_COLLECTION).document(userId)
                .update(CART, cart)
                .await()
            RemoteIncrementResult.Success(newValue)
        } catch (e: FirebaseNetworkException) {
            RemoteIncrementResult.NetworkError
        } catch (e: Exception) {
            RemoteIncrementResult.OtherError
        }
    }

    override suspend fun deleteFoodFromCart(
        userId: String,
        foodId: Long
    ): RemoteCartTransactionsResult {
        return try {
            val cart = (firestore.collection(USER_COLLECTION).document(userId)
                .get()
                .await()
                .data?.get(CART) as List<HashMap<String, Int>>)
                .toMutableList()
            val index = cart.indexOfFirst { it.keys.contains(foodId.toString()) }
            cart.removeAt(index)
            firestore.collection(USER_COLLECTION).document(userId)
                .update(CART, cart)
                .await()
            RemoteCartTransactionsResult.Success
        } catch (e: FirebaseNetworkException) {
            RemoteCartTransactionsResult.NetworkError
        } catch (e: Exception) {
            RemoteCartTransactionsResult.OtherError
        }
    }

    override suspend fun deleteSelectedFoodFromCart(
        userId: String,
        foodIds: List<Long>
    ): RemoteCartTransactionsResult {
        return try {
            val cart = (firestore.collection(USER_COLLECTION).document(userId)
                .get()
                .await()
                .data?.get(CART) as List<HashMap<String, Int>>)
                .toMutableList()

            foodIds.forEach { foodId ->
                val index = cart.indexOfFirst { it.keys.contains(foodId.toString()) }
                cart.removeAt(index)
            }
            firestore.collection(USER_COLLECTION).document(userId)
                .update(CART, cart)
                .await()
            RemoteCartTransactionsResult.Success
        } catch (e: FirebaseNetworkException) {
            RemoteCartTransactionsResult.NetworkError
        } catch (e: Exception) {
            RemoteCartTransactionsResult.OtherError
        }
    }

    private suspend fun getFoodById(foodId: Long): CartRemoteFood {
        return firestore.collection(FOOD_COLLECTION).whereEqualTo(FOOD_ID, foodId)
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

    private suspend fun getUserCartIds(userId: String): List<Pair<Long, Int>> {
        val result = firestore.collection(USER_COLLECTION).document(userId)
            .get()
            .await()
            .data!![CART] as List<HashMap<String, Int>>
        return result.map { pair ->
            val key = pair.keys.toList()[0].toLong()
            val value = pair.values.toList()[0]
            key to value
        }
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
        private const val FOOD_COLLECTION = "food"
        private const val CART = "cart"
        private const val FOOD_ID = "id"

        private const val ID = "id"
        private const val DESC = "description"
        private const val IMAGE = "image_url"
        private const val NAME = "name"
        private const val PRICE = "price"
    }
}