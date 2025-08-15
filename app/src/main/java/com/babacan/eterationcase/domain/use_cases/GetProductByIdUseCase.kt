package com.babacan.eterationcase.domain.use_cases

import com.babacan.eterationcase.core.Result
import com.babacan.eterationcase.domain.model.ShopProduct
import kotlinx.coroutines.flow.Flow

interface GetProductByIdUseCase {
    operator fun invoke(productId: String): Flow<Result<ShopProduct>>
}