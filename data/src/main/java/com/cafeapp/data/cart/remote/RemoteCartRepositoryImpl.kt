package com.cafeapp.data.cart.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class RemoteCartRepositoryImpl(private val firestore: FirebaseFirestore) : RemoteCartRepository {

    override suspend fun createCartForUser(userId: String) {
        val documentData = hashMapOf("cart" to listOf<Int>())
        firestore.collection(USER_COLLECTION).document(userId)
            .set(documentData, SetOptions.merge())
            .await()
    }

    companion object {
        private const val USER_COLLECTION = "user"
    }
}