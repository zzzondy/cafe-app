package com.cafeapp.data.food_list.remote.repository

import com.cafeapp.data.food_list.remote.models.FoodRemote
import com.google.firebase.firestore.FirebaseFirestore

class RemoteFoodListRepositoryImpl(private val fireStore: FirebaseFirestore) :
    RemoteFoodListRepository {
    override suspend fun getPagedFoodList(page: Int, limit: Long): List<FoodRemote> {
        var result: List<FoodRemote> = listOf()
        fireStore.collection(FOOD_COLLECTION)
            .startAt(page)
            .limit(limit)
            .get()
            .addOnSuccessListener { res ->
                result = res.documents.map { document ->
                    toFoodRemote(
                        document.data!![ID],
                        document.data!![NAME],
                        document.data!![DESC],
                        document.data!![PRICE],
                        document.data!![IMAGE]
                    )
                }
            }

        return result
    }

    private fun toFoodRemote(vararg data: Any?): FoodRemote {
        return FoodRemote(
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