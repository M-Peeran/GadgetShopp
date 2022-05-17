package com.peeranm.gadgetshopp.feature_gadgets.data.repositories

import com.peeranm.gadgetshopp.core.utils.Constants
import com.peeranm.gadgetshopp.core.utils.Resource
import com.peeranm.gadgetshopp.feature_gadgets.data.local.ShoppingCartDatabase
import com.peeranm.gadgetshopp.feature_gadgets.data.local.entities.ShoppingCartItem
import com.peeranm.gadgetshopp.feature_gadgets.data.remote.RetrofitInstance
import com.peeranm.gadgetshopp.feature_gadgets.model.Gadget
import com.peeranm.gadgetshopp.feature_gadgets.utils.GadgetMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class GadgetsRepositoryImpl(
    private val retrofitInstance: RetrofitInstance,
    private val database: ShoppingCartDatabase,
    private val mapper: GadgetMapper
) : GadgetsRepository {

    override fun getGadgets(): Flow<Resource<List<Gadget>>> = flow {
        try {
            val response = retrofitInstance.gadgetAPI.getGadgets()
            if (response.isSuccessful) {
                response.body()?.let { gadgetApiResponse ->
                    if (gadgetApiResponse.products.isEmpty()) {
                        emit(Resource.Error("Empty response received"))
                    }
                    val gadgets = gadgetApiResponse.products.map { mapper.fromDtoToUiModel(it) }
                    emit(Resource.Success(gadgets))
                }
            } else {
                val errorMessage = when (response.code()) {
                    Constants.ERROR_RESOURCE_NOT_FOUND -> "The resource not found"
                    Constants.ERROR_INTERNAL_SERVER -> "The server is having problems, please try to connect later"
                    Constants.ERROR_UNAUTHORIZED -> "You are unauthorized to use this service"
                    else -> "Oops! Something went wrong"
                }
                emit(Resource.Error(errorMessage))
            }
        } catch (throwable: Throwable) {
            val errorMessage =  when (throwable) {
                is IOException -> "Couldn't reach server. Please check your internet connection"
                else -> throwable.message ?: "Oops! Something went wrong"
            }
            emit(Resource.Error(errorMessage))
        }
    }

    override fun getAllCartItems(): Flow<List<ShoppingCartItem>> {
        return database.shoppingCartDao().getShoppingCartItems()
    }

    override suspend fun insertToCart(item: ShoppingCartItem) {
        database.shoppingCartDao().insertItem(item)
    }

    override suspend fun deleteFromCart(item: ShoppingCartItem) {
        database.shoppingCartDao().deleteItem(item)
    }

    override suspend fun clearCart() {
        database.shoppingCartDao().clear()
    }
}