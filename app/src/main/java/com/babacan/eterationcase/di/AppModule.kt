package com.babacan.eterationcase.di

import android.content.Context
import androidx.room.Room
import com.babacan.eterationcase.data.local.ShopDAO
import com.babacan.eterationcase.data.local.ShopDatabase
import com.babacan.eterationcase.data.repository.ShopProductRepositoryImpl
import com.babacan.eterationcase.data.service.ShopService
import com.babacan.eterationcase.domain.repository.ShopProductRepository
import com.babacan.eterationcase.domain.use_cases.GetProductByIdUseCaseImpl
import com.babacan.eterationcase.domain.use_cases.GetProductsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideShopProductRepository(
        service: ShopService
    ): ShopProductRepository {
        return ShopProductRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideGetProductsUseCase(
        repository: ShopProductRepository,
        shopDAO: ShopDAO
    ): GetProductsUseCaseImpl {
        return GetProductsUseCaseImpl(repository, shopDAO)
    }

    @Provides
    @Singleton
    fun provideGetProductByIdUseCase(
        repository: ShopProductRepository,
        shopDAO: ShopDAO
    ): GetProductByIdUseCaseImpl {
        return GetProductByIdUseCaseImpl(repository, shopDAO)
    }

    @Singleton
    @Provides
    fun provideShopDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ShopDatabase::class.java,
        "shop_database"
    ).build()

    @Singleton
    @Provides
    fun provideShopDao(database: ShopDatabase) = database.shopDao()

}