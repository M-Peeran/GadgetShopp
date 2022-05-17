package com.peeranm.gadgetshopp.feature_gadgets.presentation.gadget_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.peeranm.gadgetshopp.core.utils.Constants
import com.peeranm.gadgetshopp.feature_gadgets.model.Gadget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GadgetDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _gadget = MutableStateFlow(getDummyGadget())
    val gadget: StateFlow<Gadget>
    get() = _gadget

    init {
        val gadget = savedStateHandle.get<Gadget>(Constants.SELECTED_GADGET)
        gadget?.let { _gadget.value = it }
    }

    private fun getDummyGadget() = Gadget("", 0.0, 0, "")

}