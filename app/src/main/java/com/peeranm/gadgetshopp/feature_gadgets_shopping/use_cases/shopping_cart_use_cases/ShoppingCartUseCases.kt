package com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.shopping_cart_use_cases

class ShoppingCartUseCases(
    val getShoppingCartItems: GetShoppingCartItemsUseCase,
    val getCartItemsCount: GetCartItemsCountUseCase,
    val insertToShoppingCart: InsertToShoppingCartUseCase,
    val removeItemFromCartById: RemoveItemFromCartByIdUseCase,
    val incrementQuantity: IncrementQuantityUseCase,
    val decrementQuantity: DecrementQuantityUseCase,
    val getTotalPrice: GetTotalPriceUseCase,
    val clearShoppingCart: ClearShoppingCartUseCase
)