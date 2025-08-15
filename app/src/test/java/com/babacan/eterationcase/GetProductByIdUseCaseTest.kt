package com.babacan.eterationcase

import com.babacan.eterationcase.core.Result
import com.babacan.eterationcase.data.local.ShopDAO
import com.babacan.eterationcase.domain.model.Favorite
import com.babacan.eterationcase.domain.model.ShopProduct
import com.babacan.eterationcase.domain.repository.ShopProductRepository
import com.babacan.eterationcase.domain.use_cases.GetProductByIdUseCase
import com.babacan.eterationcase.domain.use_cases.GetProductByIdUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetProductByIdUseCaseImplTest {

    private lateinit var repository: ShopProductRepository
    private lateinit var shopDAO: ShopDAO
    private lateinit var useCase: GetProductByIdUseCase

    @Before
    fun setup() {
        repository = mockk()
        shopDAO = mockk()
        useCase = GetProductByIdUseCaseImpl(repository, shopDAO)
    }

    @Test
    fun `invoke should return product with isFavorite true if it is in favorites`() = runTest {
        val productId = "1"
        val product = ShopProduct(
            id = productId,
            name = "Test Product",
            price = "100",
            image = "img.png",
            description = "desc",
            brand = "brand",
            model = "model",
            createdAt = "2025-08-15"
        )
        val favorites = listOf(
            Favorite(
                id = productId,
                image = "img.png",
                name = "Test Product",
                price = "100"
            )
        )

        coEvery { repository.getProductById(productId) } returns flow {
            emit(Result.Success(product))
        }

        coEvery { shopDAO.getAllFavorites() } returns flow {
            emit(favorites)
        }

        val results = useCase(productId).toList()
        val lastResult = results.last() as Result.Success

        assertEquals(true, lastResult.data.isFavorite)
        assertEquals(product.id, lastResult.data.id)
    }

    @Test
    fun `invoke should return product with isFavorite false if it is not in favorites`() = runTest {
        val productId = "1"
        val product = ShopProduct(
            id = productId,
            name = "Test Product",
            price = "100",
            image = "img.png",
            description = "desc",
            brand = "brand",
            model = "model",
            createdAt = "2025-08-15"
        )
        val favorites = listOf<Favorite>()
        coEvery { repository.getProductById(productId) } returns flow {
            emit(Result.Success(product))
        }

        coEvery { shopDAO.getAllFavorites() } returns flow {
            emit(favorites)
        }

        val results = useCase(productId).toList()
        val lastResult = results.last() as Result.Success

        assertEquals(false, lastResult.data.isFavorite)
    }
}
