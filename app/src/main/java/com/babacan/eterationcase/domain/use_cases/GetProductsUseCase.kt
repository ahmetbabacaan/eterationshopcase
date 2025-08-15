package com.babacan.eterationcase.domain.use_cases

import com.babacan.eterationcase.core.Result
import com.babacan.eterationcase.domain.model.ShopProduct
import kotlinx.coroutines.flow.Flow

interface GetProductsUseCase {
    operator fun invoke(): Flow<Result<List<ShopProduct>>>
}