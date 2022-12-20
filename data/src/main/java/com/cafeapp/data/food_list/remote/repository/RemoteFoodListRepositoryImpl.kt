package com.cafeapp.data.food_list.remote.repository

import com.cafeapp.data.food_list.remote.models.RemoteFood
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RemoteFoodListRepositoryImpl(private val fireStore: FirebaseFirestore) :
    RemoteFoodListRepository {
    override suspend fun getPagedFoodList(page: Int, limit: Int): List<RemoteFood> {
        return fireStore.collection(FOOD_COLLECTION)
            .orderBy(ID)
            .startAt(page)
            .limit(limit.toLong())
            .get()
            .await()
            .documents.map { document ->
                toFoodRemote(
                    document.data!![ID],
                    document.data!![NAME],
                    document.data!![DESC],
                    document.data!![PRICE],
                    document.data!![IMAGE]
                )
            }
    }

    private fun toFoodRemote(vararg data: Any?): RemoteFood {
        return RemoteFood(
            id = data[0].toString().toLong(),
            name = data[1].toString(),
            description = data[2].toString(),
            price = data[3].toString().toInt(),
            imageUrl = data[4].toString()
        )
    }

    companion object {
        private const val FOOD_COLLECTION = "food"

        private const val ID = "id"
        private const val DESC = "description"
        private const val IMAGE = "image_url"
        private const val NAME = "name"
        private const val PRICE = "price"
    }
}