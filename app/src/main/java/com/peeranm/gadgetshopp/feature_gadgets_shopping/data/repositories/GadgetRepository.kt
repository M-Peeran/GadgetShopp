package com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories

import com.peeranm.gadgetshopp.core.utils.Resource
import com.peeranm.gadgetshopp.feature_gadgets_shopping.model.Gadget
import kotlinx.coroutines.flow.Flow

interface GadgetRepository {
    fun getGadgets(): Flow<Resource<List<Gadget>>>
}