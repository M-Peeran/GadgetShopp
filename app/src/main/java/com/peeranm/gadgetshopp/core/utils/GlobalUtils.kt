package com.peeranm.gadgetshopp.core.utils

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.peeranm.gadgetshopp.R
import java.text.SimpleDateFormat

fun Fragment.setActionBarTitle(@StringRes stringResId: Int) {
    (this.requireActivity() as AppCompatActivity).supportActionBar?.title = this.getString(stringResId)
}

fun getDateAndTimeFromTimestamp(timestamp: Long): String {
    val formatter = SimpleDateFormat.getDateTimeInstance()
    return formatter.format(timestamp)
}

fun getOrderState(state: Int): Int {
    return when (state) {
        1 -> R.string.order_state_confirmed
        2 -> R.string.order_state_shipped
        3 -> R.string.order_state_on_the_way
        4 -> R.string.order_state_delivered
        else -> R.string.order_state_unconfirmed
    }
}