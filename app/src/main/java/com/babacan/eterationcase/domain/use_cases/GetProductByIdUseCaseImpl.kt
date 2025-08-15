package com.babacan.eterationcase.domain.use_cases

import com.babacan.eterationcase.core.Result
import com.babacan.eterationcase.data.local.ShopDAO
import com.babacan.eterationcase.domain.model.ShopProduct
import com.babacan.eterationcase.domain.repository.ShopProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductByIdUseCaseImpl @Inject constructor(
    private val repository: ShopProductRepository,
    private val shopDAO: ShopDAO,
) : GetProductByIdUseCase {

    override operator fun invoke(productId: String): Flow<Result<ShopProduct>> {
        return flow {
            repository.getProductById(productId).collect { products ->
                when (products) {
                    is Result.Error -> {
                        emit(Result.Error(products.message))
                    }

                    Result.Loading -> {
                        emit(Result.Loading)
                    }

                    is Result.Success<ShopProduct> -> {
                        shopDAO.getAllFavorites().collect {
                            val favorites = it.map { favorite -> favorite.id }.toSet()
                            val product = products.data.let { product ->
                                product.copy(isFavorite = favorites.contains(product.id))
                            }
                            emit(Result.Success(product))
                        }
                    }
                }
            }
        }
    }

}