package com.peeranm.gadgetshopp.feature_gadgets.data.repositories

import com.peeranm.gadgetshopp.core.Resource
import com.peeranm.gadgetshopp.feature_gadgets.data.local.ShoppingCartDatabase
import com.peeranm.gadgetshopp.feature_gadgets.data.local.entities.ShoppingCartItem
import com.peeranm.gadgetshopp.feature_gadgets.data.remote.RetrofitInstance
import com.peeranm.gadgetshopp.feature_gadgets.model.Gadget
import com.peeranm.gadgetshopp.feature_gadgets.utils.GadgetMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

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
                        emit(Resource.error("Empty response received"))
                    }
                    val gadgets = gadgetApiResponse.products.map { mapper.fromDtoToUiModel(it) }
                    emit(Resource.success(gadgets))
                }
            }
        } catch (exception: IOException) {
            emit(Resource.error("Couldn't reach server. Please check your internet connection"))
        } catch (exception: HttpException) {
            emit(Resource.error("An unknown error occurred"))
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