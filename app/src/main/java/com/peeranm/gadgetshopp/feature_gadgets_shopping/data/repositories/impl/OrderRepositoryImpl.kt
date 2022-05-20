package com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.impl

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.GadgetDatabase
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.OrderItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.OrderRepository
import com.peeranm.gadgetshopp.feature_gadgets_shopping.utils.OrderMapper
import kotlinx.coroutines.flow.Flow


class OrderRepositoryImpl(
    private val database: GadgetDatabase,
    private val mapper: OrderMapper
) : OrderRepository {

    override suspend fun insertOrders(items: List<ShoppingCartItem>) {
        items.forEach { cartItem ->
            if (cartItem.quantity > 1) {
                for (i in 1..cartItem.quantity) {
                    database.orderDao().insertOrder(
                        mapper.fromCartToOrderItem(cartItem)
                    )
                }
            } else {
                database.orderDao().insertOrder(
                    mapper.fromCartToOrderItem(cartItem)
                )
            }
        }
    }

    override fun getAllOrders(): Flow<List<OrderItem>> {
        return database.orderDao().getAllOrders()
    }
}