package com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.shopping_cart_use_cases

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.ShoppingCartRepository
import kotlinx.coroutines.flow.Flow

class GetShoppingCartItemsUseCase(private val repository: ShoppingCartRepository) {
    operator fun invoke(): Flow<List<ShoppingCartItem>>
    = repository.getAllCartItems()
}