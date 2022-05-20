package com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.order_use_cases

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.OrderRepository

class InsertOrdersUseCase(private val repository: OrderRepository) {
    suspend operator fun invoke(items: List<ShoppingCartItem>) {
        repository.insertOrders(items)
    }
}