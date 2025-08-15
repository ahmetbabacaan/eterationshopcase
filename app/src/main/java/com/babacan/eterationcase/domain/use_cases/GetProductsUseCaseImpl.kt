package com.babacan.eterationcase.domain.use_cases

import com.babacan.eterationcase.core.Result
import com.babacan.eterationcase.data.local.ShopDAO
import com.babacan.eterationcase.domain.model.ShopProduct
import com.babacan.eterationcase.domain.repository.ShopProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsUseCaseImpl @Inject constructor(
    private val repository: ShopProductRepository,
    private val shopDAO: ShopDAO,
): GetProductsUseCase {
    override operator fun invoke(): Flow<Result<List<ShopProduct>>> {
        return flow {
            repository.getProducts().collect { products ->
                when (products) {
                    is Result.Error -> {
                        emit(Result.Error(products.message))
                    }

                    Result.Loading -> {
                        emit(Result.Loading)
                    }

                    is Result.Success<List<ShopProduct>> -> {
                        shopDAO.getAllFavorites().collect {
                            val favorites = it.map { favorite -> favorite.id }.toSet()
                            val products = products.data.map { product ->
                                product.copy(isFavorite = favorites.contains(product.id))
                            }
                            emit(Result.Success(products))
                        }
                    }
                }
            }
        }

    }
}