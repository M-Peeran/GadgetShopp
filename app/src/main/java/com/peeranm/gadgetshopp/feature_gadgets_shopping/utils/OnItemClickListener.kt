package com.peeranm.gadgetshopp.feature_gadgets_shopping.utils

import android.view.View

interface OnItemClickListener {
    fun <T> onItemClick(view: View?, data: T, position: Int)
}