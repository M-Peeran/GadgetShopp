package com.peeranm.gadgetshopp.feature_gadgets_shopping.utils

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.OrderItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem

class OrderMapper {
    fun fromCartToOrderItem(item: ShoppingCartItem): OrderItem {
        return OrderItem(
            name = item.name,
            imageUrl = item.imageUrl,
            price = item.price,
        )
    }
}