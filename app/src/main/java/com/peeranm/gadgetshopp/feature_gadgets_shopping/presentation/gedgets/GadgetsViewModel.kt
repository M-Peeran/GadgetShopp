package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation.gedgets

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.gadgetshopp.core.utils.Resource
import com.peeranm.gadgetshopp.feature_gadgets_shopping.model.Gadget
import com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.gadget_use_cases.GetGadgetsUseCase
import com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.shopping_cart_use_cases.ShoppingCartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GadgetsViewModel @Inject constructor(
    private val getGadgetsUseCase: GetGadgetsUseCase,
    private val shoppingCartUseCases: ShoppingCartUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _gadgets = MutableStateFlow<Resource<List<Gadget>>>(Resource.Loading)
    val gadgets: StateFlow<Resource<List<Gadget>>>
    get() = _gadgets

    private val _cartItemsCount = MutableStateFlow(0)
    val cartItemsCount: StateFlow<Int>
    get() = _cartItemsCount

    init {
        getGadgetsUseCase()
            .onEach { resource -> _gadgets.value = resource }
            .launchIn(viewModelScope)

        shoppingCartUseCases.getCartItemsCount()
            .onEach { count -> _cartItemsCount.value = count ?: 0 }
            .launchIn(viewModelScope)
    }

}