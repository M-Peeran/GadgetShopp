package com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.model.Gadget
import kotlinx.coroutines.flow.Flow

interface ShoppingCartRepository {
    fun getAllCartItems(): Flow<List<ShoppingCartItem>>
    fun getTotalPrice(): Flow<Double?>
    fun getCartItemsCount(): Flow<Int?>
    suspend fun insertToCart(item: Gadget)
    suspend fun removeItemFromCartById(itemId: Long)
    suspend fun incrementQuantityForItem(item: ShoppingCartItem)
    suspend fun decrementQuantityForItem(item: ShoppingCartItem)
    suspend fun clear()
}