package com.peeranm.gadgetshopp.feature_gadgets_shopping.utils

import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.entities.ShoppingCartItem
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.remote.dtos.GadgetDto
import com.peeranm.gadgetshopp.feature_gadgets_shopping.model.Gadget

class GadgetMapper {

    fun fromDtoToUiModel(dto: GadgetDto): Gadget {
        return Gadget(
            name = dto.name,
            price = dto.price.toDouble(),
            imageUrl = dto.image_url,
            rating = dto.rating
        )
    }

    fun fromUiModelToCartItem(model: Gadget): ShoppingCartItem {
        return ShoppingCartItem(
            name = model.name,
            imageUrl = model.imageUrl,
            price = model.price,
            rating = model.rating,
            quantity = 1
        )
    }
}