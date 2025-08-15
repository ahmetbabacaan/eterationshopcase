package com.babacan.eterationcase.data.repository

import com.babacan.eterationcase.core.Result
import com.babacan.eterationcase.data.model.toDomain
import com.babacan.eterationcase.data.service.ShopService
import com.babacan.eterationcase.domain.model.ShopProduct
import com.babacan.eterationcase.domain.repository.ShopProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ShopProductRepositoryImpl @Inject constructor(
    private val api: ShopService
) : ShopProductRepository {

    override fun getProducts(): Flow<Result<List<ShopProduct>>> = flow {
        try {
            emit(Result.Loading)
            val list = withContext(Dispatchers.IO) {
                api.getProducts().map { it.toDomain() }
            }
            emit(Result.Success(list))
        } catch (t: Throwable) {
            emit(Result.Error(t.message))
        }
    }

    override fun getProductById(id: String): Flow<Result<ShopProduct>> = flow {
        try {
            emit(Result.Loading)
            val product = withContext(Dispatchers.IO) {
                api.getProductById(id).toDomain()
            }
            emit(Result.Success(product))
        } catch (t: Throwable) {
            emit(Result.Error(t.message))
        }
    }
}