package com.peeranm.gadgetshopp.feature_gadgets_shopping.data.remote.dtos

data class GadgetApiResponse(
    val products: List<GadgetDto>,
    val code: Int,
    val message: String?
)