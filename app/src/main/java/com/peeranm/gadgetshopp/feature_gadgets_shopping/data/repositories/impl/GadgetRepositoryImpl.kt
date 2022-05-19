package com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.impl

import com.peeranm.gadgetshopp.core.utils.Constants
import com.peeranm.gadgetshopp.core.utils.Resource
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.remote.GadgetAPI
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.remote.RetrofitInstance
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.GadgetRepository
import com.peeranm.gadgetshopp.feature_gadgets_shopping.model.Gadget
import com.peeranm.gadgetshopp.feature_gadgets_shopping.utils.GadgetMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class GadgetRepositoryImpl(
    private val gadgetAPI: GadgetAPI,
    private val mapper: GadgetMapper
) : GadgetRepository {

    override fun getGadgets(): Flow<Resource<List<Gadget>>> = flow {
        try {
            val response = gadgetAPI.getGadgets()
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
}
