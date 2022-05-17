package com.peeranm.gadgetshopp.feature_gadgets.data.repositories

import com.peeranm.gadgetshopp.core.utils.Resource
import com.peeranm.gadgetshopp.feature_gadgets.data.local.entities.ShoppingCartItem
import com.peeranm.gadgetshopp.feature_gadgets.model.Gadget
import kotlinx.coroutines.flow.Flow

interface GadgetsRepository {
    fun getGadgets(): Flow<Resource<List<Gadget>>>
    fun getAllCartItems(): Flow<List<ShoppingCartItem>>
    suspend fun insertToCart(item: ShoppingCartItem)
    suspend fun deleteFromCart(item: ShoppingCartItem)
    suspend fun clearCart()
}