package com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.shopping_cart_use_cases

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.ShoppingCartRepository

class RemoveItemFromCartByIdUseCase(private val repository: ShoppingCartRepository) {
    suspend operator fun invoke(itemId: Long) {
        repository.removeItemFromCartById(itemId)
    }
}