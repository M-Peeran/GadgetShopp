package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation.shopping_cart

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem

sealed class ShoppingCartEvent {
    class IncrementQuantityForItem(val item: ShoppingCartItem) : ShoppingCartEvent()
    class DecrementQuantityForItem(val item: ShoppingCartItem) : ShoppingCartEvent()
    class RemoveFromCart(val itemId: Long) : ShoppingCartEvent()
    class InsertOrders(val items: List<ShoppingCartItem>): ShoppingCartEvent()

}