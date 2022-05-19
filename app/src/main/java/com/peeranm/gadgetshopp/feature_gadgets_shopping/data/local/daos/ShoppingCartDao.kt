package com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.daos

import androidx.room.*
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(cartItem: ShoppingCartItem)

    @Query("delete from table_shopping_cart_items where id =:itemId")
    suspend fun removeItemFromCartById(itemId: Long)

    @Query("select * from table_shopping_cart_items")
    fun getShoppingCartItems(): Flow<List<ShoppingCartItem>>

    @Query("select count(*) from table_shopping_cart_items")
    fun getCartItemsCount(): Flow<Int?>

    @Query("select sum(quantity * price) from table_shopping_cart_items")
    fun getTotalPrice(): Flow<Double?>

    @Query("delete from table_shopping_cart_items")
    suspend fun clear()

}