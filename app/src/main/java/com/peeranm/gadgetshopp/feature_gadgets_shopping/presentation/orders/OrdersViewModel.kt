package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation.orders

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.OrderItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.order_use_cases.OrderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderUseCases: OrderUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _orders = MutableStateFlow(emptyList<OrderItem>())
    val orders: StateFlow<List<OrderItem>>
    get() = _orders

    init {
        orderUseCases.getAllOrders()
            .onEach { orders -> _orders.value = orders }
            .launchIn(viewModelScope)
    }

}