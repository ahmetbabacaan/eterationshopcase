package com.babacan.eterationcase.domain.repository

import com.babacan.eterationcase.core.Result
import com.babacan.eterationcase.domain.model.ShopProduct
import kotlinx.coroutines.flow.Flow

interface ShopProductRepository {
    fun getProducts(): Flow<Result<List<ShopProduct>>>
    fun getProductById(id: String): Flow<Result<ShopProduct>>
}