package com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.OrderItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun insertOrders(items: List<ShoppingCartItem>)
    fun getAllOrders(): Flow<List<OrderItem>>
}