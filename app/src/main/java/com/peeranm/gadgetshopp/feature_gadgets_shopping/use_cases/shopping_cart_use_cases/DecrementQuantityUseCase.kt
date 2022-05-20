package com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.shopping_cart_use_cases

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.ShoppingCartRepository

class DecrementQuantityUseCase(private val repository: ShoppingCartRepository) {
    suspend operator fun invoke(item: ShoppingCartItem) {
        repository.decrementQuantityForItem(item)
    }
}