package com.peeranm.gadgetshopp.core.di

import android.app.Application
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.local.GadgetDatabase
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.remote.RetrofitInstance
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.GadgetRepository
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.OrderRepository
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.impl.GadgetRepositoryImpl
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.ShoppingCartRepository
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.impl.OrderRepositoryImpl
import com.peeranm.gadgetshopp.feature_gadgets_shopping.data.repositories.impl.ShoppingCartRepositoryImpl
import com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.gadget_use_cases.GetGadgetsUseCase
import com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.order_use_cases.GetAllOrdersUseCase
import com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.order_use_cases.InsertOrdersUseCase
import com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.order_use_cases.OrderUseCases
import com.peeranm.gadgetshopp.feature_gadgets_shopping.use_cases.shopping_cart_use_cases.*
import com.peeranm.gadgetshopp.feature_gadgets_shopping.utils.GadgetMapper
import com.peeranm.gadgetshopp.feature_gadgets_shopping.utils.OrderMapper
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
    fun provideCartDatabase(app: Application): GadgetDatabase {
        return GadgetDatabase.getInstance(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideGadgetRepository(): GadgetRepository {
        return GadgetRepositoryImpl(
            gadgetAPI = RetrofitInstance.gadgetAPI,
            mapper = GadgetMapper()
        )
    }

    @Provides
    @Singleton
    fun provideShoppingCartRepository(database: GadgetDatabase): ShoppingCartRepository {
        return ShoppingCartRepositoryImpl(
            database = database,
            mapper = GadgetMapper()
        )
    }

    @Provides
    @Singleton
    fun provideOrderRepository(database: GadgetDatabase): OrderRepository {
        return OrderRepositoryImpl(database, OrderMapper())
    }

    @Provides
    @Singleton
    fun provideGadgetUseCase(repository: GadgetRepository): GetGadgetsUseCase {
        return GetGadgetsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideShoppingCartUseCases(
        shoppingCartRepository: ShoppingCartRepository
    ): ShoppingCartUseCases {
        return ShoppingCartUseCases(
            getShoppingCartItems = GetShoppingCartItemsUseCase(shoppingCartRepository),
            getCartItemsCount = GetCartItemsCountUseCase(shoppingCartRepository),
            insertToShoppingCart = InsertToShoppingCartUseCase(shoppingCartRepository),
            removeItemFromCartById = RemoveItemFromCartByIdUseCase(shoppingCartRepository),
            incrementQuantity = IncrementQuantityUseCase(shoppingCartRepository),
            decrementQuantity = DecrementQuantityUseCase(shoppingCartRepository),
            getTotalPrice = GetTotalPriceUseCase(shoppingCartRepository),
            clearShoppingCart = ClearShoppingCartUseCase(shoppingCartRepository),
        )
    }

    @Provides
    @Singleton
    fun provideOrderUseCases(repository: OrderRepository): OrderUseCases {
        return OrderUseCases(
            insertOrders = InsertOrdersUseCase(repository),
            getAllOrders = GetAllOrdersUseCase(repository)
        )
    }

}