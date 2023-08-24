package com.cafeapp.data.cart.local

object CartDatabaseContract {
    const val DATABASE_NAME = "Cart.db"
    const val DATABASE_VERSION = 1

    object LocalCart {
        const val TABLE_NAME = "local_cart"

        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val PRICE = "price"
        const val IMAGE_URL = "image_url"
        const val COUNT = "count"
    }
}