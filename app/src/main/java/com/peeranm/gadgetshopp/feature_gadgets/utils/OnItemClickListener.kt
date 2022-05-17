package com.peeranm.gadgetshopp.feature_gadgets.utils

interface OnItemClickListener {
    fun <T> onItemClick(data: T, position: Int)
}