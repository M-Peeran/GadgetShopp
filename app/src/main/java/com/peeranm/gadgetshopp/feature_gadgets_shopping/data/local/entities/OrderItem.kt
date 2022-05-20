package com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
@Entity(tableName = "table_orders")
data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val timestamp: Long = System.currentTimeMillis(),
    val name: String,
    val imageUrl: String,
    val price: Double,
    val orderStatus: Int = Random.nextInt(1, 5)
): Parcelable