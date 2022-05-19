package com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.peeranm.gadgetshopp.core.utils.Constants
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.daos.OrderDao
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.daos.ShoppingCartDao
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.OrderItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem

@Database(entities = [ShoppingCartItem::class, OrderItem::class], version = 1, exportSchema = false)
abstract class GadgetDatabase : RoomDatabase() {

    abstract fun shoppingCartDao(): ShoppingCartDao
    abstract fun orderDao(): OrderDao

    companion object {

        @Volatile
        private var INSTANCE: GadgetDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): GadgetDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = synchronized(lock) {
                    Room.databaseBuilder(
                        context,
                        GadgetDatabase::class.java,
                        Constants.DATABASE_NAME
                    ).build()
                }
                INSTANCE = instance
            }
            return instance
        }
    }
}