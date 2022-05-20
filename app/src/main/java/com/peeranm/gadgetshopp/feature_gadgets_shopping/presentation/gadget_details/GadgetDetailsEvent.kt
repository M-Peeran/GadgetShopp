package com.peeranm.gadgetshopp.feature_gadgets_shopping.presentation.gadget_details

import com.peeranm.gadgetshopp.feature_gadgets_shopping.model.Gadget

sealed class GadgetDetailsEvent {
    class InsertToCart(val item: Gadget) : GadgetDetailsEvent()
}