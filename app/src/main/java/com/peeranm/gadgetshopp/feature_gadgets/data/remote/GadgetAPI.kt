package com.peeranm.gadgetshopp.feature_gadgets.data.remote

import com.peeranm.gadgetshopp.feature_gadgets.data.remote.dtos.GadgetApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface GadgetAPI {

    // Not using query/path params because url is static
    @GET("/nancymadan/assignment/db")
    suspend fun getGadgets(): Response<GadgetApiResponse>

}