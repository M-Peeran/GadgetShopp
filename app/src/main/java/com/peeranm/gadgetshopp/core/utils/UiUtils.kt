package com.peeranm.gadgetshopp.core.utils

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setActionBarTitle(@StringRes stringResId: Int) {
    (this.requireActivity() as AppCompatActivity).supportActionBar?.title = this.getString(stringResId)
}