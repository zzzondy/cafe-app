package com.cafeapp.ui.screens.cart.make_order

data class MakeOrderScreenNavArgs(
    val selectedFoodIds: LongArray,
    val foodsCount: IntArray,
    val total: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MakeOrderScreenNavArgs

        if (!selectedFoodIds.contentEquals(other.selectedFoodIds)) return false

        return true
    }

    override fun hashCode(): Int {
        return selectedFoodIds.contentHashCode()
    }
}