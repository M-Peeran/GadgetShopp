package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation.order_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.peeranm.gadgetshopp.core.utils.Constants
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.OrderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _selectedOrder = MutableStateFlow(getDummyOrder())
    val selectedOrder: StateFlow<OrderItem>
    get() = _selectedOrder

    init {
        val order = savedStateHandle.get<OrderItem>(Constants.SELECTED_ORDER)
        order?.let { _selectedOrder.value = it }
    }

    private fun getDummyOrder() = OrderItem(
        timestamp = 0,
        name = "",
        imageUrl = "",
        price = 0.0
    )

}