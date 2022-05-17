package com.peeranm.gadgetshopp.feature_gadgets.presentation.gedgets

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.gadgetshopp.core.utils.Resource
import com.peeranm.gadgetshopp.feature_gadgets.model.Gadget
import com.peeranm.gadgetshopp.feature_gadgets.use_cases.GadgetUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GadgetsViewModel @Inject constructor(
    private val gadgetUseCases: GadgetUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _gadgets = MutableStateFlow<Resource<List<Gadget>>>(Resource.Loading)
    val gadgets: StateFlow<Resource<List<Gadget>>>
    get() = _gadgets

    init {
        gadgetUseCases.getGadgets()
            .onEach { resource -> _gadgets.value = resource }
            .launchIn(viewModelScope)
    }

}