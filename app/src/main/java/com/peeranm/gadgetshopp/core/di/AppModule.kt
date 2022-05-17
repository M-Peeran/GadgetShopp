package com.peeranm.gadgetshopp.core.di

import android.app.Application
import com.peeranm.gadgetshopp.feature_gadgets.data.local.ShoppingCartDatabase
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

}