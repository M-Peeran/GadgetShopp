package com.peeranm.gadgetshopp.feature_gadgets.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_shopping_cart_items")
class ShoppingCartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val rating: Int,
    val quantity: Int
)