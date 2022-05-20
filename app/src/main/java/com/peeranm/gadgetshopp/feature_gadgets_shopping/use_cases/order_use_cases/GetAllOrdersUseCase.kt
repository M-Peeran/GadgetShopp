package com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.order_use_cases

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.OrderItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.OrderRepository
import kotlinx.coroutines.flow.Flow

class GetAllOrdersUseCase(private val repository: OrderRepository) {
    operator fun invoke(): Flow<List<OrderItem>>
    = repository.getAllOrders()
}