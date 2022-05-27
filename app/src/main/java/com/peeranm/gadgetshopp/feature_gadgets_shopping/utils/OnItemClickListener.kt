package com.peeranm.gadgetshopp.feature_gadgets_shopping.utils

import android.view.View

interface OnItemClickListener<T> {
    fun onItemClick(view: View?, data: T, position: Int)
}