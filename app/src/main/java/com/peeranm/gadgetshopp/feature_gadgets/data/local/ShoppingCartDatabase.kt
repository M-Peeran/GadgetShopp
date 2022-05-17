package com.peeranm.gadgetshopp.feature_gadgets.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.peeranm.gadgetshopp.core.utils.Constants
import com.peeranm.gadgetshopp.feature_gadgets.data.local.daos.ShoppingCartDao
import com.peeranm.gadgetshopp.feature_gadgets.data.local.entities.ShoppingCartItem

@Database(entities = [ShoppingCartItem::class], version = 1, exportSchema = false)
abstract class ShoppingCartDatabase : RoomDatabase() {

    abstract fun shoppingCartDao(): ShoppingCartDao

    companion object {

        @Volatile
        private var INSTANCE: ShoppingCartDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): ShoppingCartDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = synchronized(lock) {
                    Room.databaseBuilder(
                        context,
                        ShoppingCartDatabase::class.java,
                        Constants.DATABASE_NAME
                    ).build()
                }
                INSTANCE = instance
            }
            return instance
        }
    }
}