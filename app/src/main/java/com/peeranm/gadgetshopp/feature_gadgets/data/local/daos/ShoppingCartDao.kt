package com.peeranm.gadgetshopp.feature_gadgets.data.local.daos

import androidx.room.*
import com.peeranm.gadgetshopp.feature_gadgets.data.local.entities.ShoppingCartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(cartItem: ShoppingCartItem)

    @Delete
    suspend fun deleteItem(cartItem: ShoppingCartItem)

    @Query("select * from table_shopping_cart_items")
    fun getShoppingCartItems(): Flow<List<ShoppingCartItem>>

    @Query("select sum(quantity * price) from table_shopping_cart_items")
    fun getTotalPrice(): Flow<Double>

    @Query("delete from table_shopping_cart_items")
    fun clear()

}