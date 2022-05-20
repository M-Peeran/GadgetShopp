package com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.shopping_cart_use_cases

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.ShoppingCartRepository
import com.peeranm.gadgetshopp.feature_gadgets_shopping.model.Gadget

class InsertToShoppingCartUseCase(private val repository: ShoppingCartRepository) {
    suspend operator fun invoke(item: Gadget) {
        repository.insertToCart(item)
    }
}