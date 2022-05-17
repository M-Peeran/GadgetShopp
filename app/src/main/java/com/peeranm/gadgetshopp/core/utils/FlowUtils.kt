package com.peeranm.gadgetshopp.core.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * Get lifecycle awareness with flows
 */


fun <T> LifecycleOwner.collectWithLifecycle(flow: Flow<T>, collect: FlowCollector<T>) =
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }

fun <T> LifecycleOwner.collectLatestWithLifecycle(flow: Flow<T>, collect: suspend (T) -> Unit) =
    lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        flow.collectLatest(collect)
    }
}