package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation.gadget_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.gadgetshopp.core.utils.Constants
import com.peeranm.gadgetshopp.feature_gadgets_shopping.model.Gadget
import com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.shopping_cart_use_cases.ShoppingCartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GadgetDetailsViewModel @Inject constructor(
    private val shoppingCartUseCases: ShoppingCartUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _gadget = MutableStateFlow(getDummyGadget())
    val gadget: StateFlow<Gadget>
    get() = _gadget

    private val _cartItemsCount = MutableStateFlow(0)
    val cartItemsCount: StateFlow<Int>
    get() = _cartItemsCount

    init {
        val gadget = savedStateHandle.get<Gadget>(Constants.SELECTED_GADGET)
        gadget?.let { _gadget.value = it }

        shoppingCartUseCases.getCartItemsCount()
            .onEach { count -> _cartItemsCount.value = count ?: 0 }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: GadgetDetailsEvent) {
        when (event) {
            is GadgetDetailsEvent.InsertToCart -> {
                viewModelScope.launch {
                    shoppingCartUseCases.insertToShoppingCart(event.item)
                }
            }
        }
    }

    private fun getDummyGadget() = Gadget("", 0.0, 0, "")

}