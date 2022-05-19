package com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.gadget_use_cases

import com.peeranm.gadgetshopp.core.utils.Resource
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.GadgetRepository
import com.peeranm.gadgetshopp.feature_gadgets_shopping.model.Gadget
import kotlinx.coroutines.flow.Flow

class GetGadgetsUseCase(private val repository: GadgetRepository) {

    operator fun invoke(): Flow<Resource<List<Gadget>>> = repository.getGadgets()
}