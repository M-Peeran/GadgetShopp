package com.peeranm.gadgetshopp.feature_gadgets.use_cases

import com.peeranm.gadgetshopp.core.utils.Resource
import com.peeranm.gadgetshopp.feature_gadgets.data.repositories.GadgetsRepository
import com.peeranm.gadgetshopp.feature_gadgets.model.Gadget
import kotlinx.coroutines.flow.Flow

class GetGadgetsUseCase(private val repository: GadgetsRepository) {

    operator fun invoke(): Flow<Resource<List<Gadget>>> = repository.getGadgets()
}