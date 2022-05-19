package com.peeranm.gadgetshopp.feature_gadgets_shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gadget(
    val name: String,
    val price: Double,
    val rating: Int,
    val imageUrl: String
) : Parcelable