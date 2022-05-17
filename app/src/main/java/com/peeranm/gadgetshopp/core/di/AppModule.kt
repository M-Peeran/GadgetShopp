package com.peeranm.gadgetshopp.core.di

import android.app.Application
import com.peeranm.gadgetshopp.feature_gadgets.data.local.ShoppingCartDatabase
import com.peeranm.gadgetshopp.feature_gadgets.data.remote.RetrofitInstance
import com.peeranm.gadgetshopp.feature_gadgets.data.repositories.GadgetsRepository
import com.peeranm.gadgetshopp.feature_gadgets.data.repositories.GadgetsRepositoryImpl
import com.peeranm.gadgetshopp.feature_gadgets.use_cases.GadgetUseCases
import com.peeranm.gadgetshopp.feature_gadgets.use_cases.GetGadgetsUseCase
import com.peeranm.gadgetshopp.feature_gadgets.utils.GadgetMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCartDatabase(app: Application): ShoppingCartDatabase {
        return ShoppingCartDatabase.getInstance(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideGadgetRepository(database: ShoppingCartDatabase): GadgetsRepository {
        return GadgetsRepositoryImpl(
            retrofitInstance = RetrofitInstance,
            database = database,
            mapper = GadgetMapper()
        )
    }

    @Provides
    @Singleton
    fun provideGadgetUseCases(repository: GadgetsRepository): GadgetUseCases {
        return GadgetUseCases(
            getGadgets = GetGadgetsUseCase(repository)
        )
    }

}