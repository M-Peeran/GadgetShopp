package com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.daos

import androidx.room.*
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.OrderItem
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orderItem: OrderItem)

    @Query("select * from table_orders order by timestamp desc")
    fun getAllOrders(): Flow<List<OrderItem>>

}