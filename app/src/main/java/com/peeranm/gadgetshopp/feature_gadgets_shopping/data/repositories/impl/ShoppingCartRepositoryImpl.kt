package com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.impl

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.GadgetDatabase
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.ShoppingCartRepository
import com.peeranm.gadgetshopp.feature_gadgets_shopping.model.Gadget
import com.peeranm.gadgetshopp.feature_gadgets_shopping.utils.GadgetMapper
import kotlinx.coroutines.flow.Flow

class ShoppingCartRepositoryImpl(
    private val database: GadgetDatabase,
    private val mapper: GadgetMapper
) : ShoppingCartRepository {

    override fun getAllCartItems(): Flow<List<ShoppingCartItem>> {
        return database.shoppingCartDao().getShoppingCartItems()
    }

    override fun getTotalPrice(): Flow<Double?> {
        return database.shoppingCartDao().getTotalPrice()
    }

    override fun getCartItemsCount(): Flow<Int?> {
        return database.shoppingCartDao().getCartItemsCount()
    }

    override suspend fun insertToCart(item: Gadget) {
        val cartItem = mapper.fromUiModelToCartItem(item)
        database.shoppingCartDao().insertItem(cartItem)
    }

    override suspend fun removeItemFromCartById(itemId: Long) {
        database.shoppingCartDao().removeItemFromCartById(itemId)
    }

    override suspend fun incrementQuantityForItem(item: ShoppingCartItem) {
        val newItem = item.copy(quantity = item.quantity + 1)
        database.shoppingCartDao().insertItem(newItem)
    }

    override suspend fun decrementQuantityForItem(item: ShoppingCartItem) {
        val newItem = item.copy(quantity = if (item.quantity > 1) item.quantity - 1 else return)
        database.shoppingCartDao().insertItem(newItem)
    }

    override suspend fun clear() {
        database.shoppingCartDao().clear()
    }
}
