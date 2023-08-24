package com.cafeapp.data.orders.remote.make_order.repository

import com.cafeapp.data.orders.remote.models.*
import com.cafeapp.data.orders.remote.make_order.states.RemoteMakeOrderResult
import com.cafeapp.data.orders.remote.make_order.states.RemoteObtainingDeliveryMethodsResult
import com.cafeapp.data.orders.remote.make_order.states.RemoteObtainingMethodPaymentsResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class RemoteMakeOrderRepositoryImpl(private val firestore: FirebaseFirestore) :
    RemoteMakeOrderRepository {

    override suspend fun getPaymentMethods(): RemoteObtainingMethodPaymentsResult {
        return try {
            val result = firestore.collection(PAYMENT_METHODS_COLLECTION)
                .get()
                .await()
                .toObjects(RemotePaymentMethod::class.java)

            if (result.isNotEmpty()) {
                RemoteObtainingMethodPaymentsResult.Success(result)
            } else {
                RemoteObtainingMethodPaymentsResult.NetworkError
            }
        } catch (e: FirebaseNetworkException) {
            RemoteObtainingMethodPaymentsResult.NetworkError
        } catch (e: Exception) {
            RemoteObtainingMethodPaymentsResult.OtherError
        }
    }

    override suspend fun getDeliveryMethods(): RemoteObtainingDeliveryMethodsResult {
        return try {
            val result = firestore.collection(DELIVERY_METHODS_COLLECTION)
                .get()
                .await()
                .toObjects(RemoteDeliveryMethod::class.java)

            if (result.isNotEmpty()) {
                RemoteObtainingDeliveryMethodsResult.Success(result)
            } else {
                RemoteObtainingDeliveryMethodsResult.NetworkError
            }
        } catch (e: FirebaseNetworkException) {
            RemoteObtainingDeliveryMethodsResult.NetworkError
        } catch (e: Exception) {
            RemoteObtainingDeliveryMethodsResult.OtherError
        }
    }

    override suspend fun makeOrder(orderDetails: RemoteOrderDetails): RemoteMakeOrderResult {
        return try {
            deleteFromUserCart(orderDetails.userId, orderDetails.food)
            addToOrders(orderDetails)
            RemoteMakeOrderResult.Success
        } catch (e: FirebaseNetworkException) {
            RemoteMakeOrderResult.NetworkError
        } catch (e: Exception) {
            RemoteMakeOrderResult.OtherError
        }
    }

    private suspend fun deleteFromUserCart(userId: String, foods: List<Pair<Long, Int>>) {
        val cart = (firestore.collection(USER_COLLECTION).document(userId)
            .get()
            .await()
            .data!![CART] as List<HashMap<String, Int>>).toMutableList()

        foods.forEach { pair ->
            val index = cart.indexOfFirst { it.keys.contains(pair.first.toString()) }
            cart.removeAt(index)
        }

        firestore.collection(USER_COLLECTION).document(userId)
            .update(CART, cart)
            .await()
    }

    private suspend fun addToOrders(orderDetails: RemoteOrderDetails) {
        val newReference = firestore.collection(ORDERS_COLLECTION).document()
        val order = RemoteOrder(
            foods = orderDetails.food.map { hashMapOf(it.first.toString() to it.second) },
            total = orderDetails.total,
            deliveryMethod = orderDetails.deliveryMethod,
            paymentMethod = orderDetails.paymentMethod,
            phoneNumber = orderDetails.phoneNumber,
            deliveryAddress = orderDetails.deliveryAddress
        )

        newReference.set(order, SetOptions.merge()).await()

        firestore.collection(USER_COLLECTION).document(orderDetails.userId)
            .update(ORDERS_LIST, FieldValue.arrayUnion(newReference.id))
            .await()
    }


    companion object {
        private const val PAYMENT_METHODS_COLLECTION = "payment_methods"
        private const val DELIVERY_METHODS_COLLECTION = "delivery_methods"
        private const val USER_COLLECTION = "user"
        private const val ORDERS_COLLECTION = "orders"

        private const val CART = "cart"
        private const val ORDERS_LIST = "orders_list"
    }
}