package com.cafeapp.data.make_order.remote.repository

import com.cafeapp.data.make_order.remote.models.RemoteDeliveryMethod
import com.cafeapp.data.make_order.remote.models.RemotePaymentMethod
import com.cafeapp.data.make_order.remote.states.RemoteObtainingDeliveryMethodsResult
import com.cafeapp.data.make_order.remote.states.RemoteObtainingMethodPaymentsResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RemoteMakeOrderRepositoryImpl(private val firestore: FirebaseFirestore) :
    RemoteMakeOrderRepository {

    override suspend fun getPaymentMethods(): RemoteObtainingMethodPaymentsResult {
        return try {
            RemoteObtainingMethodPaymentsResult.Success(
                firestore.collection(PAYMENT_METHODS_COLLECTION)
                    .get()
                    .await()
                    .toObjects(RemotePaymentMethod::class.java)
            )
        } catch (e: FirebaseNetworkException) {
            RemoteObtainingMethodPaymentsResult.NetworkError
        } catch (e: Exception) {
            RemoteObtainingMethodPaymentsResult.OtherError
        }
    }

    override suspend fun getDeliveryMethods(): RemoteObtainingDeliveryMethodsResult {
        return try {
            RemoteObtainingDeliveryMethodsResult.Success(
                firestore.collection(DELIVERY_METHODS_COLLECTION)
                    .get()
                    .await()
                    .toObjects(RemoteDeliveryMethod::class.java)
            )
        } catch (e: FirebaseNetworkException) {
            RemoteObtainingDeliveryMethodsResult.NetworkError
        } catch (e: Exception) {
            RemoteObtainingDeliveryMethodsResult.OtherError
        }
    }

    companion object {
        private const val PAYMENT_METHODS_COLLECTION = "payment_methods"
        private const val DELIVERY_METHODS_COLLECTION = "delivery_methods"
    }
}