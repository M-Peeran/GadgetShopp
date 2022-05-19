package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation.shopping_cart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.order_use_cases.OrderUseCases
import com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.shopping_cart_use_cases.ShoppingCartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val shoppingCartUseCases: ShoppingCartUseCases,
    private val orderUseCases: OrderUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _shoppingCartItems = MutableStateFlow(emptyList<ShoppingCartItem>())
    val shoppingCartItems: StateFlow<List<ShoppingCartItem>>
    get() = _shoppingCartItems

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double>
    get() = _totalPrice

    init {
        shoppingCartUseCases.getShoppingCartItems()
            .onEach { items -> _shoppingCartItems.value = items }
            .launchIn(viewModelScope)

        shoppingCartUseCases.getTotalPrice()
            .onEach { total ->
                if (total == null || total.toInt() <= 0) {
                    _totalPrice.value = 0.0
                } else _totalPrice.value = total
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: ShoppingCartEvent) {
        when (event) {
            is ShoppingCartEvent.InsertOrders -> {
                viewModelScope.launch {
                    orderUseCases.insertOrders(event.items)
                    shoppingCartUseCases.clearShoppingCart()
                }
            }
            is ShoppingCartEvent.IncrementQuantityForItem -> {
                viewModelScope.launch {
                    shoppingCartUseCases.incrementQuantity(event.item)
                }
            }
            is ShoppingCartEvent.DecrementQuantityForItem -> {
                viewModelScope.launch {
                    shoppingCartUseCases.decrementQuantity(event.item)
                }
            }
            is ShoppingCartEvent.RemoveFromCart -> {
                viewModelScope.launch {
                    shoppingCartUseCases.removeItemFromCartById(event.itemId)
                }
            }
        }
    }

}